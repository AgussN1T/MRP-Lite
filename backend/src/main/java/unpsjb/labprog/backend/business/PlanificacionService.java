package unpsjb.labprog.backend.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Equipo;
import unpsjb.labprog.backend.model.Hueco;
import unpsjb.labprog.backend.model.Planificacion;
import unpsjb.labprog.backend.model.Taller;
import unpsjb.labprog.backend.model.TipoEquipo;

@Service
public class PlanificacionService {

    @Autowired
    PlanificacionRepository repository;

    @Autowired
    PedidoFabricacionService pedidoFabricacionService;

    // Map<Integer, List<Planificacion>> mapaPlanificaciones = new
    // ConcurrentHashMap<Integer, List<Planificacion>>();

    public List<Planificacion> findByEquipoId(int aEquipoId) {
        return repository.findByEquipoId(aEquipoId);
    }
    
    public List<Planificacion> findByEquipoIdAndFechas(int aEquipoId,Date fechaDesde, Date fechaHasta) {
        return repository.findByEquipoIdAndFechas(aEquipoId, fechaDesde, fechaHasta);
    }

    public List<Planificacion> findAll() {
        List<Planificacion> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    @Transactional
    public Planificacion create(Planificacion aPlanificacion) {
        return repository.save(aPlanificacion);
    }

    @Transactional
    public Planificacion save(Planificacion e) {
        return repository.save(e);
    }

    public void saveAll(Collection<Planificacion> collection){
        repository.saveAll(collection);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<Planificacion> encontrarDisponibilidad(Equipo aEquipo) {
        return repository.encontrarDisponibilidad(aEquipo);
    }

    public List<Hueco> obtenerHuecos(Equipo aEquipo) {
        List<Hueco> huecos = new ArrayList<>();

        List<Planificacion> planificaciones = encontrarDisponibilidad(aEquipo);

        Timestamp prevFin = null;

        for (Planificacion planificacion : planificaciones) {
            if (prevFin == null || prevFin.before(planificacion.getInicio())) {
                Timestamp inicioHueco = (prevFin == null) ? pedidoFabricacionService.primeraFechaPedido() : prevFin;
                Timestamp finHueco = planificacion.getInicio();
                huecos.add(new Hueco(inicioHueco, finHueco, null));
            }
            prevFin = planificacion.getFin();
        }
        if (prevFin != null) {
            Timestamp inicioHueco = prevFin;
            Timestamp finHueco = pedidoFabricacionService.ultimaFechaEntrega();
            huecos.add(new Hueco(inicioHueco, finHueco, null));
        }
        return huecos;
    }

    public void agotarTaller(Taller aTaller) {

        for (Equipo equipo : aTaller.getEquipos()) {
            Planificacion aPlanificacion = new Planificacion();
            aPlanificacion.setTarea(null);
            aPlanificacion.setEquipo(equipo);
            aPlanificacion.setInicio(pedidoFabricacionService.primeraFechaPedido());
            aPlanificacion.setFin(pedidoFabricacionService.ultimaFechaEntrega());
            repository.save(aPlanificacion);
        }
            
    }

    public List<Planificacion> encontrarPlanificacionesTipoEquipo(Taller aTaller, TipoEquipo aTipoEquipo){
        return repository.encontrarDisponibilidadTipoEquipo(aTaller, aTipoEquipo);
    }

}
