package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeNiveles extends JpaRepository <Nivel, Long> {

}
