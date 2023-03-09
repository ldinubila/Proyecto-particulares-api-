package ar.edu.unlam.proyecto.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.proyecto.entidades.UsuarioProfesor;
@Repository
public interface RepositorioDeProfesor extends JpaRepository<UsuarioProfesor, Long> {

	@Query(value = "SELECT * "
	  		+ "FROM profesor "
	  		+ "WHERE (profesor.Usuario_id = ?1)"
				, nativeQuery = true)
	UsuarioProfesor  buscarProfesorPorIdUser(Long user);
}
