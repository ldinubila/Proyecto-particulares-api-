package ar.edu.unlam.proyecto.controladores;

import ar.edu.unlam.proyecto.dtos.DatosAcademicosDto;
import ar.edu.unlam.proyecto.dtos.ProfesorDto;
import ar.edu.unlam.proyecto.entidades.ArchivoDeDatosAcademicos;
import ar.edu.unlam.proyecto.entidades.DatosAcademicos;
import ar.edu.unlam.proyecto.entidades.UsuarioProfesor;
import ar.edu.unlam.proyecto.servicios.ServicioDeProfesor;
import excepciones.ExcepcionMailRepetido;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("v1/profesor")
public class ControladorDeProfesor {
	
	private ServicioDeProfesor servicioProfesor;

    @Autowired
    public ControladorDeProfesor(ServicioDeProfesor servicioProfesor){
        this.servicioProfesor = servicioProfesor;
    }
    
    
    @PostMapping("Guardar")
    public ResponseEntity <?> guardar(@RequestBody ProfesorDto profesor) {
        try {
    		return ResponseEntity.ok(this.servicioProfesor.guardar(profesor));
    	}catch (ExcepcionMailRepetido e) {
			return ResponseEntity.badRequest().body("Ya existe un usuario con el mail ingresado");
    	}
    }

    @PostMapping("Modificar")
    public ResponseEntity <?> modificar(@RequestBody ProfesorDto profesor) {
        try {
    		return ResponseEntity.ok(this.servicioProfesor.modificarRegistro(profesor));
    	}catch (ExcepcionMailRepetido e) {
			return ResponseEntity.badRequest().body("Ya existe un usuario con el mail ingresado");
    	}
    }
    
    @PostMapping("DatosAcademicos")
    public ResponseEntity <DatosAcademicos> datosAcadémicos(@RequestBody DatosAcademicosDto datosAcademicos) {
        this.servicioProfesor.guardarDatosAcademicos(datosAcademicos);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("eliminarDatoAacademico/{id}")
    public ResponseEntity <DatosAcademicos> eliminarDatoAacademico(@PathVariable("id") Long id) {
    	servicioProfesor.eliminarDatoAcademico(id);
        return ResponseEntity.noContent().build();
    }   
    
    @GetMapping("mostrarDatosAcademicos/{id}")
    public ResponseEntity <List<DatosAcademicos>> obtenerDatosAcademicos(@PathVariable("id") Long id) {
    	List<DatosAcademicos> user = this.servicioProfesor.obtenerPorIdDetalle(id);
        return ResponseEntity.ok(user);
    }
    
    
    @PostMapping("editarPerfil")
    public ResponseEntity <DatosAcademicos> editarPerfil(@RequestBody ProfesorDto profesor) {
        this.servicioProfesor.editarPerfilSave(profesor);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("obtenerTodos")
    public ResponseEntity obtenerTodos() {
        return ResponseEntity.ok( this.servicioProfesor.obtenerTodos());
    }
    
    @GetMapping("mostrarRegistro/{id}")
    public ResponseEntity<UsuarioProfesor> obtenerProfesor(@PathVariable("id") Long id) {
    	UsuarioProfesor user = this.servicioProfesor.obtenerPorId(id);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("todos")
    public ResponseEntity<List<UsuarioProfesor>> todos() {
    	List<UsuarioProfesor> users = this.servicioProfesor.todos();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("archivoDatoAcademico/{id}")
    public ResponseEntity<List<ArchivoDeDatosAcademicos>> obtenerarchivoDatoAcademico(@PathVariable("id") Long detalleAcademicoId) {
        List<ArchivoDeDatosAcademicos> archivo = this.servicioProfesor.obtenerArchivo(detalleAcademicoId);
        return ResponseEntity.ok(archivo);
    }
    
       

}
