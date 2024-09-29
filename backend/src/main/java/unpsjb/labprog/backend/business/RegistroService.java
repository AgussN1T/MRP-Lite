package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Registro;

@Service
public class RegistroService {
    
    @Autowired
    RegistroRepository repository;
    
    public Page<Registro> findByPage(int page, int size){
        return repository.findAll(PageRequest.of(page, size));
    }

    public List<Registro>findAll(){
        List<Registro> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    @Transactional
    public Registro create(Registro aRegistro){
        return repository.save(aRegistro);
    }

    @Transactional
    public Registro save(Registro aRegistro){
        return repository.save(aRegistro);
    }

    @Transactional
    public void delete(int id){
        repository.deleteById(id);
    }

}
