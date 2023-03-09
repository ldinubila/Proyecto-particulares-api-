package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.DatosAcademicos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeDatosAcademicos extends JpaRepository <DatosAcademicos, Long> {

	@Query(value = "SELECT *"
	  		+ "FROM datos_academicos AS d "
	  		+ "WHERE (d.profesor_id = ?1)"
				, nativeQuery = true)
	List<DatosAcademicos>  buscarProfesorPorIdUser(Long user);
}
