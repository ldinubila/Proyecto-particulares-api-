package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.Modelo;
import ar.edu.unlam.proyecto.entidades.Resolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioDeResoluciones extends JpaRepository<Resolucion, Long> {
    List<Resolucion> findAllByModelo(Modelo modelo);
}
