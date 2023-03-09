package ar.edu.unlam.proyecto.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Compra {

    public enum EstadoCompra {
        EN_PROCESO,
        ACEPTADA,
        RECHAZADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    private BigDecimal monto;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private LocalDateTime fecha;

    private String identificadorExterno;

    @Enumerated(EnumType.STRING)
    private EstadoCompra estado;

    public boolean fueRecibidaPor(Long idVendedor) {
        return this.producto.publicadoPor(idVendedor);
    }
}
