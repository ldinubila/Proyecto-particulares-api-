package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeCarreras extends JpaRepository<Carrera, Long> {
}
