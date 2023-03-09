package ar.edu.unlam.proyecto.clientes.mercadopago;

import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPApiResponse;
import com.mercadopago.core.MPBase;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ClienteMercadoPago {

    private final Adaptador adaptador;
    private final Logger logger = LoggerFactory.getLogger(ClienteMercadoPago.class.getSimpleName());

    @Autowired
    public ClienteMercadoPago(@Value("${mercado.pago.accessToken}") String accessToken,
                              Adaptador adaptador) {
        MercadoPago.SDK.configure(accessToken);
        this.adaptador = adaptador;
    }

    public PreferenciaMP obtenerPreferenciaPorId(String id) {
        return this.adaptador.adaptar(this.buscarPreferenciaEnMP(id));
    }

    public PreferenciaMP guardarPreferencia(ProductoMP producto) {
        return this.adaptador.adaptar(this.guardarPreferenciaEnMP(producto));
    }

    private Preference guardarPreferenciaEnMP(ProductoMP producto){
        Preference preferencia = new Preference();
        try {
            Item item = this.adaptador.adaptar(producto);
            preferencia.appendItem(item).save();
            logger.debug("Se creó una preferencia en Mercado Pago con id <{}>", preferencia.getId());
        } catch (MPException e) {
            logger.error("Ocurrió un error generando una preferencia. Mensaje: {}", e.getMessage());
            logger.debug("Ocurrió un error generando una preferencia. Mensaje: {}. Causa {}", e.getMessage(), e.getStackTrace());
        }
        return preferencia;
    }

    private Preference buscarPreferenciaEnMP(String id) {
        Preference preferenciaBuscada = null;
        try {
            preferenciaBuscada = Preference.findById(id);
            HttpStatus resultado = this.obtenerResultadoDeOperacion(preferenciaBuscada);
            if (resultado.equals(HttpStatus.OK)) {
                logger.debug("Se encontró la preferencia con id <{}>", id);
            } else if (resultado.equals(HttpStatus.NOT_FOUND)) {
                logger.info("No se encontró la preferencia con id <{}>", id);
            }
        } catch (MPException e) {
            logger.error("Ocurrió un error buscando la preferencia con id {}. Mensaje: {}", id, e.getMessage());
            logger.debug("Ocurrió un error buscando la preferencia con id {}. Mensaje: {}. Causa {}", id, e.getMessage(), e.getStackTrace());
        }
        return preferenciaBuscada;
    }

    private HttpStatus obtenerResultadoDeOperacion(MPBase objetoBase) {
        MPApiResponse response = objetoBase.getLastApiResponse();
        return HttpStatus.valueOf(response.getStatusCode());
    }
}
