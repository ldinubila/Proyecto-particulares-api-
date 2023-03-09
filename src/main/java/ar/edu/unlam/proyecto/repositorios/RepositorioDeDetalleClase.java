package ar.edu.unlam.proyecto.repositorios;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.unlam.proyecto.entidades.Clase;
import ar.edu.unlam.proyecto.entidades.DetalleClase;
import ar.edu.unlam.proyecto.entidades.Modelo;

public interface RepositorioDeDetalleClase  extends JpaRepository<DetalleClase, Long>{

	Optional<List<DetalleClase>> findByClase(Clase clase);

	Optional<DetalleClase> findByClaseAndFecha(Optional<Clase> clase, java.util.Date fecha);

	Optional<DetalleClase> findByClaseAndFecha(Optional<Clase> clase, LocalDateTime fecha);
	
	@Query(value="select * from DetalleClase as d "
			+ "			inner join Clase as c "
			+ "			on c.id = d.id_clase "
			+ "			inner join Modelo as m "
			+ "			on c.id_modelo=m.id "
			+ "			and (m.id = ?1)  "
			+ "			ORDER BY m.fecha Asc",
			 nativeQuery = true)
	List<DetalleClase> findByModeloId(Long id);



}
