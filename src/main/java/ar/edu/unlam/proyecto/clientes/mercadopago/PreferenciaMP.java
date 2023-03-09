package ar.edu.unlam.proyecto.clientes.mercadopago;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PreferenciaMP {

    private String id;
    private String checkoutUrl;

    public static PreferenciaMP nueva() {
        return new PreferenciaMP();
    }

    public static PreferenciaMP vacia() {
        return null;
    }

    public PreferenciaMP conId(String id) {
        this.id = id;
        return this;
    }

    public PreferenciaMP conCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
        return this;
    }
}
