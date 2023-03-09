package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.Institucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeInstituciones extends JpaRepository<Institucion, Long> {
}
