package ar.edu.unlam.proyecto.clientes.mercadopago;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductoMP {

    private String nombre;
    private int cantidad;
    private Float precioUnitario;

    public static ProductoMP nuevo() {
        return new ProductoMP();
    }

    public ProductoMP conNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ProductoMP conCantidad(int cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public ProductoMP conPrecioUnitario(float precioUnitario) {
        this.precioUnitario = Float.valueOf(precioUnitario);
        return this;
    }
}
