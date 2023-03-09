package ar.edu.unlam.proyecto.integracion;

import ar.edu.unlam.proyecto.clientes.mercadopago.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = {Adaptador.class, ClienteMercadoPago.class }
)
public class ClienteMercadoPagoIntegrationTest {

    @Autowired
    private ClienteMercadoPago cliente;

    @Test
    public void seGeneraElClienteDeMercadoPago() {
        assertThat(cliente).isNotNull();
    }

    @Test
    public void devuelveUnaPreferenciaNulaCuandoNoExiste() {
        PreferenciaMP preferencia = cliente.obtenerPreferenciaPorId("id-inexistente");
        assertThat(preferencia).isNull();
    }

    @Test
    public void crearUnaPreferenciaParaUnProducto() {

        ProductoMP claseDeIngles = ProductoMP.nuevo()
                .conNombre("Clase de ingles")
                .conCantidad(1)
                .conPrecioUnitario(500.0f);

        PreferenciaMP preferencia = cliente.guardarPreferencia(claseDeIngles);

        assertThat(preferencia).isNotNull();
        assertThat(preferencia.getId()).isNotBlank();
        assertThat(preferencia.getCheckoutUrl()).isNotBlank();
    }
}
