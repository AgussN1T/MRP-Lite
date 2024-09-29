package unpsjb.labprog.backend.business;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import unpsjb.labprog.backend.model.TipoEquipo;

public interface TipoEquipoRepository extends CrudRepository<TipoEquipo, Integer>, PagingAndSortingRepository<TipoEquipo, Integer>{
    @Query("select e from TipoEquipo e where e.nombre ilike ?1")
    List<TipoEquipo> search(String term);

    @Query("select e from TipoEquipo e where e.nombre like ?1")
    Optional<TipoEquipo> findByName(String nombre);
}

