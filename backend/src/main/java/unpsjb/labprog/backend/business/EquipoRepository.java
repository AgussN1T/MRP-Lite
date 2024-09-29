package unpsjb.labprog.backend.business;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import unpsjb.labprog.backend.model.Equipo;

public interface EquipoRepository extends CrudRepository<Equipo, Integer>, PagingAndSortingRepository<Equipo, Integer>{
    
    @Query("SELECT e FROM Equipo e WHERE e.codigo = ?1")
    Optional<Equipo> findByCode(String code);

    @Query("select e from Equipo e where e.codigo ilike ?1")
    List<Equipo> search(String term);

}