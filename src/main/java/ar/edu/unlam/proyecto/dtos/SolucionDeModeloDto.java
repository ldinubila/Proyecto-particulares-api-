package ar.edu.unlam.proyecto.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SolucionDeModeloDto {
    private Long idModelo;
    private String comentarioAdicional;
    private List<ArchivoDto> archivos;
    private Long idUsuario;
}
