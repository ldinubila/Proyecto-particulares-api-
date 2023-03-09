package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.TipoDeDemora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeDemoras extends JpaRepository<TipoDeDemora, Long> {
}
