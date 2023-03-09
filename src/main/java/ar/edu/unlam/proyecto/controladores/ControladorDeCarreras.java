package ar.edu.unlam.proyecto.controladores;


import ar.edu.unlam.proyecto.entidades.Carrera;
import ar.edu.unlam.proyecto.servicios.ServicioDeCarreras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1/carreras")
public class ControladorDeCarreras {

    private ServicioDeCarreras servicioDeCarreras;

    @Autowired
    public ControladorDeCarreras(ServicioDeCarreras servicioDeCarreras) {
        this.servicioDeCarreras = servicioDeCarreras;
    }

    @GetMapping
    public ResponseEntity<List<Carrera>> obtenerTodas(){
        List<Carrera> carrera = this.servicioDeCarreras.obtenerTodas();
        return ResponseEntity.ok(carrera);
    }

}
