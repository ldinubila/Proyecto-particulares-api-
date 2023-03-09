package ar.edu.unlam.proyecto.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Clase {

    public enum MetodoClase {
        ONLINE,
        PRESENCIAL,
        ERROR
    }

    public enum ModoClase{
        INDIVIDUAL,
        GRUPAL,
        ERROR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private int cupo;
    private String descripcion;
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private MetodoClase metodo;

    @Enumerated(EnumType.STRING)
    private ModoClase modo;

    private int puntuacion;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia Materia;

    @ManyToOne
    @JoinColumn(name = "id_nivel")
    private Nivel Nivel;

    @OneToOne
    @JoinColumn(name = "id_modelo")
    private Modelo modelo;
    
    @ManyToOne
    @JoinColumn(name = "id_profesor")
    private UsuarioProfesor Profesor;


}
