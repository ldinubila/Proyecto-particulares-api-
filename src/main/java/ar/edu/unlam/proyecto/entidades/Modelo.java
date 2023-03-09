package ar.edu.unlam.proyecto.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Modelo {

    public enum EstadoModelo {
        ACTIVO,
        INACTIVO,
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "id_institucion")
    private Institucion institucion;
    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;
    @ManyToOne
    @JoinColumn(name = "id_nivel")
    private Nivel nivel;
    @Enumerated(EnumType.STRING)
    private EstadoModelo estado;
    private LocalDateTime fecha;
    private boolean publico;
}
