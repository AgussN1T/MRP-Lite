package unpsjb.labprog.backend.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Equipo;
import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Hueco;
import unpsjb.labprog.backend.model.PedidoFabricacion;
import unpsjb.labprog.backend.model.Planificacion;
import unpsjb.labprog.backend.model.Producto;
import unpsjb.labprog.backend.model.Registro;
import unpsjb.labprog.backend.model.Taller;
import unpsjb.labprog.backend.model.Tarea;
import unpsjb.labprog.backend.model.TipoEquipo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PlanificadorService {

    @Autowired
    ProductoService productoService;

    @Autowired
    TallerService tallerService;

    @Autowired
    PedidoFabricacionService pedidoFabricacionService;

    @Autowired
    PlanificacionService planificacionService;

    @Autowired
    EquipoService equipoService;

    @Autowired
    HuecoService huecoService;

    @Autowired
    TallerPertinenteService tallerPertinenteService;

    @Autowired
    PlanificacionHuecoService planificacionHuecoService;

    @Autowired
    RegistroService registroService;

    private static final Logger logger = LoggerFactory.getLogger(PlanificadorService.class);

    private Timestamp primerLugar(Equipo aEquipo, Tarea aTarea, Timestamp finAnterior, Taller aTaller) {

        // huecoService.obtenerHuecos(aTarea, aTaller, finAnterior);
        
        huecoService.obtenerHueco(aTaller, aTarea, finAnterior);

        List<Hueco> huecos = planificacionService.obtenerHuecos(aEquipo);

        for (Hueco aHueco : huecos) {
            if (aHueco.getInicioHueco().getTime() >= finAnterior.getTime()
                    && (calcularFechaFin(aTarea, aEquipo, aHueco.getInicioHueco()).getTime() <= aHueco.getFinHueco()
                            .getTime())) {
                return aHueco.getInicioHueco();

            }
        }
        return finAnterior;
    }

    public Taller recuperarTallerPertinente(String nombreProducto) {

        Producto producto = productoService.findByName(nombreProducto);
        if (producto == null)
            throw new ProductoNotFoundException();

        List<Taller> talleres = tallerService.findAllOrderByCode();

        Set<TipoEquipo> productoTipoEquipos = producto.getTareas().stream()
                .map(Tarea::getTipoEquipo)
                .collect(Collectors.toSet());

        Optional<Taller> aTallerOrNull = talleres.stream()
                .filter(taller -> taller.getEquipos().stream()
                        .map(Equipo::getTipoEquipo)
                        .collect(Collectors.toSet())
                        .containsAll(productoTipoEquipos))
                .findFirst();

        return aTallerOrNull.orElse(null);
    }

    public List<Taller> recuperarTalleresPertinentes(String nombreProducto) {

        Producto producto = productoService.findByName(nombreProducto);
        if (producto == null)
            throw new ProductoNotFoundException();

        List<Taller> talleres = tallerService.findAllOrderByCode();

        Set<TipoEquipo> productoTipoEquipos = producto.getTareas().stream()
                .map(Tarea::getTipoEquipo)
                .collect(Collectors.toSet());

        List<Taller> aTalleresOrNull = talleres.stream()
                .filter(taller -> taller.getEquipos().stream()
                        .map(Equipo::getTipoEquipo)
                        .collect(Collectors.toSet())
                        .containsAll(productoTipoEquipos))
                .collect(Collectors.toList());

        if (aTalleresOrNull.isEmpty())
            return null;

        return aTalleresOrNull;
    }

    public long calcularDuracionTarea(Equipo aEquipo, Tarea aTarea) {
        return (long) (aTarea.getTiempo() / aEquipo.getCapacidad()) * 60 * 1000;
    }

    private Timestamp calcularFechaFin(Tarea aTarea, Equipo aEquipo, Timestamp tiempoBase) {
        long duracionMilisegundos = (long) (aTarea.getTiempo() / aEquipo.getCapacidad()) * 60 * 1000;
        Timestamp result = new Timestamp(tiempoBase.getTime() + duracionMilisegundos);
        return result;
    }

    public void planificarTodo() {
        for (PedidoFabricacion aPedido : pedidoFabricacionService.findAllOrderByFechaPedido()) {
            planificarPedido(aPedido);
        }
    }

    public void rollbackHardcodeado(PedidoFabricacion aPedido) {
        for (Planificacion aPlanificacion : aPedido.getPlanificaciones()) {
            planificacionService.delete(aPlanificacion.getId());
        }
    }

    public PedidoFabricacion planificarPedido(PedidoFabricacion aPedido) {

        if (aPedido == null) {
            throw new PedidoNotFoundException();
        }

        if (aPedido.getEstado() == Estado.Planificado || aPedido.getEstado() == Estado.Finalizado) {
            registroService.save(
                    new Registro("El pedido con id " + aPedido.getId() + " no se pudo planificar debido a su estado"));
            return aPedido;
        }

        boolean result = false;
        for (Taller aTaller : tallerPertinenteService.talleresPorEficiencia(aPedido.getProducto())) {
            huecoService.generarBackup(aTaller);
            try {
                result = planificarProducto2(aPedido, aTaller);
                if (result) {
                    registroService.save(
                            new Registro("Se planificó el pedido con id " + aPedido.getId() + " en el taller: " + aTaller.getNombre() + " con esquema de pronta entrega"));
                    break;
                }

            } catch (NoPlanificableException ex) {
                // rollbackHardcodeado(aPedido);
                huecoService.rollbackBackup(aTaller);
                aPedido.getPlanificaciones().clear();
                huecoService.rollback(aTaller);
                result = false;
                continue;
            }

        }

        if (result) {
            aPedido.setEstado(Estado.Planificado);
            planificacionService.saveAll(aPedido.getPlanificaciones());
            pedidoFabricacionService.save(aPedido);
        } else {
            aPedido.setEstado(Estado.No_Planificable);
            registroService.save(
                new Registro("No pudo planificarse el pedido con id " + aPedido.getId() + "con esquema de pronta entrega"));
        }

        return aPedido;
    }

    @Transactional(rollbackOn = NoPlanificableException.class)
    public boolean planificarProducto(PedidoFabricacion aPedido, Taller aTaller) {
        List<Tarea> tareas = ordenarTareas(aPedido.getProducto());

        Timestamp fechaTrabajo;

        for (int i = 0; i < aPedido.getCantidad(); i++) {
            fechaTrabajo = aPedido.getFechaPedido();

            for (Tarea aTarea : tareas) {
                List<Equipo> equipos = buscarEquipo(aTarea, aTaller);

                fechaTrabajo = planificarTarea(aTarea, equipos.get(0), aTaller, fechaTrabajo, aPedido);

                if (fechaTrabajo.getTime() > aPedido.getFechaEntrega().getTime()) {
                    throw new NoPlanificableException();
                }
            }

        }
        return true;
    }

    public Timestamp planificarTarea(Tarea aTarea, Equipo aEquipo, Taller aTaller, Timestamp fechaTrabajo,
            PedidoFabricacion aPedido) {

        Timestamp iniciaPlanificacion = primerLugar(aEquipo, aTarea, fechaTrabajo, aTaller);

        Timestamp terminaPlanificacion = calcularFechaFin(aTarea, aEquipo, iniciaPlanificacion);

        Planificacion aPlanificacion = new Planificacion();

        aPlanificacion.setInicio(iniciaPlanificacion);
        aPlanificacion.setEquipo(aEquipo);
        aPlanificacion.setTarea(aTarea);
        aPlanificacion.setFin(terminaPlanificacion);

        aPedido.getPlanificaciones().add(aPlanificacion);
        planificacionService.save(aPlanificacion);

        return terminaPlanificacion;
    }

    public List<Equipo> buscarEquipo(Tarea aTarea, Taller aTaller) {

        List<Equipo> someEquipos = aTaller.getEquipos().stream()
                .filter(equipo -> equipo.getTipoEquipo().getNombre().equals(aTarea.getTipoEquipo().getNombre()))
                .collect(Collectors.toList());

        return someEquipos;
    }

    public List<Tarea> ordenarTareas(Producto aProducto) {

        List<Tarea> tareasOrdenadas = new ArrayList<Tarea>();

        tareasOrdenadas.addAll(aProducto.getTareas());

        Comparator<Tarea> comparadorPorNumeroOrden = new Comparator<Tarea>() {
            public int compare(Tarea tarea1, Tarea tarea2) {
                return Integer.compare(tarea1.getOrden(), tarea2.getOrden());
            }
        };

        tareasOrdenadas.sort(comparadorPorNumeroOrden);

        return tareasOrdenadas;
    }

    public void agotarTaller(int id) {
        Taller aTaller = tallerService.findById(id);
        planificacionService.agotarTaller(aTaller);
        logger.info("Se agotó el taller " + aTaller.getNombre());
    }

    public boolean planificarProducto2(PedidoFabricacion aPedido, Taller aTaller) {
        List<Tarea> tareas = ordenarTareas(aPedido.getProducto());

        Timestamp fechaTrabajo;

        for (int i = 0; i < aPedido.getCantidad(); i++) {
            fechaTrabajo = aPedido.getFechaPedido();

            for (Tarea aTarea : tareas) {
                fechaTrabajo = planificarTarea2(aPedido, aTaller, aTarea, fechaTrabajo);

                if (fechaTrabajo.getTime() > aPedido.getFechaEntrega().getTime()) {
                    throw new NoPlanificableException();
                }
            }

        }
        return true;

    }

    public Timestamp planificarTarea2(PedidoFabricacion aPedido, Taller aTaller, Tarea aTarea,
            Timestamp aFechaTrabajo) {

        Planificacion aPlanificacion = generarPlanificacion(aTarea, aTaller, aFechaTrabajo);
        if (aPlanificacion == null)
            throw new NoPlanificableException();

        aPedido.getPlanificaciones().add(aPlanificacion);
        // logger.info("Planificacion: " + aPlanificacion.getInicio() + " y " +
        // aPlanificacion.getFin());
        return aPlanificacion.getFin();
    }

    private Planificacion generarPlanificacion(Tarea aTarea, Taller aTaller, Timestamp aFechaTrabajo) {
        return huecoService.obtenerHueco(aTaller, aTarea, aFechaTrabajo);
    }

 


}