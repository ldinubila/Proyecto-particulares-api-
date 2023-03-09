package ar.edu.unlam.proyecto.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlumnoDto {

    private String materiasInteres;
    private Long nivelAcademico;
    private UsuarioDto usuario;
    private Long idUser;

}
