package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Taller;

@Service
public class TallerService {
    
    @Autowired
    TallerRepository repository;

    public Taller findById(int id){
        return repository.findById(id).orElse(null);
    }
    
    public Page<Taller> findByPage(int page, int size){
        return repository.findAll(PageRequest.of(page, size));
    }

    public Taller findByCode(String code){
        return repository.findByCode(code).orElse(null);
    }

    public List<Taller>findAll(){
        List<Taller> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public List<Taller>findAllOrderByCode(){
        List<Taller> result = new ArrayList<>();
        repository.findAll(Sort.by(Sort.Direction.ASC, "codigo")).forEach(e -> result.add(e));
        return result;
    }


    public List<Taller> search(String term){
        return repository.search("%"+term.toUpperCase()+"%");
    }

    @Transactional
    public Taller create(Taller aTaller){
        return repository.save(aTaller);
    }

    @Transactional
    public Taller save(Taller e){
        return repository.save(e);
    }

    @Transactional
    public void delete(int id){
        repository.deleteById(id);
    }

}
