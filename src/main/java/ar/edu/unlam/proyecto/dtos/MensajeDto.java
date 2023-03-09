package ar.edu.unlam.proyecto.dtos;

import java.util.Date;

import ar.edu.unlam.proyecto.entidades.Mensaje;
import ar.edu.unlam.proyecto.entidades.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class MensajeDto {
    private String contenido;
    private String asunto;
    private Long emisor;
    private Long receptor;
    private Long idMensaje;
}
