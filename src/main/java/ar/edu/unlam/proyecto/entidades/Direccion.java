package ar.edu.unlam.proyecto.entidades;

import javax.persistence.*;

@Entity
@Table(name = "Direccion")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String Calle;

    private int Numero;

    @ManyToOne
    private Localidad Localidad;

}
