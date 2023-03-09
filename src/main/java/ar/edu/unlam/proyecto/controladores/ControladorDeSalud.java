package ar.edu.unlam.proyecto.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salud")
public class ControladorDeSalud {

    @GetMapping
    public String obtenerEstado() {
        return "Ok";
    }
}
