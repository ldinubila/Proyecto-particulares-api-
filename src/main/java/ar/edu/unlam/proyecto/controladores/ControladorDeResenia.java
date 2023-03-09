package ar.edu.unlam.proyecto.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlam.proyecto.dtos.ReseniaDto;
import ar.edu.unlam.proyecto.entidades.Resenia;
import ar.edu.unlam.proyecto.servicios.ServicioDeResenia;
import excepciones.ExcepcionLaReseniaYaExiste;
import excepciones.FechaNoDisponibleExcepcion;

@RestController
@CrossOrigin
@RequestMapping("v1/resenia")
public class ControladorDeResenia {
	private ServicioDeResenia servicioDeResenia;

    @Autowired
    public ControladorDeResenia(ServicioDeResenia servicioDeResenia){
        this.servicioDeResenia = servicioDeResenia;
    }
    
    
    @PostMapping("guardar")
    public ResponseEntity<?> guardar(@RequestBody ReseniaDto resenia) {
    	try {
            return ResponseEntity.ok(  this.servicioDeResenia.guardar(resenia));
    	}catch (ExcepcionLaReseniaYaExiste e) {
			return ResponseEntity.badRequest().body("Ya existe una reseña");
    	}
    }
    @GetMapping("obtener/{id}")
    public ResponseEntity<?> obtener(@PathVariable("id") Long id) {
            return ResponseEntity.ok(  this.servicioDeResenia.obtener(id));
    
    }
    
    @GetMapping("obtenerIdUser/{id}")
    public ResponseEntity<?> obtenerIdUser(@PathVariable("id") Long id) {
            return ResponseEntity.ok(  this.servicioDeResenia.obtenerIdUser(id));
    
    }
    @GetMapping("obtenerPorParticular/{id}")
    public ResponseEntity<?> obtenerPorParticular(@PathVariable("id") Long id) {
            return ResponseEntity.ok(  this.servicioDeResenia.obtenerPorParticular(id));
    
    }

}
