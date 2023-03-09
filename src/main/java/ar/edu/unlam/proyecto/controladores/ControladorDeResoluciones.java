package ar.edu.unlam.proyecto.controladores;

import ar.edu.unlam.proyecto.entidades.ArchivoDeResolucion;
import ar.edu.unlam.proyecto.entidades.Resolucion;
import ar.edu.unlam.proyecto.servicios.ServicioDeResolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1/resoluciones")
public class ControladorDeResoluciones {

    private final ServicioDeResolucion servicioDeResolucion;

    @Autowired
    public ControladorDeResoluciones(ServicioDeResolucion servicioDeResolucion) {
        this.servicioDeResolucion = servicioDeResolucion;
    }

    @GetMapping("{id}/archivos")
    public ResponseEntity<List<ArchivoDeResolucion>> obtenerArchivosPorResolucion(@PathVariable("id") Long idResolucion) {
        Resolucion resolucion = new Resolucion();
        resolucion.setId(idResolucion);
        return ResponseEntity.ok(this.servicioDeResolucion.obtenerArchivos(resolucion));
    }
}
