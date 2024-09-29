package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Cliente;

@Service
public class ClienteService {
    
    @Autowired
    ClienteRepository repository;

    public Cliente findById(int id){
        return repository.findById(id).orElse(null);
    }
    
    public Page<Cliente> findByPage(int page, int size){
        return repository.findAll(PageRequest.of(page, size));
    }

    public List<Cliente>findAll(){
        List<Cliente> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Cliente findByCUIT(long cuit){
        return repository.findByCUIT(cuit).orElse(null);
    }

    public List<Cliente> search(String term){
        return repository.search("%"+term.toUpperCase()+"%");
    }

    @Transactional
    public Cliente create(Cliente aCliente){
        return repository.save(aCliente);
    }

    @Transactional
    public Cliente save(Cliente e){
        return repository.save(e);
    }

    @Transactional
    public void delete(int id){
        repository.deleteById(id);
    }

}
