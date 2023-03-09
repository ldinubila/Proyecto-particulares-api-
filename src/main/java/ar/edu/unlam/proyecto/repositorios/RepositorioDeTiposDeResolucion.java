package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.TipoDeResolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeTiposDeResolucion extends JpaRepository<TipoDeResolucion, Long> {
}
