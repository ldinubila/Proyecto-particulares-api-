package ar.edu.unlam.proyecto.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class Usuario {

    public enum rolUsuario{
        alumno,
        particular,
        administrador
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private Long documento;

    @Column(unique=true)
    private String Email;

    private String contrasenia;
    private Long telefono;
    private Date fechaNacimiento;
    private byte[] fotoPerfil;
    private boolean bloqueado;
    
    @Enumerated(EnumType.STRING)
    private rolUsuario rol;

}
