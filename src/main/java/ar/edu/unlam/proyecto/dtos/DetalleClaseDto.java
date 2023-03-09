package ar.edu.unlam.proyecto.dtos;

import java.util.List;
import ar.edu.unlam.proyecto.entidades.DetalleClase;
import ar.edu.unlam.proyecto.entidades.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class DetalleClaseDto {
	   private List<Usuario> alumnos;
}
