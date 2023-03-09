package ar.edu.unlam.proyecto.repositorios;

import ar.edu.unlam.proyecto.entidades.UsuarioAlumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDeAlumno extends JpaRepository<UsuarioAlumno, Long> {


    @Query(value = "SELECT *"
            + "FROM alumno "
            + "WHERE (alumno.Usuario_id = ?1)"
            , nativeQuery = true)
    UsuarioAlumno buscarAlumnoPorIdUser(Long user);


}
