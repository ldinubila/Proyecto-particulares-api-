package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeUsuarios extends JpaRepository<Usuario, Long> {
	
	@Query("select u from Usuario u where (u.Email LIKE ?1)")
	List<Usuario> filtrarPorMail(String usuario);
	
	@Query("select u from Usuario u where u.Email = ?1 and u.contrasenia = ?2 and u.bloqueado = ?3")
	Usuario login(String emailId, String password,Boolean bloqueado);

	@Query("select u from Usuario u where (u.Email = ?1)")
	Usuario nuestroUsuario(String usuario);
	
	@Query(value = "select * from Usuario where rol != 'administrador'"
				, nativeQuery = true)
	List<Usuario> devolverUsuarios();
	
	@Query(value = "select * "
			+ "from Usuario "
	  		+ "where (Usuario.nombre LIKE ?1% or Usuario.apellido LIKE ?1% or Usuario.Email LIKE ?1%)"
	  		+ "and (rol != 'administrador')"
				, nativeQuery = true)
	List<Usuario> filtrarPorNombre(String busqueda);

}
