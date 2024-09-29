package unpsjb.labprog.backend.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.model.Equipo;
import unpsjb.labprog.backend.model.Estado;
import unpsjb.labprog.backend.model.Hueco;
import unpsjb.labprog.backend.model.PedidoFabricacion;
import unpsjb.labprog.backend.model.Planificacion;
import unpsjb.labprog.backend.model.Producto;
import unpsjb.labprog.backend.model.Registro;
import unpsjb.labprog.backend.model.Taller;
import unpsjb.labprog.backend.model.Tarea;

@Service
public class PlanificadorTardioService {

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
    TallerPertinenteService tallerPertinenteService;

    @Autowired
    PlanificacionHuecoService planificacionHuecoService;

    @Autowired
    RegistroService registroService;

    private static final Logger logger = LoggerFactory.getLogger(PlanificadorService.class);

    public void planificarTodo() {
        // huecoService.romperTodo();
        for (PedidoFabricacion aPedido : pedidoFabricacionService.findAllOrderByFechaEntrega()) {
            planificarPedido(aPedido);
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
            try {
                result = planificarProductoT(aPedido, aTaller);
                if (result) {
                    registroService.save(
                            new Registro("Se planificó el pedido con id " + aPedido.getId() + " en el taller: "
                                    + aTaller.getNombre() + "con esquema de entrega tardía"));
                    break;
                }

            } catch (NoPlanificableException ex) {
                logger.info("rollback con pedido: " + aPedido.getId());
                planificacionHuecoService.rollback((List<Planificacion>) aPedido.getPlanificaciones());

                aPedido.getPlanificaciones().clear();

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
                    new Registro("No pudo planificarse el pedido con id " + aPedido.getId()
                            + " con esquema de entrega tardía"));
        }

        registroService.create(new Registro(
                "Pedido con id " + aPedido.getId() + " solicitado el" + aPedido.getFechaPedido() + " a entregar el "
                        + aPedido.getFechaEntrega() + "con estado: " + aPedido.getEstado()));

        return aPedido;
    }

    @Transactional(rollbackFor = NoPlanificableException.class)
    public boolean planificarProductoT(PedidoFabricacion aPedido, Taller aTaller) {
        List<Tarea> tareas = ordenarTareasT(aPedido.getProducto());

        Timestamp fechaTrabajo;

        for (int i = 0; i < aPedido.getCantidad(); i++) {
            fechaTrabajo = aPedido.getFechaEntrega();

            for (Tarea aTarea : tareas) {
                fechaTrabajo = planificarTareaT(aPedido, aTaller, aTarea, fechaTrabajo);

                if (fechaTrabajo.getTime() < aPedido.getFechaPedido().getTime()) {
                    throw new NoPlanificableException();
                }
            }

        }

        return true;

    }

    public Timestamp planificarTareaT(PedidoFabricacion aPedido, Taller aTaller, Tarea aTarea,
            Timestamp aFechaTrabajo) {

        Planificacion aPlanificacion = generarPlanificacionT(aTarea, aTaller, aFechaTrabajo, aPedido);
        if (aPlanificacion == null) {
            throw new NoPlanificableException();
        }

        aPedido.getPlanificaciones().add(aPlanificacion);

        return aPlanificacion.getInicio();
    }

    private Planificacion generarPlanificacionT(Tarea aTarea, Taller aTaller, Timestamp aFechaTrabajo,
            PedidoFabricacion aPedido) {
        List<Hueco> huecos = planificacionHuecoService.huecosTallerTipo(aTarea, aTaller);

        Comparator<Hueco> compararPorHueco = new Comparator<Hueco>() {
            public int compare(Hueco h1, Hueco h2) {
                return Long.compare(h2.getFinHueco().getTime(), h1.getFinHueco().getTime());
            }
        };
        huecos.sort(compararPorHueco);

        Hueco aHueco = primerLugar(aTarea, aTaller, aFechaTrabajo);
        //huecos.stream().filter(hueco= -> esValido(hueco, aTarea, aFechaTrabajo)).findFirst().orElse(null);

        if (aHueco == null)
            return null;

        Planificacion aPlanificacion = new Planificacion();
        aPlanificacion.setEquipo(aHueco.getEquipo());
        aPlanificacion.setTarea(aTarea);
        aPlanificacion.setFin(new Timestamp(Math.min(aHueco.getFinHueco().getTime(), aFechaTrabajo.getTime())));
        aPlanificacion.setInicio(calcularFechaInicio(aHueco.getEquipo(), aTarea, aPlanificacion.getFin()));
        aPlanificacion.setPedido(aPedido);

        planificacionHuecoService.actualizarHuecos(aPlanificacion.getInicio(), aPlanificacion.getFin(), aHueco);

        return aPlanificacion;
    }

    // private boolean esValido(Hueco aHueco, Tarea aTarea, Timestamp aFecha) {

    //     if (aHueco.tiempoHueco() < calcularDuracionTarea(aTarea, aHueco.getEquipo())) {
    //         return false;
    //     }

    //     return (((aHueco.getFinHueco().getTime() <= aFecha.getTime()))
    //             || ((aFecha.getTime() > aHueco.getInicioHueco().getTime())
    //                     && (aFecha.getTime() < aHueco.getFinHueco().getTime())));

    // }

    private boolean fits(Timestamp taskStart, Timestamp gapStart) {
        return taskStart.getTime() >= gapStart.getTime();
    }

    public boolean isUsefulGap(Hueco actualGap, Timestamp lastTaskDate, Timestamp taskStart, Timestamp taskStart2) {
        return ((actualGap.getFinHueco().getTime() <= lastTaskDate.getTime())
                && fits(taskStart, actualGap.getInicioHueco()))
                || ((lastTaskDate.getTime() > actualGap.getInicioHueco().getTime())
                        && (lastTaskDate.getTime() < actualGap.getFinHueco().getTime())
                        && fits(taskStart2, actualGap.getInicioHueco()));
    }

    private Timestamp minusSeconds(Timestamp from, long durationInSeconds) {
        return Timestamp.valueOf(from.toLocalDateTime().minusSeconds(durationInSeconds));
    }

    private Timestamp getStartDate(Timestamp from, int taskTime, double equipmentCapacity) {
        long durationInSeconds = (long) ((taskTime / equipmentCapacity) * 60);

        return minusSeconds(from, durationInSeconds);
    }

    private Hueco primerLugar(Tarea tarea, Taller taller, Timestamp finAnterior) {

        List<Hueco> todas = planificacionHuecoService.huecosTallerTipo(tarea, taller);
        Collections.sort(todas, PlanificadorTardioService::compararPorFecha);
    
        for (Hueco actualGap : todas) {
            if (isUsefulGap(actualGap, finAnterior,
                    getStartDate(actualGap.getFinHueco(),
                            tarea.getTiempo(),
                            actualGap.getEquipo().getCapacidad()),
                    getStartDate(finAnterior,
                            tarea.getTiempo(),
                            actualGap.getEquipo().getCapacidad()))) {

                Hueco result = new Hueco();
                result.setFinHueco(new Timestamp(Math.min(actualGap.getFinHueco().getTime(), finAnterior.getTime())));
                result.setInicioHueco(getStartDate(
                        result.getFinHueco(),
                        tarea.getTiempo(),
                        actualGap.getEquipo().getCapacidad()));
                result.setEquipo(actualGap.getEquipo());

                planificacionHuecoService.actualizarHuecos(result.getInicioHueco(), result.getFinHueco(), actualGap);
                return result;
            }
        }

        return null;
    }


    private static int compararPorFecha(Hueco hueco1, Hueco hueco2) {
        return Long.compare(hueco2.getFinHueco().getTime(), hueco1.getFinHueco().getTime());
    }
    


    public Timestamp calcularFechaInicio(Equipo aEquipo, Tarea aTarea, Timestamp aFecha) {

        long duracion = calcularDuracionTarea(aTarea, aEquipo);

        return new Timestamp(aFecha.getTime() - (duracion * 60000));
    }

    public int calcularDuracionTarea(Tarea aTarea, Equipo aEquipo) {

        return (int) (aTarea.getTiempo() / aEquipo.getCapacidad());
    }

    public List<Tarea> ordenarTareasT(Producto aProducto) {

        List<Tarea> tareasOrdenadas = new ArrayList<Tarea>();

        tareasOrdenadas.addAll(aProducto.getTareas());

        Comparator<Tarea> comparadorPorNumeroOrden = new Comparator<Tarea>() {
            public int compare(Tarea tarea1, Tarea tarea2) {
                return Integer.compare(tarea2.getOrden(), tarea1.getOrden());
            }
        };

        tareasOrdenadas.sort(comparadorPorNumeroOrden);

        return tareasOrdenadas;
    }

}
