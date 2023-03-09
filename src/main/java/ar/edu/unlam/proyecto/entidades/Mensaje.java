package ar.edu.unlam.proyecto.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import ar.edu.unlam.proyecto.entidades.Modelo.EstadoModelo;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Mensaje {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenido;
    private String asunto;
    private Boolean eliminadoReceptor;
    private Boolean leidoReceptor;
    private Boolean eliminadoEmisor;
    private Boolean leidoEmisor;
    private LocalDateTime fecha;
    @OneToOne
    @JoinColumn(name = "id_emisor")
    private Usuario emisor;
    @OneToOne
    @JoinColumn(name = "id_receptor")
    private Usuario receptor;
    @OneToOne
    @JoinColumn(name = "id_respuesta")
    private Mensaje respuesta;

}
