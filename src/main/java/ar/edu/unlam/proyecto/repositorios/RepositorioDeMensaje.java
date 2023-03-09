package ar.edu.unlam.proyecto.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.proyecto.entidades.Mensaje;
import ar.edu.unlam.proyecto.entidades.Modelo;
import ar.edu.unlam.proyecto.entidades.Usuario;
@Repository
public interface RepositorioDeMensaje extends JpaRepository<Mensaje, Long> {

	List<Mensaje> findByReceptor(Usuario receptor);

	List<Mensaje> findByEmisor(Usuario emisor);
 
	@Query("select m from Mensaje m where (m.emisor.id = ?1  AND m.eliminadoEmisor = true  ) OR (  m.receptor.id = ?1 AND m.eliminadoReceptor = true)"
			+" ORDER BY m.fecha Desc")
	List<Mensaje> eliminados(Long id);
	
	
	@Query("select m from Mensaje m where  ( m.eliminadoReceptor = false and m.respuesta != null) or (m.receptor.id = ?1)"
			+" ORDER BY m.fecha Desc")
	List<Mensaje> recibidos(long id);
	
	
	@Query("select m from Mensaje m where  (  m.emisor.id = ?1 AND m.eliminadoEmisor = false)"
			+" ORDER BY m.fecha Desc")
	List<Mensaje> enviados(Long id);
	
	
	@Query("select m from Mensaje m where  (  m.emisor.id = ?1 AND  m.receptor.id = ?2 AND m.eliminadoEmisor = false AND m.asunto = ?3)"
    +" OR  ( m.emisor.id = ?2 AND  m.receptor.id = ?1 AND m.eliminadoEmisor = false AND m.asunto = ?3)"
			+" ORDER BY m.fecha Asc")
	List<Mensaje> respuestas(Long id1,Long id2,String asunto);

	@Query("select m from Mensaje m where  (  m.emisor.id = ?1 AND  m.receptor.id = ?2  AND m.asunto = ?3)"
		    +" OR  ( m.emisor.id = ?2 AND  m.receptor.id = ?1  AND m.asunto = ?3)"
					+" ORDER BY m.fecha Asc")
			List<Mensaje> respuestasEliminar(Long id1,Long id2,String asunto);
	@Query("select m from Mensaje m where  (  m.receptor.id = ?1 AND m.eliminadoEmisor = false)"
			+" ORDER BY m.fecha Desc")
	List<Mensaje> noLeidos(Long id);
	
}
