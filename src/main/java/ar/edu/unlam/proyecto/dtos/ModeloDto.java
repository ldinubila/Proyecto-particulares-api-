package ar.edu.unlam.proyecto.dtos;

import ar.edu.unlam.proyecto.entidades.Modelo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ModeloDto {

    @JsonProperty("idModelo")
    private Long id;
    private String nombre;
    private Modelo.EstadoModelo estado;
    private Long institucion;
    private Long carrera;
    private Long materia;
    private Long nivel;
    private Long usuario;
    private boolean publico;
    private List<ArchivoDto> archivos;
}