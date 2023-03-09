package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.Clase;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeClases extends JpaRepository<Clase, Long> {
	
	@Query(value = "SELECT c.id, c.nombre,c.metodo,c.modo,c.precio,c.cupo,c.descripcion,c.fecha,d.estado,c.puntuacion,c.id_materia, c.id_nivel,c.id_profesor,c.id_modelo "
	  		+ "FROM Clase AS c "
	  		+ "INNER JOIN DetalleClase AS d "
	  		+ "ON c.id = d.id_clase "
	  		+ "WHERE (c.nombre ILIKE ?1%)"
	  		+ "and (c.id_modelo is null)"
	  		+ "and (d.estado ilike 'DISPONIBLE')"
	  		+" GROUP BY c.id, c.nombre,c.metodo,c.modo,c.precio,c.cupo,c.descripcion,c.fecha,d.estado,c.puntuacion,c.id_materia, c.id_nivel,c.id_profesor,c.id_modelo"
				, nativeQuery = true)
	List<Clase> filtrarPorNombre(String busqueda);
	
	
	@Query(value = "SELECT c.id, c.nombre,c.metodo,c.modo,c.precio,c.cupo,c.descripcion,c.fecha,d.estado,c.puntuacion,c.id_materia, c.id_nivel,c.id_profesor,c.id_modelo "
	  		+ "FROM Clase AS c "
	  		+ "INNER JOIN DetalleClase AS d "
	  		+ "ON c.id = d.id_clase "
	  		+ "WHERE (c.nombre ILIKE ?1%)"
	  		+ "or (c.metodo ILIKE ?2)"
	  		+ "or (c.id_nivel = ?3)"
	  		+ "or (c.modo = ?4)"
	  		+ "and (c.id_modelo is null)"
	  		+ "and (d.estado ilike 'DISPONIBLE')"
	  		+" GROUP BY c.id, c.nombre,c.metodo,c.modo,c.precio,c.cupo,c.descripcion,c.fecha,d.estado,c.puntuacion,c.id_materia, c.id_nivel,c.id_profesor,c.id_modelo"
				, nativeQuery = true)
	List<Clase> filtrarFecha(String busqueda,String metodoClase, Long nivel,String modo);
	
	
	@Query(value = "SELECT c.id, c.nombre,c.metodo,c.modo,c.precio,c.cupo,c.descripcion,c.fecha,d.estado,c.puntuacion,c.id_materia, c.id_nivel,c.id_profesor,c.id_modelo "
	  		+ "FROM Clase AS c "
	  		+ "INNER JOIN DetalleClase AS d "
	  		+ "ON c.id = d.id_clase "
	  		+ "WHERE (d.estado ilike 'DISPONIBLE') "
	  		+ "and (c.id_modelo is null)"
	  		+" GROUP BY c.id, c.nombre,c.metodo,c.modo,c.precio,c.cupo,c.descripcion,c.fecha,d.estado,c.puntuacion,c.id_materia, c.id_nivel,c.id_profesor,c.id_modelo"
				, nativeQuery = true)
	List<Clase> devolverTodos();
	
	@Query("select c from Clase c where c.Profesor.id = :idParticular")
	List<Clase> filtrarPorParticular(@Param("idParticular") Long particularId);

}
