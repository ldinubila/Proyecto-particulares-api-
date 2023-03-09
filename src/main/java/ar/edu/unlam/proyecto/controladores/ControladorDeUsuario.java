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

import ar.edu.unlam.proyecto.dtos.UsuarioDto;
import ar.edu.unlam.proyecto.entidades.Clase;
import ar.edu.unlam.proyecto.entidades.Usuario;
import ar.edu.unlam.proyecto.servicios.ServicioDeUsuario;

@RestController
@CrossOrigin
@RequestMapping("v1/usuario")
public class ControladorDeUsuario {

	   private ServicioDeUsuario servicioDeUsuario;
	   
	   @Autowired
	    public ControladorDeUsuario(ServicioDeUsuario servicioDeUsuario){
	        this.servicioDeUsuario = servicioDeUsuario;
	    }
	   
	    @GetMapping
		public ResponseEntity<List<Usuario>> verUsuarios() {
			 List<Usuario> usuarios =  this.servicioDeUsuario.getAllUsuarios();
		        return ResponseEntity.ok(usuarios);
		}
	    
	    
	    @GetMapping("Bloquear/{id}")
		public ResponseEntity  bloquear(@PathVariable("id") Long id) {
			 this.servicioDeUsuario.bloquearUsuario(id);
		     return ResponseEntity.noContent().build();
		}
	    
	    @GetMapping("buscador/{nombre}")
	    public ResponseEntity<List<Usuario>> buscador(@PathVariable("nombre") String busqueda) {
	        List<Usuario> usuario = this.servicioDeUsuario.buscador(busqueda);
	        return ResponseEntity.ok(usuario);
	    }  
}
