package unpsjb.labprog.backend.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.model.Equipo;
import unpsjb.labprog.backend.model.Hueco;
import unpsjb.labprog.backend.model.Planificacion;
import unpsjb.labprog.backend.model.Taller;
import unpsjb.labprog.backend.model.Tarea;
import unpsjb.labprog.backend.model.TipoEquipo;

@Service
public class HuecoService {

    @Autowired
    PlanificacionService planificacionService;

    @Autowired
    PedidoFabricacionService pedidoFabricacionService;

    //private static final Logger logger = LoggerFactory.getLogger(PlanificadorService.class);

    Map<Taller, Map<TipoEquipo, List<Hueco>>> mapaTallerTipoEquipos = new HashMap<Taller, Map<TipoEquipo, List<Hueco>>>();

    Map<TipoEquipo, List<Hueco>> backupRollback = new HashMap<TipoEquipo, List<Hueco>>();

    public void mapearTaller(Taller aTaller) {

        mapaTallerTipoEquipos.put(aTaller, new HashMap<TipoEquipo, List<Hueco>>());

    }

    public void mapearTipoEquipo(Taller aTaller, TipoEquipo aTipoEquipo) {

        List<Hueco> huecosTipoEquipo = new ArrayList<Hueco>();

        List<Equipo> equiposTipoEquipo = new ArrayList<Equipo>();

        equiposTipoEquipo.addAll(aTaller.getEquipos().stream()
                .filter(equipo -> equipo.getTipoEquipo().equals(aTipoEquipo)).collect(Collectors.toList()));

        for (Equipo aEquipo : equiposTipoEquipo) {
            huecosTipoEquipo.addAll(generarHuecosEquipo(aEquipo));
        }

        mapaTallerTipoEquipos.get(aTaller).put(aTipoEquipo, huecosTipoEquipo);
    }

    public List<Hueco> generarHuecosEquipo(Equipo aEquipo) {
        List<Planificacion> planificaciones = new ArrayList<Planificacion>();

        planificaciones.addAll(planificacionService.findByEquipoId(aEquipo.getId()));

        return generarHuecos(planificaciones, aEquipo);
    }

    public List<Hueco> generarHuecos(List<Planificacion> planificaciones, Equipo aEquipo) {
        List<Hueco> huecos = new ArrayList<Hueco>();
        if (planificaciones == null || planificaciones.isEmpty()) {
            huecos.add(new Hueco(pedidoFabricacionService.primeraFechaPedido(),
                    pedidoFabricacionService.ultimaFechaEntrega(), aEquipo));
            return huecos;
        }

        if (planificaciones.size() == 1) {
            if (planificaciones.get(0).getInicio().getTime() > pedidoFabricacionService.primeraFechaPedido()
                    .getTime()) {
                huecos.add(new Hueco(pedidoFabricacionService.primeraFechaPedido(), planificaciones.get(0).getInicio(),
                        aEquipo));
            }

            if (planificaciones.get(0).getFin().getTime() < pedidoFabricacionService.ultimaFechaEntrega().getTime()) {
                huecos.add(new Hueco(planificaciones.get(0).getFin(), pedidoFabricacionService.ultimaFechaEntrega(),
                        aEquipo));
            }
            return huecos;
        }

        if (planificaciones.size() == 2) {
            if (hayHueco(pedidoFabricacionService.primeraFechaPedido(), planificaciones.get(0).getInicio())) {
                huecos.add(new Hueco(pedidoFabricacionService.primeraFechaPedido(), planificaciones.get(0).getInicio(),
                        aEquipo));
            }
            if (hayHueco(planificaciones.get(planificaciones.size() - 1).getFin(),
                    pedidoFabricacionService.ultimaFechaEntrega())) {
                huecos.add(new Hueco(planificaciones.get(planificaciones.size() - 1).getFin(),
                        pedidoFabricacionService.ultimaFechaEntrega(),
                        aEquipo));
            }
            if (hayHueco(planificaciones.get(0).getFin(), planificaciones.get(1).getInicio())) {
                huecos.add(new Hueco(planificaciones.get(0).getFin(), planificaciones.get(1).getInicio(), aEquipo));
            }
            return huecos;
        }

        if (planificaciones.size() > 2) {
            if (hayHueco(pedidoFabricacionService.primeraFechaPedido(), planificaciones.get(0).getInicio())) {
                huecos.add(new Hueco(pedidoFabricacionService.primeraFechaPedido(), planificaciones.get(0).getInicio(),
                        aEquipo));
            }
            for (int i = 0; i < planificaciones.size() - 2; i++) {
                if (hayHueco(planificaciones.get(i).getFin(), planificaciones.get(i + 1).getInicio())) {
                    huecos.add(new Hueco(planificaciones.get(i).getFin(), planificaciones.get(i + 1).getInicio(),
                            aEquipo));
                }
            }
            if (hayHueco(planificaciones.get(planificaciones.size() - 1).getFin(),
                    pedidoFabricacionService.ultimaFechaEntrega())) {
                huecos.add(new Hueco(planificaciones.get(planificaciones.size() - 1).getFin(),
                        pedidoFabricacionService.ultimaFechaEntrega(),
                        aEquipo));
            }
            return huecos;
        }

        return huecos;
    }

    public boolean hayHueco(Timestamp fecha1, Timestamp fecha2) {

        if (fecha1.getTime() < fecha2.getTime()) {
            return true;
        } else {
            return false;
        }

    }

    public Planificacion obtenerHueco(Taller aTaller, Tarea aTarea, Timestamp aFecha) {
        if (!mapaTallerTipoEquipos.containsKey(aTaller)) {
            mapearTaller(aTaller);
        }

        if (!mapaTallerTipoEquipos.get(aTaller).containsKey(aTarea.getTipoEquipo())) {
            mapearTipoEquipo(aTaller, aTarea.getTipoEquipo());
        }

        // for (Hueco hueco :
        // mapaTallerTipoEquipos.get(aTaller).get(aTarea.getTipoEquipo())) {
        // logger.info(hueco.getInicioHueco() + " " + hueco.getFinHueco() + " " +
        // hueco.getEquipo().getCodigo());
        // }

        List<Hueco> huecos = mapaTallerTipoEquipos.get(aTaller).get(aTarea.getTipoEquipo());

        huecos = ordenarHuecos(huecos);
        Hueco aHueco = huecos.stream().filter(hueco -> esCompatible(hueco, aTarea, aFecha)).findFirst().orElse(null);

        if (aHueco == null)
            return null;

        if (aHueco.getInicioHueco().getTime() <= aFecha.getTime()) {
            if (esCompatible(aHueco, aTarea, aFecha)) {

                Planificacion aPlanificacion = new Planificacion();
                aPlanificacion.setTarea(aTarea);
                aPlanificacion.setEquipo(aHueco.getEquipo());
                aPlanificacion.setInicio(aFecha);
                aPlanificacion.setFin(calcularFechaFin(aTarea, aHueco.getEquipo(), aFecha));
                ajustarHuecos(huecos, aHueco, aPlanificacion);
                return aPlanificacion;
            }
        }
        if (aHueco.getInicioHueco().getTime() > aFecha.getTime()) {
            if (aHueco.getFinHueco().getTime() > aFecha.getTime()) {
                if (esCompatible(aHueco, aTarea, aHueco.getInicioHueco())) {

                    Planificacion aPlanificacion = new Planificacion();
                    aPlanificacion.setTarea(aTarea);
                    aPlanificacion.setEquipo(aHueco.getEquipo());
                    aPlanificacion.setInicio(aHueco.getInicioHueco());
                    aPlanificacion.setFin(calcularFechaFin(aTarea, aHueco.getEquipo(), aHueco.getInicioHueco()));

                    ajustarHuecos(huecos, aHueco, aPlanificacion);
                    return aPlanificacion;
                }
            }
        }

        return null;
    }

    public void ajustarHuecos(List<Hueco> huecos, Hueco aHueco, Planificacion aPlanificacion) {

        if (aPlanificacion.getInicio().getTime() == aHueco.getInicioHueco().getTime()) {
            huecos.remove(aHueco);

            aHueco.setInicioHueco(aPlanificacion.getFin());
            huecos.add(aHueco);
        }

        if (aPlanificacion.getFin().getTime() == aHueco.getFinHueco().getTime()) {
            huecos.remove(aHueco);

            aHueco.setFinHueco(aPlanificacion.getInicio());
            huecos.add(aHueco);
        }

        if (aPlanificacion.getInicio().getTime() > aHueco.getInicioHueco().getTime()
                && aPlanificacion.getInicio().getTime() < aHueco.getFinHueco().getTime()) {

            huecos.remove(aHueco);
            Hueco nuevoHueco = new Hueco(aPlanificacion.getFin(), aHueco.getFinHueco(), aHueco.getEquipo());

            aHueco.setFinHueco(aPlanificacion.getInicio());
            huecos.add(aHueco);
            huecos.add(nuevoHueco);

        }
    }

    public void actualizarHuecos(Taller aTaller, TipoEquipo aTipoEquipo, List<Hueco> huecos) {
        mapaTallerTipoEquipos.get(aTaller).put(aTipoEquipo, huecos);
    }

    public boolean esCompatible(Hueco aHueco, Tarea aTarea, Timestamp aFecha) {

        if (aHueco.tiempoHueco() < calcularDuracionTarea(aHueco.getEquipo(), aTarea)) {
            return false;
        }

        if (aHueco.getFinHueco().getTime() < aFecha.getTime()) {
            return false;
        }
        if (calcularFechaFin(aTarea, aHueco.getEquipo(), aFecha).getTime() > aHueco.getFinHueco().getTime()) {
            return false;
        }

        return true;
    }

    public long calcularDuracionTarea(Equipo aEquipo, Tarea aTarea) {
        return (long) (aTarea.getTiempo() / aEquipo.getCapacidad());
    }

    private Timestamp calcularFechaFin(Tarea aTarea, Equipo aEquipo, Timestamp tiempoBase) {
        long duracionMilisegundos = (long) (aTarea.getTiempo() / aEquipo.getCapacidad()) * 60 * 1000;
        Timestamp result = new Timestamp(tiempoBase.getTime() + duracionMilisegundos);
        return result;
    }

   //=======================================

    public void rollback(Taller aTaller) {
        mapaTallerTipoEquipos.remove(aTaller);
        mapearTaller(aTaller);
    }

    public void rollbackBackup(Taller aTaller) {
        mapaTallerTipoEquipos.put(aTaller, backupRollback);
    }

    public void generarBackup(Taller aTaller) {
        if (!mapaTallerTipoEquipos.containsKey(aTaller)) {
            mapearTaller(aTaller);
        }
        backupRollback = mapaTallerTipoEquipos.get(aTaller);
    }

    public void romperTodo() {
        for (Taller aTaller : mapaTallerTipoEquipos.keySet()) {
            mapaTallerTipoEquipos.remove(aTaller);
        }
    }

    // public List<Hueco> ordenarHuecos(List<Hueco> huecos) {

    // huecos.sort((h1, h2) -> h1.getInicioHueco().compareTo(h2.getFinHueco()));

    // return huecos;
    // }

    public List<Hueco> ordenarHuecos(List<Hueco> huecos) {
        huecos.sort(Comparator.comparing(Hueco::getInicioHueco));
        return huecos;
    }


    // public Planificacion obtenerHuecoT(Taller aTaller, Tarea aTarea, Timestamp aFecha) {
    //     if (!mapaTallerTipoEquipos.containsKey(aTaller)) {
    //         mapearTaller(aTaller);
    //     }

    //     if (!mapaTallerTipoEquipos.get(aTaller).containsKey(aTarea.getTipoEquipo())) {
    //         mapearTipoEquipo(aTaller, aTarea.getTipoEquipo());
    //     }

    //     List<Hueco> huecos = mapaTallerTipoEquipos.get(aTaller).get(aTarea.getTipoEquipo());

    //     huecos = ordenarHuecosT(huecos);
    //     Hueco aHueco = huecos.stream().filter(hueco -> esCompatibleT(hueco, aTarea, aFecha)).findFirst().orElse(null);

    //     if (aHueco == null)
    //         return null;

    //     if (aHueco.getFinHueco().getTime() >= aFecha.getTime()) {
    //         if (esCompatibleT(aHueco, aTarea, aFecha)) {

    //             Planificacion aPlanificacion = new Planificacion();
    //             aPlanificacion.setTarea(aTarea);
    //             aPlanificacion.setEquipo(aHueco.getEquipo());
    //             aPlanificacion.setInicio(calcularFechaInicioT(aTarea, aHueco.getEquipo(), aFecha));
    //             aPlanificacion.setFin(aFecha);
    //             ajustarHuecos(huecos, aHueco, aPlanificacion);
    //             return aPlanificacion;
    //         }
    //     }

    //     if (aHueco.getFinHueco().getTime() < aFecha.getTime()) {
    //         if (aHueco.getInicioHueco().getTime() < aFecha.getTime()) {
    //             if (esCompatible(aHueco, aTarea, aHueco.getInicioHueco())) {

    //                 Planificacion aPlanificacion = new Planificacion();
    //                 aPlanificacion.setTarea(aTarea);
    //                 aPlanificacion.setEquipo(aHueco.getEquipo());
    //                 aPlanificacion.setInicio(calcularFechaInicioT(aTarea, aHueco.getEquipo(), aHueco.getFinHueco()));
    //                 aPlanificacion.setFin(aHueco.getFinHueco());

    //                 ajustarHuecos(huecos, aHueco, aPlanificacion);
                    
    //                 return aPlanificacion;
    //             }
    //         }
    //     }

    //     return null;
    // }

    // public List<Hueco> ordenarHuecosT(List<Hueco> huecos) {
    //     huecos.sort((h1, h2) -> h2.getFinHueco().compareTo(h1.getFinHueco()));
    //     return huecos;
    // }

    // public boolean esCompatibleT(Hueco aHueco, Tarea aTarea, Timestamp aFecha) {

    //     if (aHueco.tiempoHueco() < calcularDuracionTarea(aHueco.getEquipo(), aTarea)) {
    //         return false;
    //     }

    //     if (aHueco.getInicioHueco().getTime() > aFecha.getTime()) {
    //         return false;
    //     }
    //     if (calcularFechaInicioT(aTarea, aHueco.getEquipo(), aFecha).getTime() < aHueco.getInicioHueco().getTime()) {
    //         return false;
    //     }

    //     return true;
    // }


    // public long calcularDuracionTareaT(Equipo aEquipo, Tarea aTarea) {
    //     return (long) (aTarea.getTiempo() / aEquipo.getCapacidad());
    // }

    // private Timestamp calcularFechaInicioT(Tarea aTarea, Equipo aEquipo, Timestamp tiempoBase) {
    //     long duracionMilisegundos = (long) (aTarea.getTiempo() / aEquipo.getCapacidad()) * 60 * 1000;
    //     Timestamp result = new Timestamp(tiempoBase.getTime() - duracionMilisegundos);
    //     return result;
    // }

    
















}
