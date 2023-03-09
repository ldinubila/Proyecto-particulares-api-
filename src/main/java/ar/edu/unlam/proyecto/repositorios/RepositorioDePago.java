package ar.edu.unlam.proyecto.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.proyecto.entidades.DetalleClase;
import ar.edu.unlam.proyecto.entidades.Pago;
@Repository
public interface RepositorioDePago extends JpaRepository<Pago, Long>{

	Optional<List<Pago>> findByDetalleClase(DetalleClase detalleClase);


}
