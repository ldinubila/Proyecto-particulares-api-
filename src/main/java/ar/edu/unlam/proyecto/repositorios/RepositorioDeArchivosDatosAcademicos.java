package ar.edu.unlam.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.proyecto.entidades.ArchivoDeDatosAcademicos;
import ar.edu.unlam.proyecto.entidades.Clase;
import ar.edu.unlam.proyecto.entidades.DatosAcademicos;
import ar.edu.unlam.proyecto.entidades.FotoPerfil;
import ar.edu.unlam.proyecto.entidades.Usuario;

@Repository
public interface RepositorioDeArchivosDatosAcademicos extends JpaRepository<ArchivoDeDatosAcademicos, Long> {

	 List<ArchivoDeDatosAcademicos> findAllByDatosAcademicos(DatosAcademicos datosAcademicos);
	 
	 
}
