package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeProductos extends JpaRepository<Producto, Long> {
}
