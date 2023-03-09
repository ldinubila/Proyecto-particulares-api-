package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeMaterias extends JpaRepository<Materia, Long> {

}
