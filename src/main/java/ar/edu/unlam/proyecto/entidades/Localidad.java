package ar.edu.unlam.proyecto.entidades;

import javax.persistence.*;

@Entity
@Table(name = "Localidad")
public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String Nombre;

}
