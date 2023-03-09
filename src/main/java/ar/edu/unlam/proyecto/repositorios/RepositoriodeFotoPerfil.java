package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.FotoPerfil;
import ar.edu.unlam.proyecto.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoriodeFotoPerfil extends JpaRepository<FotoPerfil, Long> {

    List<FotoPerfil> findAllByUsuario(Usuario usuario);
    
	@Query(value = "select * "
			+ "from FotoPerfil "
	  		+ "where (FotoPerfil.id_usuario = ?1)"
				, nativeQuery = true)
	FotoPerfil buscarImagenPorUserId(Long id);

}
