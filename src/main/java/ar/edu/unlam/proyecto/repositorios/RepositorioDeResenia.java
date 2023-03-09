package ar.edu.unlam.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.proyecto.entidades.Resenia;

@Repository
public interface RepositorioDeResenia extends JpaRepository<Resenia, Long> {

	Resenia save(Resenia nueva);
	
	@Query("select o from Resenia o where o.producto.id = ?1 and o.usuario.id = ?2")
	Resenia existeResenia(Long id_producto, Long id_usuario);
	
	@Query("select o from Resenia o where o.producto.id = ?1 ")
	List<Resenia> obtenerPorProducto(Long id);
	
	@Query("select o from Resenia o where o.usuario.id = ?1 ")
	List<Resenia> obtenerPorUser(Long id);
	
	@Query(value = "select * from Resenia as r "
			+ "             INNER join DetalleClase as dc "
			+ "		     on r.id_producto=dc.id "
			+ "			  INNER join Clase as c "
			+ "		     on dc.id_clase=c.id "
			+ "		     where (c.id_profesor = ?1)"	
			+ "			 ORDER BY r.fecha Asc"
				, nativeQuery = true)
	List<Resenia> obtenerPorParticular(Long id);
}
