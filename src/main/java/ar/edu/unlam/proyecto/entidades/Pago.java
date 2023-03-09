package ar.edu.unlam.proyecto.entidades;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Pago {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double importe;
    private String estado;
    private LocalDateTime fecha;
    @OneToOne
    @JoinColumn(name = "id_detalleClase")
    private DetalleClase detalleClase;
    @OneToOne
    @JoinColumn(name = "id_usuarioAlumno")
    private Usuario alumno;
 

}
