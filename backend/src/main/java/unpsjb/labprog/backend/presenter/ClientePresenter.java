package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.ClienteService;
import unpsjb.labprog.backend.model.Cliente;

@RestController
@RequestMapping("clientes")
public class ClientePresenter {
    
    @Autowired
    ClienteService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object>findAll(){
        return Response.ok(service.findAll());
    }

    @RequestMapping(value="/page", method=RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size){
            return Response.ok(service.findByPage(page, size));
    }

    @RequestMapping(value="/id/{id}", method=RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id){
        Cliente aClienteOrNull = service.findById(id);
        return (aClienteOrNull != null)?
            Response.ok(aClienteOrNull):
            Response.notFound("Cliente id " + id + " no encontrado");
    }

    @RequestMapping(value="/cuit/{cuit}", method=RequestMethod.GET)
    public ResponseEntity<Object> findByCUIT(@PathVariable("cuit") long cuit){
        Cliente aClienteOrNull = service.findByCUIT(cuit);
        return (aClienteOrNull != null)?
            Response.ok(aClienteOrNull):
            Response.notFound("Cliente cuit " + cuit + " no encontrado");
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term){
        return Response.ok(service.search(term));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Cliente aCliente){
        if ( aCliente.getId() != 0){
            return Response.error(aCliente,"Está intentando crear un cliente, éste no puede tener un id indefinido.");
        }
        return Response.ok(service.save(aCliente),"Cliente "+ aCliente.getRazonSocial() + " ("+aCliente.getCuit()+") registrado correctamente");
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Cliente aCliente){
        if ( aCliente.getId() <= 0){
            return Response.error(aCliente,"debe especificar un id válido para poder modificar el cliente");
        }
        return Response.ok(service.save(aCliente));
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id){
        service.delete(id);
        return Response.ok("Cliente " + id + " borrado con éxito.");
    }



}
