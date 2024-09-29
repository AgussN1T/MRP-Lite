package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Tarea;

@Service
public class TareaService {
    
    @Autowired
    TareaRepository repository;

    public Tarea findById(int id){
        return repository.findById(id).orElse(null);
    }
    
    public Page<Tarea> findByPage(int page, int size){
        return repository.findAll(PageRequest.of(page, size));
    }

    public List<Tarea>findAll(){
        List<Tarea> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public List<Tarea> search(String term){
        return repository.search("%"+term.toUpperCase()+"%");
    }

    @Transactional
    public Tarea create(Tarea aTarea){
        return repository.save(aTarea);
    }

    @Transactional
    public Tarea save(Tarea e){
        return repository.save(e);
    }

    @Transactional
    public void delete(int id){
        repository.deleteById(id);
    }

}
