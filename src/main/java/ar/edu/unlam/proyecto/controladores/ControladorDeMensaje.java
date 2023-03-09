package ar.edu.unlam.proyecto.controladores;

import ar.edu.unlam.proyecto.dtos.ClaseDto;
import ar.edu.unlam.proyecto.dtos.MensajeDto;
import ar.edu.unlam.proyecto.entidades.Clase;
import ar.edu.unlam.proyecto.entidades.Mensaje;
import ar.edu.unlam.proyecto.servicios.ServicioDeMensaje;
import excepciones.FechaNoDisponibleExcepcion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("v1/mensajes")

public class ControladorDeMensaje {
	 private ServicioDeMensaje servicioDeMensaje;
	    @Autowired
	    public ControladorDeMensaje(ServicioDeMensaje servicioDeMensaje){
	        this.servicioDeMensaje = servicioDeMensaje;
	    }
	    

	    @PostMapping("nuevo")
	    public ResponseEntity <Mensaje> guardar(@RequestBody MensajeDto mensaje) {

	        this.servicioDeMensaje.guardar(mensaje);
	        return ResponseEntity.noContent().build();
	    }
	    
	    @GetMapping("detalle/{id}")
	    public ResponseEntity <?> detalle(@PathVariable("id") Long id) {
	    		return ResponseEntity.ok(this.servicioDeMensaje.detalle(id));
	    	
	    }
	    @GetMapping("eliminar/{id}/{idUser}")
	    public ResponseEntity <?> eliminar(@PathVariable("id") Long id,@PathVariable("idUser") Long idUser) {
	    		return ResponseEntity.ok(this.servicioDeMensaje.eliminar(id,idUser));
	    	
	    }
	    
	    @GetMapping("restaurar/{id}/{idUser}")
	    public ResponseEntity <?> restaurar(@PathVariable("id") Long id,@PathVariable("idUser") Long idUser) {
	    		return ResponseEntity.ok(this.servicioDeMensaje.restaurar(id,idUser));
	    	
	    }
	    @GetMapping("recibidos/{id}")
	    public ResponseEntity <?> editar(@PathVariable("id") Long id) {
	    		return ResponseEntity.ok(this.servicioDeMensaje.recibidos(id));
	    	
	    }
	    @GetMapping("obtenerPorId/{id}")
	    public ResponseEntity <?> obtenerPorId(@PathVariable("id") Long id) {
	    		return ResponseEntity.ok(this.servicioDeMensaje.obtenerPorId(id));
	    	
	    }
	    
	    @GetMapping("enviados/{id}")
	    public ResponseEntity <?> enviados(@PathVariable("id") Long id) {
	    		return ResponseEntity.ok(this.servicioDeMensaje.enviados(id));
	    	
	    }
	    
	    @GetMapping("eliminados/{id}")
	    public ResponseEntity <?> eliminados(@PathVariable("id") Long id) {
	    		return ResponseEntity.ok(this.servicioDeMensaje.eliminados(id));
	    	
	    }
	    

	    @GetMapping("marcarMensajeComoLeido/{id}/{idUser}")
	    public ResponseEntity <?> marcarMensajeComoLeido(@PathVariable("id") Long id,@PathVariable("idUser") Long idUser) {
	    		return ResponseEntity.ok(this.servicioDeMensaje.mensajeLeido(id,idUser));
	    }
	    
	    @GetMapping("checkearMensajesNoLeidos/{idUser}")
	    public ResponseEntity <?> checkearMensajesNoLeidos(@PathVariable("idUser") Long idUser) {
	    		return ResponseEntity.ok(this.servicioDeMensaje.checkearMensajesNoLeidos(idUser));
	    }
	    
}
