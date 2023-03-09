package ar.edu.unlam.proyecto.controladores;

import java.util.List;

import ar.edu.unlam.proyecto.dtos.ClaseBusquedaDto;
import ar.edu.unlam.proyecto.dtos.ClaseDto;
import ar.edu.unlam.proyecto.dtos.LinkClaseDto;
import ar.edu.unlam.proyecto.servicios.ServicioDeClases;
import ar.edu.unlam.proyecto.servicios.ServicioDeMail;
import excepciones.ClaseNoExisteExcepcion;
import excepciones.ExcepcionMailRepetido;
import excepciones.ExcepcionNoEsAlumno;
import excepciones.ExceptionEliminarClase;
import excepciones.FechaNoDisponibleExcepcion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ar.edu.unlam.proyecto.entidades.Clase;
import ar.edu.unlam.proyecto.entidades.Compra;
import ar.edu.unlam.proyecto.entidades.DetalleClase;
import ar.edu.unlam.proyecto.entidades.DetalleClase.EstadoClase;
import ar.edu.unlam.proyecto.entidades.Producto;
import ar.edu.unlam.proyecto.entidades.Usuario;

@RestController
@CrossOrigin
@RequestMapping("v1/clases")
public class ControladorDeClases {

	private ServicioDeClases servicioDeClases;
    private ServicioDeMail servicioDeMail;

    @Autowired
    public ControladorDeClases(ServicioDeClases servicioDeClases,ServicioDeMail servicioDeMail){
        this.servicioDeClases = servicioDeClases;
        this.servicioDeMail = servicioDeMail;
    }
    
    @GetMapping
    public ResponseEntity<List<Clase>> obtenerTodas() {
        List<Clase> clases = this.servicioDeClases.obtenerTodas();
        return ResponseEntity.ok(clases);
    }
   
    @GetMapping("buscador/{nombre}")
    public ResponseEntity<List<Clase>> buscador(@PathVariable("nombre") String busqueda) {
        List<Clase> clases = this.servicioDeClases.buscador(busqueda);
        return ResponseEntity.ok(clases);
    }    
    
    @PostMapping("editar/{id}")
    public ResponseEntity <?> editar(@PathVariable("id") Long id,@RequestBody ClaseDto clase) {
    	try {
    		return ResponseEntity.ok(this.servicioDeClases.modificarClase(id,clase));
    	}catch (FechaNoDisponibleExcepcion e) {
			return ResponseEntity.badRequest().body("No puede agregar o eliminar una fecha que ya esta contratada");
    	}
    }
    

    
    @GetMapping("verDetalle/{id}")
    public ResponseEntity <Clase> verDetalle(@PathVariable("id") Long id) {
        Clase clase = this.servicioDeClases.buscadorPorId(id);
        return ResponseEntity.ok(clase);
    }   

    @GetMapping("obtenerDisponibilidad/{id}")
    public ResponseEntity <List<DetalleClase>> obtenerDisponibilidad(@PathVariable("id") Long id) {
    	List<DetalleClase> clases = this.servicioDeClases.obtenerDisponibilidad(id);
        return ResponseEntity.ok(clases);
    }
    @GetMapping("obtenerDisponibilidadModelos/{id}")
    public ResponseEntity <List<DetalleClase>> obtenerDisponibilidadModelos(@PathVariable("id") Long id) {
    	List<DetalleClase> clases = this.servicioDeClases.obtenerDisponibilidadModelos(id);
        return ResponseEntity.ok(clases);
    }
    @PostMapping
    public ResponseEntity <Clase> guardar(@RequestBody ClaseDto clase) {

        this.servicioDeClases.guardar(clase);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("filtrar")
    public ResponseEntity<List<Clase>> filtro (@RequestBody ClaseBusquedaDto clase) {
    	List<Clase> clases =  this.servicioDeClases.filtrar(clase.getMetodo(),clase.getNivel(),clase.getModo(),clase.getBusqueda());
        return ResponseEntity.ok(clases);
    }

    @GetMapping("misClases/{id}")
    public ResponseEntity<List<Clase>> obtenerClasesPorParticular(@PathVariable("id") Long particularId){
        List<Clase> clase = this.servicioDeClases.obtenerClasesPorParticular(particularId);
        return ResponseEntity.ok(clase);
    }
    
    @GetMapping("participantes/{id}")
    public ResponseEntity<List<Usuario>> participantes(@PathVariable("id") Long particularId){
        List<Usuario> clase = this.servicioDeClases.participantes(particularId);
        return ResponseEntity.ok(clase);
    }
    
    @GetMapping("compras/{id}")
    public ResponseEntity<?> compras(@PathVariable("id") Long particularId){
    	try {
            List<Compra> clase = this.servicioDeClases.compras(particularId);
        	servicioDeMail.enviarMail(particularId);
            return ResponseEntity.ok(clase);

    	}catch (ExcepcionNoEsAlumno e) {
			return ResponseEntity.badRequest().body("Debe ser un usuario alumno para realizar esta operación");
    	}
    }
    @GetMapping("misClasesAlumno/{id}")
    public ResponseEntity<List<Producto>> obtenerClasesPorAlumno(@PathVariable("id") Long id){
        List<Producto> clase = this.servicioDeClases.obtenerClasesPorAlumno(id);
        return ResponseEntity.ok(clase);
    }
    @GetMapping("estado/{id}")
    public ResponseEntity<EstadoClase> estado(@PathVariable("id") Long id){
    	EstadoClase estado = this.servicioDeClases.obtenerEstado(id);
        return ResponseEntity.ok(estado);
    }
    @GetMapping("eliminarClase/{id}")
    public ResponseEntity <?> eliminarClase(@PathVariable("id") Long id) {
        try {
        	 servicioDeClases.eliminarClase(id);
             return ResponseEntity.noContent().build();
    	}catch (ExceptionEliminarClase e) {
			return ResponseEntity.badRequest().body("No se puede eliminar una clase que tenga detalle clases en uso.");
    	}
    }
    
    @PostMapping("claseIniciada/{id}")
    public ResponseEntity<?>  claseIniciada(@PathVariable("id") Long id,@RequestBody LinkClaseDto link) {
    	try {
    		return ResponseEntity.ok(servicioDeClases.claseIniciada(id,link.getLink()));
    	}catch (ClaseNoExisteExcepcion e) {
			return ResponseEntity.badRequest().body("La clase indicada no existe");	
    }
    }

    @GetMapping("claseFinalizada/{id}")
    public ResponseEntity <?>  claseFinalizada(@PathVariable("id") Long id) {
    	try {
    		return ResponseEntity.ok(servicioDeClases.claseFinalizada(id));
    	}catch (ClaseNoExisteExcepcion e) {
			return ResponseEntity.badRequest().body("La clase indicada no existe");	
    }
    }
    @GetMapping("detalleClase/{id}")
    public ResponseEntity<DetalleClase> detalleClase(@PathVariable("id") Long id){
    	DetalleClase detalleClase = this.servicioDeClases.detalleClasePorID(id);
        return ResponseEntity.ok(detalleClase);
    }
}
