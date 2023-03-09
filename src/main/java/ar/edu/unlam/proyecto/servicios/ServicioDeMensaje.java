package ar.edu.unlam.proyecto.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlam.proyecto.dtos.DetalleMensajeDto;
import ar.edu.unlam.proyecto.dtos.MensajeDto;
import ar.edu.unlam.proyecto.entidades.Mensaje;
import ar.edu.unlam.proyecto.entidades.Usuario;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeMensaje;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeUsuarios;
@Service

public class ServicioDeMensaje {
    private RepositorioDeMensaje repositorioDeMensaje;
    private RepositorioDeUsuarios repositorioDeUsuario;

    @Autowired
    public ServicioDeMensaje(RepositorioDeMensaje repositorioDeMensaje,RepositorioDeUsuarios repositorioDeUsuario){
        this.repositorioDeMensaje = repositorioDeMensaje;
        this.repositorioDeUsuario=repositorioDeUsuario;
    }
    
    @Transactional
    public void guardar(MensajeDto mensajeDto) {
          Mensaje mensaje= new Mensaje();
          mensaje.setContenido(mensajeDto.getContenido());
          mensaje.setAsunto(mensajeDto.getAsunto().trim());
          mensaje.setEliminadoReceptor(false);
          mensaje.setLeidoReceptor(false);
          mensaje.setEliminadoEmisor(false);
          mensaje.setLeidoEmisor(false);
          mensaje.setFecha(LocalDateTime.now());
          mensaje.setEmisor(this.repositorioDeUsuario.findById(mensajeDto.getEmisor()).get());
          mensaje.setReceptor(this.repositorioDeUsuario.findById(mensajeDto.getReceptor()).get());
          Mensaje nuevo=this.repositorioDeMensaje.save(mensaje);
          
          if(mensajeDto.getIdMensaje()!= null) {
        	  Mensaje mensajeReferenciado=this.repositorioDeMensaje.findById(mensajeDto.getIdMensaje()).get();
        	  if(mensajeReferenciado!=null) {
        		  mensajeReferenciado.setRespuesta(nuevo);
        	  }
          }
       
    }

    @Transactional
    public DetalleMensajeDto detalle(Long id) {
    	DetalleMensajeDto detalles=new DetalleMensajeDto();
        Mensaje mensaje=this.repositorioDeMensaje.findById(id).get();
        detalles.setMensaje(mensaje);
        detalles.setRespuestas(this.repositorioDeMensaje.respuestas(mensaje.getEmisor().getId(),mensaje.getReceptor().getId(),mensaje.getAsunto()));
        return detalles;
       
    }
    
    @Transactional
    public Mensaje obtenerPorId(Long id) {
        Mensaje mensaje=this.repositorioDeMensaje.findById(id).get();
      
        return mensaje;
       
    }
    @Transactional
    public List<Mensaje> recibidos(Long id) {
   	 List<Mensaje> mensajesBd = this.repositorioDeMensaje.recibidos(id);
	 List<Mensaje> filtro = new ArrayList<>();
	 List<String> asuntos = new ArrayList<>();
    	 for(Mensaje m: mensajesBd) {
    		 if(!asuntos.contains(m.getAsunto())) {
    			 asuntos.add(m.getAsunto());
    			 filtro.add(m);
    		 }
    	 }
        return filtro;
    }
    
    @Transactional
    public List<Mensaje> enviados(Long id) {
    	 List<Mensaje> mensajesBd = this.repositorioDeMensaje.enviados(id);
    	 List<Mensaje> filtro = new ArrayList<>();
    	 List<String> asuntos = new ArrayList<>();
        	 for(Mensaje m: mensajesBd) {
        		 if(!asuntos.contains(m.getAsunto())) {
        			 asuntos.add(m.getAsunto());
        			 filtro.add(m);
        		 }
        	 }
            return filtro;
        }
    

    @Transactional
    public List<Mensaje> eliminados(Long id) {
    	 List<Mensaje> mensajesBd = this.repositorioDeMensaje.eliminados(id);
    	 List<Mensaje> filtro = new ArrayList<>();
    	 List<String> asuntos = new ArrayList<>();
        	 for(Mensaje m: mensajesBd) {
        		 if(!asuntos.contains(m.getAsunto())) {
        			 asuntos.add(m.getAsunto());
        			 filtro.add(m);
        		 }
        	 }
            return filtro;
    }

    @Transactional
    public Mensaje eliminar(Long id,Long idUser) {
    	Mensaje mensaje=this.repositorioDeMensaje.findById(id).get();
    	List<Mensaje> mensajesConMismoAsunto=this.repositorioDeMensaje.respuestas(mensaje.getEmisor().getId(),mensaje.getReceptor().getId(),mensaje.getAsunto());
    	
    	for(Mensaje m :mensajesConMismoAsunto) {

        	if(m.getEmisor().getId()  == idUser) {
            	m.setEliminadoEmisor(true);
        	}
        	if(m.getReceptor().getId()  == idUser) {
            	m.setEliminadoReceptor(true);
        	}
    	}
    	
        return mensaje;
    }
    
    @Transactional
    public Mensaje restaurar(Long id,Long idUser) {
    	Mensaje mensaje=this.repositorioDeMensaje.findById(id).get();
    	List<Mensaje> mensajesConMismoAsunto=this.repositorioDeMensaje.respuestasEliminar(mensaje.getEmisor().getId(),mensaje.getReceptor().getId(),mensaje.getAsunto());
    	for(Mensaje m :mensajesConMismoAsunto) {

        	if(m.getEmisor().getId()  == idUser) {
            	m.setEliminadoEmisor(false);
        	}else
        	if(m.getReceptor().getId()  == idUser) {
            	m.setEliminadoReceptor(false);
        	}
    	}
    	
        return mensaje;
    }
    
    @Transactional
    public Mensaje mensajeLeido(Long id,Long idUser) {
    	Mensaje mensaje=this.repositorioDeMensaje.findById(id).get();
    	if(mensaje.getEmisor().getId() == idUser) {
        	mensaje.setLeidoEmisor(true);
    	}
    	if(mensaje.getReceptor().getId() == idUser) {
        	mensaje.setLeidoReceptor(true);
    	}
        return mensaje;
    }
    
    @Transactional
    public int checkearMensajesNoLeidos(Long idUser) {
    	List<Mensaje> mensajes=this.repositorioDeMensaje.noLeidos(idUser);
        return mensajes.size();
    }
}
