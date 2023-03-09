package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.Compra;
import ar.edu.unlam.proyecto.entidades.Usuario;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeCompras extends JpaRepository<Compra, Long> {


	List<Compra> findAllByUsuario(Usuario usuario);

	@Query("select c from Compra c where (c.usuario.id = :id)")
	List<Compra> filtrarPorUsuario(@Param("id")Long id);
	
	@Query("select c from Compra c where (c.producto.id = :id)")
	List<Compra> filtrarPorDetalleClase(@Param("id")Long id);

	@Query("select c from Compra c where (c.producto.id = :id)")
	Compra obtenerPorDetalleClase(@Param("id")Long id);

	@Query("select c from Compra c ORDER BY c.fecha Asc")
	List<Compra> findAllRecientes();

	@Query("select c from Compra c where c.usuario.id = :idUser and c.producto.id = :idProducto")
	List<Compra> existeCompraUserProducto(@Param("idUser") Long idUser, @Param("idProducto") Long idProducto);
	
	

	@Query("SELECT c FROM Compra c WHERE c.fecha >= :fechaInicio AND c.fecha <= :fechaFin")
    List<Compra> obtenerComprasEntre(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}
