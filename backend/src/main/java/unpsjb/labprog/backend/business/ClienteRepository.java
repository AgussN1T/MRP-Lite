package unpsjb.labprog.backend.business;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import unpsjb.labprog.backend.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer>, PagingAndSortingRepository<Cliente, Integer>{

    @Query("select e from Cliente e where e.razonSocial ilike ?1")
    List<Cliente> search(String term);

    @Query("select e from Cliente e where e.cuit = ?1")
    Optional<Cliente> findByCUIT(long cuit);
}
