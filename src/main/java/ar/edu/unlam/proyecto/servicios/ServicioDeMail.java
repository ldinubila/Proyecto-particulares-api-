package ar.edu.unlam.proyecto.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlam.proyecto.entidades.Modelo;
import ar.edu.unlam.proyecto.entidades.Usuario;
import ar.edu.unlam.proyecto.mail.Mail;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeModelos;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeUsuarios;

@Service
public class ServicioDeMail {
	
	private RepositorioDeUsuarios repositorioDeUsuarios;
    private RepositorioDeModelos repositorioDeModelos;

	@Autowired
	public ServicioDeMail(RepositorioDeUsuarios repositorioDeUsuarios,RepositorioDeModelos repositorioDeModelos) {
		this.repositorioDeUsuarios = repositorioDeUsuarios;
		this.repositorioDeModelos = repositorioDeModelos;
	}
	
	 public void enviarMail(Long id) {
		Optional<Usuario> userBusqueda = repositorioDeUsuarios.findById(id);
		if(userBusqueda.isPresent()) {
			Usuario user = userBusqueda.get();
		    String destinatario =  user.getEmail();
		    String asunto = "ESTUDIA EN CASA | COMPRAS";
		    String cuerpo = "Su compra ha sido confirmada.";

		    Mail.enviarConGMail(destinatario, asunto, cuerpo);
		}
		
	}
	 
	 public void enviarMailConfirmacionModelo(Long id) {
		Optional<Modelo> modelo = repositorioDeModelos.findById(id);
		Modelo modeloObj = modelo.get();
		Optional<Usuario> usuario = repositorioDeUsuarios.findById(modeloObj.getUsuario().getId());
		Usuario usuarioObj = usuario.get();
	    String destinatario =  usuarioObj.getEmail();
	    String asunto = "ESTUDIA EN CASA | MODELOS";
	    String cuerpo = "Su modelo ha sido resuelto.";

	    Mail.enviarConGMail(destinatario, asunto, cuerpo);
	}
}
