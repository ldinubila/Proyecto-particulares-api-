package ar.edu.unlam.proyecto.dtos;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import ar.edu.unlam.proyecto.entidades.Clase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClaseDto {

    private String nombre;
    private Clase.MetodoClase metodo;
    private Clase.ModoClase modo;
    private Long precio;
    private int cupo;
    private String descripcion;
    private Long materia;
    private Long nivel;
    private LocalDateTime fecha;
    private Long id_profesor;
    private String ubicacion;
    private int puntuacion;
    private List<Date> disponibilidad;

}
