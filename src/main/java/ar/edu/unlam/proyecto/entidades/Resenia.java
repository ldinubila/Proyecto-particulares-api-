package ar.edu.unlam.proyecto.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ar.edu.unlam.proyecto.servicios.ServicioDeCompras;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Resenia")
public class Resenia {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String comentario;
	private int puntaje;
	private LocalDateTime fecha;
	
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;
	
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
	



	

}
