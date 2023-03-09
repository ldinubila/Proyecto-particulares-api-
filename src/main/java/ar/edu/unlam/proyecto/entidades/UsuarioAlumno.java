package ar.edu.unlam.proyecto.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Alumno")
public class UsuarioAlumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String materiasInteres;

    @OneToOne
    @JoinColumn(name = "id_nivel")
    private Nivel Nivel;

    @OneToOne
    @JoinColumn(name = "Usuario_id")
    private Usuario Usuario;

}
