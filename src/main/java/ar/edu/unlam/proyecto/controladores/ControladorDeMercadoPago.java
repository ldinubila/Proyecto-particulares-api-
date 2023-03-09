package ar.edu.unlam.proyecto.controladores;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("v1/mercadoPago")
public class ControladorDeMercadoPago {
//Public Key
//TEST-3e6c22cc-18c4-4dec-96ce-c0d1cdf84457

//Access Token
//TEST-8386033511256910-110703-0b707e3a537c5ab4e47291f22c936e6f-166664907

    @GetMapping
    public Preference procesarPagoDeClase() throws MPException {
        MercadoPago.SDK.configure("TEST-8386033511256910-110703-0b707e3a537c5ab4e47291f22c936e6f-166664907");
        Preference preference = new Preference();

        Item item = new Item();
        item.setTitle("Mi producto")
                .setQuantity(1)
                .setUnitPrice((float) 75.56);
        preference.appendItem(item);
        var result = preference.save();

        System.out.println(result.getSandboxInitPoint());
        return result;

    }


}
