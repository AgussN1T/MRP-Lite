package unpsjb.labprog.backend.business;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import unpsjb.labprog.backend.model.Equipo;
import unpsjb.labprog.backend.model.Planificacion;
import unpsjb.labprog.backend.model.Taller;
import unpsjb.labprog.backend.model.TipoEquipo;

public interface PlanificacionRepository extends CrudRepository<Planificacion, Integer> {

    @Query("SELECT e FROM Planificacion e WHERE e.equipo.id = ?1")
    List<Planificacion> findByEquipoId(int aEquipoId);

    @Query("SELECT p FROM Planificacion p WHERE p.equipo=:equipo ORDER BY p.inicio ASC")
    List<Planificacion> encontrarDisponibilidad(Equipo equipo);

    // @Query("SELECT p FROM Planificacion p WHERE p.equipo.tipoEquipo=:tipoEquipo AND ORDER BY p.inicio ASC ")
    // List<Planificacion> encontrarDisponibilidadTipoEquipo(@Param("aTaller") Taller aTaller, @Param("aTipoEquipo") TipoEquipo aTipoEquipo);

    @Query("SELECT p FROM Planificacion p WHERE p.equipo IN (SELECT e FROM Equipo e WHERE e.tipoEquipo = :tipoEquipo AND e IN (SELECT t.equipos FROM Taller t WHERE t = :taller))")
    List<Planificacion> encontrarDisponibilidadTipoEquipo(@Param("taller") Taller taller, @Param("tipoEquipo") TipoEquipo tipoEquipo);

    @Query("SELECT e FROM Planificacion e WHERE e.equipo.id = ?1 AND e.inicio BETWEEN ?2 AND ?3")
    List<Planificacion> findByDateAndEquipo(int aEquipoId, Timestamp fechaInicio, Timestamp fechaFin);

    @Query("SELECT e FROM Planificacion e where e.equipo.id = ?1 AND e.inicio >= ?2 AND e.inicio <= ?3")
    List<Planificacion> findByEquipoIdAndFechas(int aEquipoId, Date fechaDesde,Date fechaHasta);

}
