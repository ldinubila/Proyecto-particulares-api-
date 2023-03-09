package ar.edu.unlam.proyecto.dtos;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ar.edu.unlam.proyecto.convertidores.ImagenBase64Deserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DatosAcademicosDto {

	private Long id;
    @JsonDeserialize(using = ImagenBase64Deserializer.class)
    private byte[] documento;
    private Long idProfesor;
    private String titulo;
    private Date fechaInicio;
    private Date fechaFin;
    private List<ArchivoDto> archivos;

}
