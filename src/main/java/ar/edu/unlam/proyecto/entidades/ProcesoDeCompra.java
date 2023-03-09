package ar.edu.unlam.proyecto.entidades;

import lombok.Getter;

import java.math.BigDecimal;

//No es una entidad, es un objeto inmutable
@Getter
public class ProcesoDeCompra {

    private Producto producto;
    private Long idUsurio;
    private String identificadorExterno;

    private ProcesoDeCompra() {}

    public static ProcesoDeCompra nuevo() {
        return new ProcesoDeCompra();
    }

    public ProcesoDeCompra paraProducto(Producto producto) {
        this.producto = producto;
        return this;
    }
    
    public ProcesoDeCompra conIdUsuario(Long idUsuario) {
        this.idUsurio = idUsuario;
        return this;
    }

    public ProcesoDeCompra conIdentificadorExterno(String identificadorExterno) {
        this.identificadorExterno = identificadorExterno;
        return this;
    }

    public BigDecimal getMonto() {
        return this.producto.getPrecioProducto();
    }

    public String getNombre() {
        return this.producto.getNombreProducto();
    }
}
