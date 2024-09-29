package unpsjb.labprog.backend.business;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import unpsjb.labprog.backend.model.Tarea;

public interface TareaRepository extends CrudRepository<Tarea, Integer>, PagingAndSortingRepository<Tarea, Integer>{

    @Query("select e from Tarea e where e.nombre ilike ?1")
    List<Tarea> search(String term);
    
}