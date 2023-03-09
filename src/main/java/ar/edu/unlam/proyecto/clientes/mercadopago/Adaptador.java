package ar.edu.unlam.proyecto.clientes.mercadopago;

import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Adaptador {

    public PreferenciaMP adaptar(Preference preferencia) {
        return preferencia == null || !StringUtils.hasText(preferencia.getId()) ?
            PreferenciaMP.vacia() :
            PreferenciaMP.nueva()
                .conId(preferencia.getId())
                //Esto está bien para un ambiente de prueba pero
                //en un ambiente productivo hay que usar preferencia.getInitPoint()
                .conCheckoutUrl(preferencia.getSandboxInitPoint());
    }

    public Item adaptar(ProductoMP producto) {
        return new Item()
                .setTitle(producto.getNombre())
                .setQuantity(producto.getCantidad())
                .setUnitPrice(producto.getPrecioUnitario())
                .setCurrencyId(Payment.CurrencyId.ARS.name());
    }
}
