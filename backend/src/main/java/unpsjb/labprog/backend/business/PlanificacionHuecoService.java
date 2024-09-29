package unpsjb.labprog.backend.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.model.Equipo;
import unpsjb.labprog.backend.model.Hueco;
import unpsjb.labprog.backend.model.Planificacion;
import unpsjb.labprog.backend.model.Taller;
import unpsjb.labprog.backend.model.Tarea;

@Service
public class PlanificacionHuecoService {

    @Autowired
    PlanificacionService planificacionService;

    @Autowired
    PedidoFabricacionService pedidoFabricacionService;

    @Autowired
    PlanificacionRepository repository;

    //private static final Logger logger = LoggerFactory.getLogger(PlanificacionService.class);
    
    private Map<Equipo, List<Hueco>> mapa = new HashMap<Equipo, List<Hueco>>();
    
    private Map<Equipo, List<Hueco>> backup = new HashMap<Equipo, List<Hueco>>();
    
    private List<Hueco> obtenerHuecos(Equipo equipo) {

        if (mapa.get(equipo) != null) {
            return mapa.get(equipo);
        }

        List<Hueco> huecos = new ArrayList<>();
        List<Planificacion> planificaciones = repository.encontrarDisponibilidad(equipo);

        Hueco hueco;
        if (planificaciones.isEmpty()) {
            hueco = new Hueco();
            hueco.setEquipo(equipo);
            hueco.setInicioHueco(pedidoFabricacionService.primeraFechaPedido());
            hueco.setFinHueco(pedidoFabricacionService.ultimaFechaEntrega());
            huecos.add(hueco);
            mapa.put(equipo, huecos);
            return huecos;
        }

        Timestamp prevFin = pedidoFabricacionService.primeraFechaPedido();
        for (Planificacion planificacion : planificaciones) {
            if (prevFin.before(planificacion.getInicio())) {
                hueco = new Hueco();
                hueco.setInicioHueco(prevFin);
                hueco.setFinHueco(planificacion.getInicio());
                hueco.setEquipo(equipo);
                huecos.add(hueco);
            }
            prevFin = planificacion.getFin();
        }
        if (prevFin != null) {
            hueco = new Hueco();
            hueco.setInicioHueco(prevFin);
            hueco.setFinHueco(pedidoFabricacionService.ultimaFechaEntrega());
            hueco.setEquipo(equipo);
            huecos.add(hueco);
        }
        mapa.put(equipo, huecos);
        return huecos;
    }

    public void actualizarHuecos(Timestamp inicio, Timestamp fin, Hueco hueco) {
        List<Hueco> listaHuecos = mapa.get(hueco.getEquipo());
        if (listaHuecos == null) {
            listaHuecos = new ArrayList<>();
        } else {
            listaHuecos.remove(hueco);
        }

        Hueco hueco1 = crearPrimerHueco(inicio, hueco);
        Hueco hueco2 = crearSegundoHueco(fin, hueco);

        if (isValidHueco(hueco1)) {
            listaHuecos.add(hueco1);
        }
        if (isValidHueco(hueco2)) {
            listaHuecos.add(hueco2);
        }

        mapa.put(hueco.getEquipo(), listaHuecos);

    }

    private boolean isValidHueco(Hueco hueco) {
        return hueco.getInicioHueco().before(hueco.getFinHueco());
    }

    private Hueco crearPrimerHueco(Timestamp inicio, Hueco hueco) {
        Hueco result = new Hueco();
        result.setEquipo(hueco.getEquipo());
        result.setInicioHueco(hueco.getInicioHueco());
        result.setFinHueco(inicio);

        return result;
    }

    private Hueco crearSegundoHueco(Timestamp fin, Hueco hueco) {
        Hueco result = new Hueco();
        result.setEquipo(hueco.getEquipo());
        result.setInicioHueco(fin);
        result.setFinHueco(hueco.getFinHueco());

        return result;
    }

    public void rollback(List<Planificacion> planificaciones) {
        // recorrer las planificaciones generando huecos en su lugar.
        for (Planificacion p : planificaciones) {
            List<Hueco> huecos = mapa.get(p.getEquipo());
            huecos.add((reemplazar(huecos, p)));
            mapa.put(p.getEquipo(), huecos);
        }
        // luego verificar por huecos contiguos y unirlos. bubble sort?
    }

    private Hueco reemplazar(List<Hueco> huecos, Planificacion p) {
        Hueco nuevo = new Hueco();
        nuevo.setEquipo(p.getEquipo());
        nuevo.setInicioHueco(p.getInicio());
        nuevo.setFinHueco(p.getFin());
        return nuevo;
    }

    public List<Hueco> huecosTallerTipo(Tarea unaTarea, Taller unTaller) {
        List<Hueco> result = new ArrayList<>();
        List<Equipo> equipos = (List<Equipo>) unTaller.getEquipos();
        equipos.stream()
                .filter(equipo -> unaTarea.getTipoEquipo().equals(equipo.getTipoEquipo()))
                .forEach(equipo -> result.addAll(obtenerHuecos(equipo)));
        return result;
    }

    public void iniciar() {
        this.mapa = new HashMap<Equipo, List<Hueco>>();
    }
    
    public void rollback(){
        this.mapa = this.backup;
    }

    public void generarBackup(){
        this.backup = this.mapa;
    }
}
