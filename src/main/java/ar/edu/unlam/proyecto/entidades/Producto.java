package ar.edu.unlam.proyecto.entidades;

import ar.edu.unlam.proyecto.servicios.ServicioDeCompras;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombreProducto;
    @Column(name = "precio")
    private BigDecimal precioProducto;

    // Punto de entrada para el double dispatch
    public abstract void finalizarCompra(ServicioDeCompras servicioDeCompras,BigDecimal monto,Long tipo,Long fecha);
    public abstract boolean publicadoPor(Long id);
}
