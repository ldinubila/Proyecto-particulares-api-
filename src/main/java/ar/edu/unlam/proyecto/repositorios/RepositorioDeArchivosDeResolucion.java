package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.ArchivoDeResolucion;
import ar.edu.unlam.proyecto.entidades.Resolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioDeArchivosDeResolucion extends JpaRepository<ArchivoDeResolucion, Long> {
    List<ArchivoDeResolucion> findByResolucion(Resolucion resolucion);
}
