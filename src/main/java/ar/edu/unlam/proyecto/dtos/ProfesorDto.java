package ar.edu.unlam.proyecto.dtos;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ar.edu.unlam.proyecto.convertidores.ImagenBase64Deserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfesorDto {
	
		private Long id;
	    private String experiencia;
	    private String localidad;
	    private UsuarioDto usuario;
	    @JsonDeserialize(using = ImagenBase64Deserializer.class)
	    private byte[] video;
	    private Long idUsuario;
}
