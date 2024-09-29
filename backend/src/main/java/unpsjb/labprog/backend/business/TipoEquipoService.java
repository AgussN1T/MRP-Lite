package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.TipoEquipo;

@Service
public class TipoEquipoService {
    
    @Autowired
    TipoEquipoRepository repository;


    public TipoEquipo findById(int id){
        return repository.findById(id).orElse(null);
    }

    public TipoEquipo findByName(String nombre){
        return repository.findByName(nombre).orElse(null);
    }
    
    public Page<TipoEquipo> findByPage(int page, int size){
        return repository.findAll(PageRequest.of(page, size));
    }

    public List<TipoEquipo>findAll(){
        List<TipoEquipo> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public List<TipoEquipo> search(String term){
        return repository.search("%"+term.toUpperCase()+"%");
    }

    @Transactional
    public TipoEquipo create(TipoEquipo aTipoEquipo){
        return repository.save(aTipoEquipo);
    }

    @Transactional
    public TipoEquipo save(TipoEquipo e){
        return repository.save(e);
    }

    @Transactional
    public void delete(int id){
        repository.deleteById(id);
    }


}
