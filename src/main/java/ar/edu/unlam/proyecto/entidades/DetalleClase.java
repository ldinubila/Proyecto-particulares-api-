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
import javax.persistence.Table;

import ar.edu.unlam.proyecto.servicios.ServicioDeCompras;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "DetalleClase")
public class DetalleClase extends Producto {
	  public enum EstadoClase {
	        DISPONIBLE,
	        NODISPONIBLE,
	        INICIADA,
	        FINALIZADA,
	        AUSENTE,
	        CANCELADA
	    }

	private String url;
	private int cupoRestante;
	private Date fecha;
    @ManyToOne
    @JoinColumn(name = "id_clase")
    private Clase clase;
    
    @Enumerated(EnumType.STRING)
    private DetalleClase.EstadoClase estado;

	@Override
	public void finalizarCompra(ServicioDeCompras servicioDeCompras,BigDecimal monto,Long tipo,Long fecha) {
		
		servicioDeCompras.finalizarCompra(this,monto, tipo,fecha);
	
	}

	@Override
	public boolean publicadoPor(Long id) {
		return this.clase.getProfesor().getUsuario().getId().equals(id);
	}


}
