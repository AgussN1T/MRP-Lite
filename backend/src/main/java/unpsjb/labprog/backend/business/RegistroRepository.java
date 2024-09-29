package unpsjb.labprog.backend.business;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import unpsjb.labprog.backend.model.Registro;

public interface RegistroRepository extends CrudRepository<Registro, Integer>, PagingAndSortingRepository<Registro, Integer>{
        
}
