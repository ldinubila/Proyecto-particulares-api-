package ar.edu.unlam.proyecto.controladores;

import ar.edu.unlam.proyecto.entidades.Materia;
import ar.edu.unlam.proyecto.entidades.Nivel;
import ar.edu.unlam.proyecto.servicios.ServicioDeNiveles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("v1/niveles")

public class ControladorDeNiveles {

    private ServicioDeNiveles servicioDeNiveles;

    @Autowired
    public ControladorDeNiveles(ServicioDeNiveles servicioDeNiveles){
        this.servicioDeNiveles = servicioDeNiveles;
    }

    @GetMapping
    public ResponseEntity<List<Nivel>> obtenerTodos(){
        List<Nivel> niveles = this.servicioDeNiveles.obtenerTodos();
        return ResponseEntity.ok(niveles);
    }
}
