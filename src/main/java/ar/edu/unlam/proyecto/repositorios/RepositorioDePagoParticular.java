package ar.edu.unlam.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.proyecto.entidades.PagoParticular;
import ar.edu.unlam.proyecto.entidades.UsuarioAlumno;

@Repository
public interface RepositorioDePagoParticular extends JpaRepository<PagoParticular, Long> {

}
