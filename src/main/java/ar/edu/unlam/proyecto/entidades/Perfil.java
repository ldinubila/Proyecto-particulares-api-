package ar.edu.unlam.proyecto.entidades;

import javax.persistence.*;

@Entity
@Table(name = "Perfil")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String Apellido;

    private String Nombre;

    private Long Documento;

    @Column(unique=true)
    private String Email;

    private Long Telefono;

    @OneToOne
    private Usuario Usuario;






}
