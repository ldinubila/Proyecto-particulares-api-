package ar.edu.unlam.proyecto.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlam.proyecto.dtos.EstadisticasDto;
import ar.edu.unlam.proyecto.dtos.EstadisticasModeloClaseDto;
import ar.edu.unlam.proyecto.entidades.Compra;
import ar.edu.unlam.proyecto.servicios.ServicioDeEstadisticas;

@RestController
@CrossOrigin
@RequestMapping("v1/estadisticas")
public class ControladorDeEstadisticas {
	private ServicioDeEstadisticas servicioEstadisticas;
	
    @Autowired
    public ControladorDeEstadisticas(ServicioDeEstadisticas servicioEstadisticas){
        this.servicioEstadisticas = servicioEstadisticas;
    }
    
   
    
    
    @PostMapping("porCompras")
    public ResponseEntity<?> porCompras(@RequestBody  EstadisticasDto body) {
        List<Compra> compras = this.servicioEstadisticas.porCompras(body);
        return ResponseEntity.ok(compras);
    } 
    

    @PostMapping("porParticular/{id}")
    public ResponseEntity<?> porParticular(@PathVariable("id") Long id,@RequestBody  EstadisticasDto body) {
        List<Compra> compras = this.servicioEstadisticas.porMateriasPorParticular(id,body);
        return ResponseEntity.ok(compras);
    }  
  
    @PostMapping("modelosClases")
    public ResponseEntity<?> modelosClases(@RequestBody  EstadisticasDto body) {
    	EstadisticasModeloClaseDto dto = this.servicioEstadisticas.porClasesModelos(body);
        return ResponseEntity.ok(dto);
    } 
    @PostMapping("porClases")
    public ResponseEntity<?> porClases(@RequestBody  EstadisticasDto body) {
        List<Compra> compras = this.servicioEstadisticas.porClases(body);
        return ResponseEntity.ok(compras);
    } 
    
    @PostMapping("porModelo")
    public ResponseEntity<?> porModelos(@RequestBody  EstadisticasDto body) {
        List<Compra> compras = this.servicioEstadisticas.porModelo(body);
        return ResponseEntity.ok(compras);
    } 
    
    
    @GetMapping("clasesMasPupularesDelMes")
    public ResponseEntity<?> clasesMasPupularesDelMes() {
            return ResponseEntity.ok(  this.servicioEstadisticas.clasesMasPupularesDelMes());
    
    }
    
    @GetMapping("modelosMasPupularesDelMes")
    public ResponseEntity<?> modelosMasPupularesDelMes() {
            return ResponseEntity.ok(  this.servicioEstadisticas.modelosMasPupularesDelMes());
    }
    
    
    @GetMapping("agregadosRecientemente")
    public ResponseEntity<?> agregadosRecientemente() {
            return ResponseEntity.ok(  this.servicioEstadisticas.agregadosRecientemente());
    }
    
    
}
