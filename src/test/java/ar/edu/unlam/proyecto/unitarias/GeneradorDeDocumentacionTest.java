package ar.edu.unlam.proyecto.unitarias;

import ar.edu.unlam.proyecto.controladores.ControladorDeSalud;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = {ControladorDeSalud.class })
@AutoConfigureRestDocs
public class GeneradorDeDocumentacionTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void obtenerEstadoAplicacion() throws Exception {
        this.mvc.perform(get("/salud").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Ok"))
                .andExpect(status().isOk())
                .andDo(document("{method-name}"));
    }
}
