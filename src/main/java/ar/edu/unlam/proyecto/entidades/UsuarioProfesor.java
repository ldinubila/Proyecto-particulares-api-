package ar.edu.unlam.proyecto.entidades;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Profesor")
public class UsuarioProfesor {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

	    private String experiencia;
	    private byte[] video;
	    private String localidad;

	    @OneToOne
	    @JoinColumn(name = "Usuario_id")
	    private Usuario Usuario;
}
