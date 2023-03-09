package ar.edu.unlam.proyecto.controladores;

import ar.edu.unlam.proyecto.entidades.TipoDeResolucion;
import ar.edu.unlam.proyecto.servicios.ServicioDeTipoDeResolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("v1/tiposDeResoluciones")
public class ControladorDeTiposDeResolucion {

    private ServicioDeTipoDeResolucion servicioDeTipoDeResolucion;

    @Autowired
    public ControladorDeTiposDeResolucion(ServicioDeTipoDeResolucion servicioDeTipoDeResolucion) {
        this.servicioDeTipoDeResolucion = servicioDeTipoDeResolucion;
    }

    @GetMapping
    public ResponseEntity<List<TipoDeResolucion>> obtenerTodos() {
        return ResponseEntity.ok(this.servicioDeTipoDeResolucion.obtenerTodos());
    }
}
