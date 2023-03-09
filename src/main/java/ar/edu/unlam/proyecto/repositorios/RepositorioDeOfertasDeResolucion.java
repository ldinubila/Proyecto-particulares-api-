package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.OfertaDeResolucion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeOfertasDeResolucion extends JpaRepository<OfertaDeResolucion, Long> {
	
	@Query("select o from OfertaDeResolucion o where o.modelo.id = ?1")
    List<OfertaDeResolucion> obtenerPostulacionesPorModelo(Long id);
}
