package ar.edu.unlam.proyecto.controladores;

import ar.edu.unlam.proyecto.dtos.FiltroModeloDto;
import ar.edu.unlam.proyecto.dtos.ModeloDto;
import ar.edu.unlam.proyecto.dtos.OfertaDeResolucionDto;
import ar.edu.unlam.proyecto.dtos.SolucionDeModeloDto;
import ar.edu.unlam.proyecto.entidades.Archivo;
import ar.edu.unlam.proyecto.entidades.Modelo;
import ar.edu.unlam.proyecto.entidades.OfertaDeResolucion;
import ar.edu.unlam.proyecto.entidades.Resolucion;
import ar.edu.unlam.proyecto.servicios.ServicioDeMail;
import ar.edu.unlam.proyecto.servicios.ServicioDeModelos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1/modelos")
public class ControladorDeModelos {

    private ServicioDeModelos servicioDeModelos;
    private ServicioDeMail servicioDeMail;


    @Autowired
    public ControladorDeModelos (ServicioDeModelos servicioDeModelos,ServicioDeMail servicioDeMail){
        this.servicioDeModelos = servicioDeModelos;
        this.servicioDeMail = servicioDeMail;
    }

    @PostMapping
    public ResponseEntity guardar(@RequestBody ModeloDto modelo) {

        this.servicioDeModelos.guardar(modelo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Modelo>> obtenerTodos() {
        List<Modelo> modelos = this.servicioDeModelos.obtenerTodos();
        return ResponseEntity.ok(modelos);
    }

    @GetMapping("{id}/archivos")
    public ResponseEntity<List<Archivo>> obtenerArchivosPorModelo(@PathVariable("id") Long modeloId) {

        List<Archivo> archivos = this.servicioDeModelos.obtenerArchivosPorIdDeModelo(modeloId);
        return ResponseEntity.ok(archivos);
    }
    //buscar modelos alumno
    @PostMapping("BuscarModelosAlumno")
    public ResponseEntity<List<Modelo>> filtrarModelosAlumno(@RequestBody FiltroModeloDto filtros) {
        List<Modelo> modelos;
        if (!filtros.getText().isEmpty() ) {
            modelos = this.servicioDeModelos.filtrarBuscarModelosAlumno(filtros);
        } else {
            modelos = this.servicioDeModelos.TodosModelosAlumno();
        }
        return ResponseEntity.ok(modelos);
    }
    
    //mis modelos alumno
    @PostMapping("BuscarMisModelosAlumno")
    public  ResponseEntity<List<Modelo>> filtrarMisModelosAlumno(@RequestBody FiltroModeloDto filtros) {
    	 List<Modelo> modelos;
         if(!filtros.getText().isEmpty() ) {
            modelos =this.servicioDeModelos.filtrarMisModelosAlumno(filtros);
         }else {
            modelos =this.servicioDeModelos.obtenerPorIdDeUsuario(filtros.getIdUser());
         }  
        return ResponseEntity.ok(modelos);
    }
    
    //buscar modelos particular
    @PostMapping("BuscarModelosParticular")
    public  ResponseEntity<List<Modelo>> filtrarMisModelosParticular(@RequestBody FiltroModeloDto filtros) {

   	 List<Modelo> modelos;
     if(!filtros.getText().isEmpty()  ) {
        modelos =this.servicioDeModelos.filtrarBuscarModelosParticular(filtros);

     }else {
        modelos =this.servicioDeModelos.TodosModelosParticular();
     }     
        return ResponseEntity.ok(modelos);
    }
    
    //mis modelos particular
    @PostMapping("BuscarMisModelosParticular")
    public  ResponseEntity<List<Modelo>> filtrarModelosParticular(@RequestBody FiltroModeloDto filtros) {
    	 List<Modelo> modelos;
         if(!filtros.getText().isEmpty() ) {
            modelos =this.servicioDeModelos.filtrarMisModelosParticular(filtros);
         }else {
            modelos =this.servicioDeModelos.TodosMisModelosParticular(filtros.getIdUser());
         } 
        return ResponseEntity.ok(modelos);
    } 
    @PostMapping("{id}/ofertasDeResoluciones")
    public ResponseEntity ofertarResolucion(@RequestBody OfertaDeResolucionDto ofertaDeResolucionDto, @PathVariable("id") Long modeloId) {

        ofertaDeResolucionDto.setIdModelo(modeloId);
        this.servicioDeModelos.guardarOfertaDeResolucion(ofertaDeResolucionDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Modelo> obtenerModeloPorId( @PathVariable("id") Long modeloId){
        Modelo modelo = this.servicioDeModelos.obtenerModeloPorId(modeloId);
        return ResponseEntity.ok(modelo);
    }

    @GetMapping("obtenerPostulaciones/{id}")
    public ResponseEntity<List<OfertaDeResolucion>> obtenerPostulacionPorId( @PathVariable("id") Long modeloId){
    	List<OfertaDeResolucion> postulaciones = this.servicioDeModelos.obtenerPostulacionesPorModelo(modeloId);
        return ResponseEntity.ok(postulaciones);
    }

    @PostMapping("{id}/resoluciones")
    public ResponseEntity<Resolucion> resolverModelo(@RequestBody SolucionDeModeloDto solucionDeModeloDto) {
        Resolucion resolucion = this.servicioDeModelos.resolverModelo(solucionDeModeloDto);
    	servicioDeMail.enviarMailConfirmacionModelo(solucionDeModeloDto.getIdModelo());
        return ResponseEntity.ok(resolucion);
    }

    @PutMapping("{id}")
    public ResponseEntity<Modelo> actualizararModelo(@RequestBody ModeloDto modeloDto){
        Modelo modelo = this.servicioDeModelos.actualizarModelo(modeloDto);
        return ResponseEntity.ok(modelo);
    }
    @GetMapping("{id}/resoluciones")
    public ResponseEntity<List<Resolucion>> obtenerResolucionesPorModelo(@PathVariable("id") Long modeloId){
        List<Resolucion> resoluciones = this.servicioDeModelos.obtenerResolucionesPorModelo(modeloId);
        return ResponseEntity.ok(resoluciones);
    }
}
