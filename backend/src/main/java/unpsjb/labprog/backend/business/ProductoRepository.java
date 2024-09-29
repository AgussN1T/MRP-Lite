package unpsjb.labprog.backend.business;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import unpsjb.labprog.backend.model.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Integer>, PagingAndSortingRepository<Producto, Integer>{

    @Query("SELECT e FROM Producto e WHERE e.nombre = ?1")
    Optional<Producto> findByName(String name);

    @Query("select e from Producto e where e.nombre ilike ?1")
    List<Producto> search(String term);
    
}