package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.Archivo;
import ar.edu.unlam.proyecto.entidades.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioDeArchivos extends JpaRepository<Archivo, Long> {

    List<Archivo> findAllByModelo(Modelo modelo);
   
}
