package unpsjb.labprog.backend.presenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.PedidoFabricacionService;
import unpsjb.labprog.backend.business.PlanificadorService;
import unpsjb.labprog.backend.business.PlanificadorTardioService;
import unpsjb.labprog.backend.business.ProductoNotFoundException;
import unpsjb.labprog.backend.model.PedidoFabricacion;
import unpsjb.labprog.backend.model.Taller;

@RestController
@RequestMapping("planificador")
public class PlanificadorPresenter {

    @Autowired
    PlanificadorService planificadorService;

    @Autowired
    PedidoFabricacionService pedidoService;

    @Autowired
    PlanificadorTardioService planificadorTardioService;

    @RequestMapping(value = "productos/recuperarTallerPertinente/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<Object> recuperarTallerPertinente(@PathVariable("nombre") String nombre) {
        Taller aTallerOrNull;
        try {
            aTallerOrNull = planificadorService.recuperarTallerPertinente(nombre);
        } catch (ProductoNotFoundException ex) {
            return Response.notImplemented("El producto no existe");
        }

        return (aTallerOrNull != null) ? Response.ok(aTallerOrNull, "Taller recuperado exitosamente")
                : Response.notFound("Taller no encontrado");
    }

    @RequestMapping(value = "productos/recuperarTalleresPertinentes/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<Object> recuperarTalleresPertinentes(@PathVariable("nombre") String nombre) {
        List<Taller> aTalleresOrNull;
        try {
            aTalleresOrNull = planificadorService.recuperarTalleresPertinentes(nombre);
        } catch (ProductoNotFoundException ex) {
            return Response.notImplemented("El producto no existe");
        }

        return (aTalleresOrNull != null) ? Response.ok(aTalleresOrNull, "Talleres recuperados exitosamente")
                : Response.notFound("Taller no encontrado");
    }

    @RequestMapping(value = "pedidos/planificarPedidoPronta/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> planificarPedido(@PathVariable("id") int id) {

        PedidoFabricacion aPedidoOrNull = pedidoService.findById(id);

        aPedidoOrNull = planificadorService.planificarPedido(aPedidoOrNull);

        return Response.ok(aPedidoOrNull, "Pedido planificado con éxito");
    }

    @RequestMapping(value = "pedidos/planificarPedidoTardia/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> planificarPedidoT(@PathVariable("id") int id) {

        PedidoFabricacion aPedidoOrNull = pedidoService.findById(id);

        aPedidoOrNull = planificadorTardioService.planificarPedido(aPedidoOrNull);

        return Response.ok(aPedidoOrNull, "Pedido planificado con éxito");
    }

    @RequestMapping(value = "pedidos/planificarTodoPronta", method = RequestMethod.GET)
    public ResponseEntity<Object> planificarTodoPronta() {

        this.planificadorService.planificarTodo();

        return Response.ok("Pedido planificado con éxito");
    }

    @RequestMapping(value = "pedidos/planificarTodoTardia", method = RequestMethod.GET)
    public ResponseEntity<Object> planificarTodoTardia() {

        this.planificadorTardioService.planificarTodo();

        return Response.ok("Pedido planificado con éxito");
    }
}