package unpsjb.labprog.backend.business;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.model.Equipo;
import unpsjb.labprog.backend.model.Producto;
import unpsjb.labprog.backend.model.Taller;
import unpsjb.labprog.backend.model.Tarea;
import unpsjb.labprog.backend.model.TipoEquipo;

@Service
public class TallerPertinenteService {

    @Autowired
    TallerService tallerService;
    @Autowired
    ProductoService productoService;

    public Taller primerTallerPertinente(Producto producto) {
        return (!todosTallerPertinente(producto).isEmpty())
                ? todosTallerPertinente(producto).get(0)
                : null;
    }

    public List<Taller> todosTallerPertinente(Producto producto) {
        Set<TipoEquipo> equiposProducto = producto.getTareas().stream()
                .map(Tarea::getTipoEquipo)
                .collect(Collectors.toSet());

        List<Taller> talleres = tallerService.findAllOrderByCode();

        List<Taller> aTallerOrNull = talleres.stream()
                .filter(taller -> taller.getEquipos()
                        .stream()
                        .map(Equipo::getTipoEquipo)
                        .collect(Collectors.toSet())
                        .containsAll(equiposProducto))
                .collect(Collectors.toList());

        return (aTallerOrNull != null) ? aTallerOrNull : null;
    }

    private boolean contieneNecesarios(Taller taller, Set<TipoEquipo> equiposProducto) {
        return taller.getEquipos()
                .stream()
                .map(Equipo::getTipoEquipo)
                .collect(Collectors.toSet())
                .containsAll(equiposProducto);
    }

    public List<Taller> talleresOrdenados(Producto producto) {
        Set<TipoEquipo> equiposProducto = producto.getTareas().stream()
                .map(Tarea::getTipoEquipo)
                .collect(Collectors.toSet());

        List<Taller> talleres = tallerService.findAllOrderByCode();
        List<Taller> aTallerOrNull = talleres.stream()
                .filter(taller -> contieneNecesarios(taller, equiposProducto))
                .collect(Collectors.toList());
        return (aTallerOrNull != null) ? aTallerOrNull : null;
    }

    private Set<TipoEquipo> getTipoEquipoByProduct(Producto producto) {
        return producto.getTareas().stream().map(Tarea::getTipoEquipo)
                .collect(Collectors.toSet());
    }

    public List<Taller> talleresPorEficiencia(Producto producto) {
        return orderByCapacityAvg(todosTallerPertinente(producto), producto);
    }

    private List<Taller> orderByCapacityAvg(List<Taller> allTallers, Producto producto) {
        Set<TipoEquipo> setProductTipoEquipos = getTipoEquipoByProduct(producto);
        allTallers.sort((w1, w2) -> compareByAverageCapacity(w1, w2, setProductTipoEquipos));
        return allTallers;
    }

    private int compareByAverageCapacity(Taller w1, Taller w2, Set<TipoEquipo> setProductTipoEquipos) {
        double avgCapacity1 = w1.getEquipos().stream()
                .filter(e -> setProductTipoEquipos.contains(e.getTipoEquipo()))
                .mapToDouble(Equipo::getCapacidad)
                .average()
                .orElse(0);
        double avgCapacity2 = w2.getEquipos().stream()
                .filter(e -> setProductTipoEquipos.contains(e.getTipoEquipo()))
                .mapToDouble(Equipo::getCapacidad)
                .average()
                .orElse(0);
        return Double.compare(avgCapacity2, avgCapacity1);
    }

}
