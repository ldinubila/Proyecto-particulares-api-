package ar.edu.unlam.proyecto.dtos;

import ar.edu.unlam.proyecto.convertidores.ImagenBase64Deserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArchivoDto {

    private String nombre;
    @JsonDeserialize(using = ImagenBase64Deserializer.class)
    private byte[] datos;
    private String extension;
    private double tamanio;
}
