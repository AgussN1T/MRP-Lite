package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Equipo;


@Service
public class EquipoService {
     @Autowired
    EquipoRepository repository;

    public Equipo findById(int id){
        return repository.findById(id).orElse(null);
    }
    
    public Page<Equipo> findByPage(int page, int size){
        return repository.findAll(PageRequest.of(page, size));
    }

    public List<Equipo>findAll(){
        List<Equipo> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Equipo findByCode(String code){
        return repository.findByCode(code).orElse(null);
    }

    public List<Equipo> search(String term){
        return repository.search("%"+term.toUpperCase()+"%");
    }

    @Transactional
    public Equipo create(Equipo aEquipo){
        return repository.save(aEquipo);
    }

    @Transactional
    public Equipo save(Equipo e){
        return repository.save(e);
    }

    @Transactional
    public void delete(int id){
        repository.deleteById(id);
    }
}
