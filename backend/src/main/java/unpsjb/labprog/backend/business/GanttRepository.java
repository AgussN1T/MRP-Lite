package unpsjb.labprog.backend.business;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import unpsjb.labprog.backend.model.Equipo;
import unpsjb.labprog.backend.model.GanttObjectPlanificacion;
import unpsjb.labprog.backend.model.Planificacion;
import unpsjb.labprog.backend.model.Taller;

public interface GanttRepository extends CrudRepository<Planificacion, Integer>{
    @Query("SELECT new unpsjb.labprog.backend.model.GanttObjectPlanificacion(p.equipo, p.pedido, p.inicio, p.fin) "
+
"FROM Taller w " +
"JOIN w.equipos we " +
"JOIN Equipo e ON we.id = e.id " +
"JOIN Planificacion p ON e.id = p.equipo.id " +
"WHERE w = :taller AND e = :equipo AND p.fin >= :from AND p.inicio <= :to " +
"ORDER BY e.codigo, p.inicio ASC")
List<GanttObjectPlanificacion> getGanttPlanifications(@Param("taller") Taller taller,
@Param("equipo") Equipo equipo,
@Param("from") Timestamp from,
@Param("to") Timestamp to);

@Query("SELECT min(p.inicio) " +
"FROM Taller w " +
"JOIN w.equipos we " +
"JOIN Equipo e ON we.id = e.id " +
"JOIN Planificacion p ON e.id = p.equipo.id " +
"WHERE w = :taller AND p.fin >= :from AND p.inicio <= :to ")
Timestamp getMinStart(@Param("taller") Taller taller,
@Param("from") Timestamp from,
@Param("to") Timestamp to);

@Query("SELECT max(p.fin) " +
"FROM Taller w " +
"JOIN w.equipos we " +
"JOIN Equipo e ON we.id = e.id " +
"JOIN Planificacion p ON e.id = p.equipo.id " +
"WHERE w = :taller AND p.fin >= :from AND p.inicio <= :to ")
Timestamp getMaxFinish(@Param("taller") Taller taller,
@Param("from") Timestamp from,
@Param("to") Timestamp to);

@Query("SELECT w " +
"FROM Taller w " +
"JOIN w.equipos we " +
"JOIN Equipo e ON we.id = e.id " +
"JOIN Planificacion p ON e.id = p.equipo.id " +
"GROUP BY w.id, w.codigo , w.nombre")
List<Taller> getPlanificatedWorkshops();



}
