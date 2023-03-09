package ar.edu.unlam.proyecto.dtos;


import ar.edu.unlam.proyecto.entidades.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

	private Long userId;
	private Usuario.rolUsuario rol;
	private String token;
}
