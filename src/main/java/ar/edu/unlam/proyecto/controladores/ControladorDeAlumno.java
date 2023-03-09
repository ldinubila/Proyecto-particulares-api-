package ar.edu.unlam.proyecto.controladores;

import ar.edu.unlam.proyecto.dtos.AlumnoDto;
import ar.edu.unlam.proyecto.entidades.*;
import ar.edu.unlam.proyecto.servicios.ServicioDeAlumno;
import excepciones.ExcepcionMailRepetido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1/alumnos")
public class ControladorDeAlumno {

    private ServicioDeAlumno servicioDeAlumno;

    @Autowired
    public ControladorDeAlumno(ServicioDeAlumno servicioDeAlumno) {
        this.servicioDeAlumno = servicioDeAlumno;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody AlumnoDto alumno) {
    	try {
    		return ResponseEntity.ok(this.servicioDeAlumno.guardar(alumno));
    	}catch (ExcepcionMailRepetido e) {
			return ResponseEntity.badRequest().body("Ya existe un usuario con el mail ingresado");
    	}
    }

    @GetMapping("obtenerTodos")
    public ResponseEntity obtenerTodos() {
        return ResponseEntity.ok( this.servicioDeAlumno.obtenerTodos());
    }


    @GetMapping("fotoPerfil/{id}")
    public ResponseEntity<List<FotoPerfil>> obtenerFotoPerfilPorUsuario(@PathVariable("id") Long usuarioId) {
        List<FotoPerfil> fotoPerfil = this.servicioDeAlumno.obtenerFotoPerfilPorIdUsuario(usuarioId);
        return ResponseEntity.ok(fotoPerfil);
    }


    @GetMapping("mostrarRegistro/{id}")
    public ResponseEntity<UsuarioAlumno> obtenerAlumno(@PathVariable("id") Long id) {
    	UsuarioAlumno user = this.servicioDeAlumno.obtenerPorId(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("modificar")
    public ResponseEntity <?> modificar(@RequestBody AlumnoDto alumno) {
        try {
    		return ResponseEntity.ok(this.servicioDeAlumno.modificarRegistro(alumno));
    	}catch (ExcepcionMailRepetido e) {
			return ResponseEntity.badRequest().body("Ya existe un usuario con el mail ingresado");
    	}
    }
    
}
