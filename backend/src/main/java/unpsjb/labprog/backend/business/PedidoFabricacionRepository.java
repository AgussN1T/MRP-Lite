package unpsjb.labprog.backend.business;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import unpsjb.labprog.backend.model.PedidoFabricacion;

public interface PedidoFabricacionRepository extends CrudRepository<PedidoFabricacion, Integer>, PagingAndSortingRepository<PedidoFabricacion, Integer>{

        @Query("SELECT MIN(fechaPedido) FROM PedidoFabricacion pf")
        Timestamp getFechaMinima();

        @Query("SELECT MAX(fechaEntrega) FROM PedidoFabricacion pf")
        Timestamp getFechaMaxima();

        @Query("SELECT e FROM PedidoFabricacion e WHERE e.estado = 1")
        List<PedidoFabricacion> recuperarPedidosPlanificados();

        @Query("SELECT e FROM PedidoFabricacion e WHERE e.estado = 2")
        List<PedidoFabricacion> recuperarPedidosNoPlanificados();

        @Query("SELECT p FROM PedidoFabricacion p ORDER BY p.fechaPedido ASC")
        List<PedidoFabricacion> findAllOrderByFechaPedido();
        
        @Query("SELECT p FROM PedidoFabricacion p ORDER BY p.fechaEntrega ASC")
        List<PedidoFabricacion> findAllOrderByFechaEntrega();
}