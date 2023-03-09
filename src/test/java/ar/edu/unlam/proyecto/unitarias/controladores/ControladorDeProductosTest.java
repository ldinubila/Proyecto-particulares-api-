package ar.edu.unlam.proyecto.unitarias.controladores;

import ar.edu.unlam.proyecto.controladores.ControladorDeProductos;
import ar.edu.unlam.proyecto.dtos.ProcesoDeCompraDto;
import ar.edu.unlam.proyecto.entidades.Producto;
import ar.edu.unlam.proyecto.servicios.PasarelaDePagos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = { ControladorDeProductos.class })
public class ControladorDeProductosTest {

    private static final String saltoDeLinea = System.getProperty("line.separator");

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private PasarelaDePagos pasarelaDePagos;

    @Test
    public void unAlumnoDeberiaPoderComprarUnaClase() throws Exception {

        final Long idComprador = 50L;
        final Long idProductoComprado = 1L;
        List<Long> ids= new ArrayList();
        ids.add(idProductoComprado);
        String pedido = generarPedidoDeCompraPara(idComprador);

        ProcesoDeCompraDto procesoDeCompraIniciado = crearProcesoDeCompra();
      //  when(pasarelaDePagos.iniciarCompra(anyLong(), anyLong())).thenReturn(procesoDeCompraIniciado);

        mockMvc.perform(post("/v1/productos/" + idProductoComprado + "/compras")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(pedido))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idExterno", equalTo(procesoDeCompraIniciado.getIdExterno())))
                .andExpect(jsonPath("$.urlExterna", equalTo(procesoDeCompraIniciado.getUrlExterna())))
                .andExpect(jsonPath("$.proveedor", equalTo(procesoDeCompraIniciado.getProveedor())));

        verify(pasarelaDePagos).iniciarCompra(eq(ids), eq(idComprador),(long) 1,(long) 1);
    }

    private String generarPedidoDeCompraPara(Long idComprador) {
        /**
         * Como aun no esta implementado lo de JWT el id de usuario viene en el body
         * pero habría que obtenerlo de los claims del token
         */
        String pedido = String.join(saltoDeLinea,
                "{",
                    "\"idUsuario\":" + idComprador,
                "}");
        return pedido;
    }

    private ProcesoDeCompraDto crearProcesoDeCompra() {
        return new ProcesoDeCompraDto(1L, "idExterno", "urlExterna", "unProveedor");
    }
}
