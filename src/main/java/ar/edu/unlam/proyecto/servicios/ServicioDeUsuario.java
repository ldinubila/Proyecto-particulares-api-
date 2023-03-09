package ar.edu.unlam.proyecto.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlam.proyecto.entidades.Clase;
import ar.edu.unlam.proyecto.entidades.Usuario;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeUsuarios;

@Service
public class ServicioDeUsuario {

	private RepositorioDeUsuarios repositorioDeUsuarios;

	@Autowired
	public ServicioDeUsuario(RepositorioDeUsuarios repositorioDeUsuarios) {
		this.repositorioDeUsuarios = repositorioDeUsuarios;
	}

	public Usuario LoginUsuario(String emailId, String password) {
		Boolean bloqueado = false;
		Usuario user = repositorioDeUsuarios.login(emailId, password, bloqueado);
		return user;
	}

	public List<Usuario> getAllUsuarios() {
		List<Usuario> list = repositorioDeUsuarios.devolverUsuarios();
		return list;
	}

	public void bloquearUsuario(Long id) {
		Optional<Usuario> usuario = repositorioDeUsuarios.findById(id);
		Usuario user = usuario.get();
		if(user.isBloqueado() == true) {
		user.setBloqueado(false);
		}else {
		user.setBloqueado(true);
		}
		repositorioDeUsuarios.save(user);
		
	}
	
	 public List<Usuario> buscador(String busqueda) {
	    	busqueda = "%" + busqueda;
	    	List<Usuario> usuario = repositorioDeUsuarios.filtrarPorNombre(busqueda);
	    	if (usuario.isEmpty()) {
	    		usuario = this.repositorioDeUsuarios.devolverUsuarios();
	    	}
	        return usuario;
	    }

}
