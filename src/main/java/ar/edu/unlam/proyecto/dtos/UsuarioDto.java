package ar.edu.unlam.proyecto.dtos;


import ar.edu.unlam.proyecto.entidades.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDto {

    @JsonProperty("idUsuario")
    private Long id;
    private String nombre;
    private String apellido;
    private Long documento;
    private String email;
    private String contrasenia;
    private Long telefono;
    private Date fechaNacimiento;
    private List<FotoPerfilDto> fotoPerfil;
    private Usuario.rolUsuario rol;

}
