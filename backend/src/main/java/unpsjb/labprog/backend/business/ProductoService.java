package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Equipo;
import unpsjb.labprog.backend.model.Producto;
import unpsjb.labprog.backend.model.Taller;
import unpsjb.labprog.backend.model.Tarea;
import unpsjb.labprog.backend.model.TipoEquipo;

@Service
public class ProductoService {
    
    @Autowired
    ProductoRepository repository;

    @Autowired
    TallerService tallerService;

    public Taller recuperarTallerPertinente(String nombreProducto){
        
        Producto producto = this.findByName(nombreProducto);
        if(producto == null)throw new ProductoNotFoundException();

        List<Taller> talleres = tallerService.findAllOrderByCode();
        
        Set<TipoEquipo> productoTipoEquipos = 
        producto.getTareas().stream()
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

    public List<Taller> recuperarTalleresPertinentes(String nombreProducto){
        
        Producto producto = this.findByName(nombreProducto);
        if(producto == null)throw new ProductoNotFoundException();

        List<Taller> talleres = tallerService.findAllOrderByCode();
        
        Set<TipoEquipo> productoTipoEquipos = 
        producto.getTareas().stream()
        .map(Tarea::getTipoEquipo)
        .collect(Collectors.toSet());

        List<Taller> aTalleresOrNull = talleres.stream()
        .filter(taller -> taller.getEquipos().stream()
            .map(Equipo::getTipoEquipo)
            .collect(Collectors.toSet())
            .containsAll(productoTipoEquipos))
            .collect(Collectors.toList());

        if(aTalleresOrNull.isEmpty()) return null;
        
        return aTalleresOrNull;
    }



    public Producto findById(int id){
        return repository.findById(id).orElse(null);
    }
    
    public Page<Producto> findByPage(int page, int size){
        return repository.findAll(PageRequest.of(page, size));
    }

    public List<Producto>findAll(){
        List<Producto> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }


    public Producto findByName(String nombre){
        return repository.findByName(nombre).orElse(null);
    }

    public List<Producto> search(String term){
        return repository.search("%"+term.toUpperCase()+"%");
    }

    @Transactional
    public Producto create(Producto aProducto){
        return repository.save(aProducto);
    }

    @Transactional
    public Producto save(Producto e){
        return repository.save(e);
    }

    @Transactional
    public void delete(int id){
        repository.deleteById(id);
    }


}
