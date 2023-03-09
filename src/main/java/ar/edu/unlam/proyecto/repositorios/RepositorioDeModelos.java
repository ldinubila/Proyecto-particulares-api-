package ar.edu.unlam.proyecto.repositorios;
import ar.edu.unlam.proyecto.entidades.Clase;
import ar.edu.unlam.proyecto.entidades.Modelo;
import ar.edu.unlam.proyecto.entidades.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeModelos extends JpaRepository<Modelo, Long> {
	//buscar modelos alumno
	@Query(value = "SELECT *  "
	  		+ "FROM Modelo AS m "
	  		+ "INNER JOIN Nivel AS n  "
	  		+ "ON n.id=m.id_nivel "
	  		+ "INNER JOIN Institucion AS i  "
	  		+ "ON i.id=m.id_institucion "
	  		+ "INNER JOIN Materia AS mt  "
	  		+ "ON mt.id=m.id_materia "
	  		+ "INNER JOIN Carrera AS c  "
	  		+ "ON c.id=m.id_carrera "
	  		+ "WHERE ( m.nombre ilike ?1% "
	  		+ "OR c.nombre ilike ?1% "
	  		+ "OR i.nombre ilike ?1% "
	  		+ "OR mt.nombre ilike ?1% "
	  		+ "OR n.descripcion ilike ?1% "
	  		+ ")"
	  		 +"AND ( m.publico = true)"
				, nativeQuery = true)

	List<Modelo> filtrarBuscarModeloAlumno(String busqueda);
	

	//buscar modelos alumno sin filtros
	@Query("select m from Modelo m where m.publico = true"
			)
	List<Modelo> todosModeloAlumno();
	
	//mis modelos alumno
	

	@Query(value = "select * from Modelo as m "
			+ "FROM Modelo AS m "
	  		+ "INNER JOIN Nivel AS n  "
	  		+ "ON n.id=m.id_nivel "
	  		+ "INNER JOIN Institucion AS i  "
	  		+ "ON i.id=m.id_institucion "
	  		+ "INNER JOIN Materia AS mt  "
	  		+ "ON mt.id=m.id_materia "
	  		+ "INNER JOIN Carrera AS c  "
	  		+ "ON c.id=m.id_carrera "
	  		+ "WHERE ( m.nombre ilike ?1% "
	  		+ "OR c.nombre ilike ?1% "
	  		+ "OR i.nombre ilike ?1% "
	  		+ "OR mt.nombre ilike ?1% "
	  		+ "OR n.descripcion ilike ?1% "
	  		+ ") and "
		            + "(m.id_usuario = ?2) "
			, nativeQuery = true)
	List<Modelo> filtrarMisModeloAlumno(String busqueda,Long id);
	
	

	

	//buscar mis modelos alumno sin filtros
	
	@Query(value = "select * from Modelo as m "
			+ " INNER join OfertaDeResolucion as o "
			+ " on o.id_modelo=m.id "
			+ " where (m.id_usuario = ?1) or Exists(select * from Compra  as c where c.id_producto = o.id) "
				, nativeQuery = true)
	List<Modelo> todosMisModeloAlumno(Long id);

   //buscar modelos particular
	@Query(value =  "select * from Modelo as m "
	  		+ " INNER JOIN Nivel AS n  "
	  		+ " ON n.id=m.id_nivel "
	  		+ " INNER JOIN Institucion AS i  "
	  		+ " ON i.id=m.id_institucion "
	  		+ " INNER JOIN Materia AS mt  "
	  		+ " ON mt.id=m.id_materia "
	  		+ " INNER JOIN Carrera AS c  "
	  		+ " ON c.id=m.id_carrera "
	  		+ " WHERE ( m.nombre ilike ?1% "
	  		+ " OR c.nombre ilike ?1% "
	  		+ " OR i.nombre ilike ?1% "
	  		+ " OR mt.nombre ilike ?1% )"
				, nativeQuery = true)

   List<Modelo> filtrarBuscarModelosParticular(String busqueda);
	  

	
	//buscar  modelos particular sin filtros
 	
	 	@Query(value="SELECT * FROM Modelo m  ",
	 			 nativeQuery = true)
	 	List<Modelo> todosModeloParticular();
	  
    //mis modelos particular

	 	 @Query(value =  "SELECT * "
	   		    + "FROM Modelo AS m "
	   			+ "inner join OfertaDeResolucion as o "
	  			+ "on o.id_modelo = m.id "
	   		    + "INNER JOIN Nivel AS n  "
	   	     	+ "ON n.id=m.id_nivel "
	   	        + "INNER JOIN Carrera AS c  "
	   	     	+ "ON c.id=m.id_carrera "
	   	        + "INNER JOIN Institucion AS i  "
	   	     	+ "ON i.id=m.id_institucion "
	   	     	+ "INNER JOIN Materia AS mt  "
	   	     	+ "ON mt.id=m.id_materia "
	   	     	+ "where o.id_usuario = ?2 "
	   		    + "    AND " 
	   		    + "( m.nombre ilike ?1% "
	   		    + "    OR i.nombre ilike ?1% "
	   		    + "    OR c.nombre ilike ?1% "
	   		    + "    OR mt.nombre ilike ?1% "
	   		    + "    OR n.descripcion ilike ?1% ) "
	  		+" ORDER BY m.fecha Desc"
			, nativeQuery = true)
     List<Modelo> filtrarMisModelosParticular(String busqueda,Long idUser);
  

     
 	//buscar mis modelos particular sin filtros
 	
 	@Query(value="select * from Modelo as m "
 			+ "inner join OfertaDeResolucion as o "
 			+ "on o.id_modelo = m.id "
 			+ "where o.id_usuario = ?1 "
 			+" ORDER BY m.fecha Asc ",
 			 nativeQuery = true)
 	List<Modelo> todosMisModeloParticular(Long id );

 	
 	
 	// pasar a repo usuario
 	@Query(value="SELECT * FROM Usuario m WHERE m.id= ?1",
 			 nativeQuery = true)
 	Usuario encontrarUserId(Long id );

	 @Query("SELECT m FROM Modelo m WHERE m.usuario.id = :idUsuario")
    List<Modelo> obtenerTodosPorIdUsuario(@Param("idUsuario") Long idUsuario);
}
