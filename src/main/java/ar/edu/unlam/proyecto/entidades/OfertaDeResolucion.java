package ar.edu.unlam.proyecto.entidades;

import ar.edu.unlam.proyecto.servicios.ServicioDeCompras;
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
public class OfertaDeResolucion extends Producto {

    public enum EstadoOferta {
        PENDIENTE_DE_RESPUESTA,
        ACEPTADA,
        RECHAZADA,
        BAJA
    }

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_modelo")
    private Modelo modelo;
    private BigDecimal costo;
    @ManyToOne
    @JoinColumn(name ="id_tipoDeDemora")
    private TipoDeDemora demora;
    @ManyToOne
    @JoinColumn(name ="id_tipoDeResolucion")
    private TipoDeResolucion tipo;
    @Enumerated(EnumType.STRING)
    private EstadoOferta estado;
    private LocalDateTime fechaDeCreacion;

    public void setCosto(BigDecimal costo) {
        super.setPrecioProducto(costo);
        this.costo = costo;
    }

    @Override
    public void finalizarCompra(ServicioDeCompras servicioDeCompras,BigDecimal monto,Long tipo,Long fecha) {
        servicioDeCompras.finalizarCompra(this,monto,tipo,fecha);
    }

    @Override
    public boolean publicadoPor(Long id) {
        return this.usuario.getId().equals(id);
    }
}
