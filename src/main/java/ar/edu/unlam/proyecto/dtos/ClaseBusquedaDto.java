package ar.edu.unlam.proyecto.dtos;

import java.time.LocalDateTime;

import ar.edu.unlam.proyecto.entidades.Clase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClaseBusquedaDto {

    private Clase.MetodoClase metodo;
    private Long nivel;
    private LocalDateTime fecha;
    private Clase.ModoClase modo;
    private String busqueda;
}
