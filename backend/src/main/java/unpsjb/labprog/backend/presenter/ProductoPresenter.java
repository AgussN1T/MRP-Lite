package unpsjb.labprog.backend.presenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.ProductoNotFoundException;
import unpsjb.labprog.backend.business.ProductoService;
import unpsjb.labprog.backend.model.Producto;
import unpsjb.labprog.backend.model.Taller;

@RestController
@RequestMapping("productos")
public class ProductoPresenter {
 
    @Autowired
    ProductoService service;


    @RequestMapping(value = "recuperarTallerPertinente/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<Object> recuperarTallerPertinente(@PathVariable("nombre") String nombre){
        Taller aTallerOrNull;
        try{
            aTallerOrNull = service.recuperarTallerPertinente(nombre);
        }
        catch(ProductoNotFoundException ex){
            return Response.notImplemented("El producto no existe");
        }

        return (aTallerOrNull != null)?
            Response.ok(aTallerOrNull, "Taller recuperado exitosamente"):
            Response.notFound("Taller no encontrado");    
    }

    @RequestMapping(value = "recuperarTalleresPertinentes/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<Object> recuperarTalleresPertinentes(@PathVariable("nombre") String nombre){
        List<Taller> aTalleresOrNull;
        try{
            aTalleresOrNull = service.recuperarTalleresPertinentes(nombre);
        }
        catch(ProductoNotFoundException ex){
            return Response.notImplemented("El producto no existe");
        }

        return (aTalleresOrNull != null)?
            Response.ok(aTalleresOrNull,"Talleres recuperados exitosamente"):
            Response.notFound("Taller no encontrado");    
    }


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
        Producto aProductoOrNull = service.findById(id);
        return (aProductoOrNull != null)?
            Response.ok(aProductoOrNull):
            Response.notFound("Producto id " + id + " no encontrado");
    }

    @RequestMapping(value="/nombre/{nombre}", method=RequestMethod.GET)
    public ResponseEntity<Object> findByName(@PathVariable("nombre") String nombre){
        Producto aProductoOrNull = service.findByName(nombre);
        return (aProductoOrNull != null)?
            Response.ok(aProductoOrNull):
            Response.notFound("Producto nombre " + nombre + " no encontrado");
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term){
        return Response.ok(service.search(term));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Producto aProducto){
        if ( aProducto.getId() != 0){
            return Response.error(aProducto,"Está intentando crear un Producto, éste no puede tener un id indefinido.");
        }
        return Response.ok(service.save(aProducto),"Producto "+ aProducto.getNombre() + " ingresado correctamente");
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Producto aProducto){
        if ( aProducto.getId() <= 0){
            return Response.error(aProducto,"debe especificar un id válido para poder modificar el Producto");
        }
        return Response.ok(service.save(aProducto),"Producto " + aProducto.getNombre() + " actualizado correctamente");
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id){
        service.delete(id);
        return Response.ok("Producto " + id + " borrado con éxito.");
    }

}
