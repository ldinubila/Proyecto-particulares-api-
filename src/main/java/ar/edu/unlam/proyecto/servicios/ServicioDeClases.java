package ar.edu.unlam.proyecto.servicios;


import java.util.ArrayList;
import java.util.Calendar;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import ar.edu.unlam.proyecto.dtos.ClaseDto;
import ar.edu.unlam.proyecto.entidades.Clase;
import ar.edu.unlam.proyecto.entidades.UsuarioProfesor;
import ar.edu.unlam.proyecto.entidades.Clase.MetodoClase;
import ar.edu.unlam.proyecto.entidades.Clase.ModoClase;
import ar.edu.unlam.proyecto.entidades.Compra;
import ar.edu.unlam.proyecto.entidades.DetalleClase;
import ar.edu.unlam.proyecto.entidades.DetalleClase.EstadoClase;
import ar.edu.unlam.proyecto.entidades.Materia;
import ar.edu.unlam.proyecto.entidades.Mensaje;
import ar.edu.unlam.proyecto.entidades.Modelo;
import ar.edu.unlam.proyecto.entidades.Nivel;
import ar.edu.unlam.proyecto.entidades.Producto;
import ar.edu.unlam.proyecto.entidades.Usuario;
import ar.edu.unlam.proyecto.repositorios.*;
import excepciones.ClaseNoExisteExcepcion;
import excepciones.ExcepcionMailRepetido;
import excepciones.ExceptionEliminarClase;
import excepciones.FechaNoDisponibleExcepcion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.time.LocalDateTime;


@Service
public class ServicioDeClases {

    private RepositorioDeClases repositorioDeClases;
    private RepositorioDeUsuarios repositorioDeUsuarios;
    private RepositorioDeDetalleClase repositorioDeDetalleClase;
    private RepositorioDeMensaje repositorioDeMensaje;
    private RepositorioDeProductos repositorioDeProductos;
    private RepositorioDeCompras repositorioDeCompra;
	private RepositorioDeProfesor repositorioDeProfesor;

    @Autowired
    public ServicioDeClases(RepositorioDeClases repositorioDeClases,
    		                RepositorioDeMensaje repositorioDeMensaje,
    		                RepositorioDePago repositorioDePago,
                            RepositorioDeDetalleClase repositorioDeDetalleClase,
                            PasarelaDePagos pasarelaDePagos,
                            RepositorioDeUsuarios repositorioDeUsuarios,
                            RepositorioDeProductos repositorioDeProductos,
                            RepositorioDeCompras repositorioDeCompra,
							RepositorioDeProfesor repositorioDeProfesor
                            ) {
        this.repositorioDeClases = repositorioDeClases;
        this.repositorioDeDetalleClase = repositorioDeDetalleClase;
        this.repositorioDeMensaje=repositorioDeMensaje;
        this.repositorioDeUsuarios=repositorioDeUsuarios;
        this.repositorioDeProductos=repositorioDeProductos;
        this.repositorioDeCompra=repositorioDeCompra;
		this.repositorioDeProfesor=repositorioDeProfesor;
    }


    public List<Clase> obtenerTodas() {
        return this.repositorioDeClases.findAll();
    }

    public List<Clase> buscador(String busqueda) {
    	busqueda = "%" + busqueda;
    	List<Clase> clases = repositorioDeClases.filtrarPorNombre(busqueda);
    	if (clases.isEmpty()) {
    		clases = this.repositorioDeClases.devolverTodos();
    	}
        return clases;
    }

    public Clase buscadorPorId(Long id) {
    	 Optional<Clase> clase = this.repositorioDeClases.findById(id);
    	 return clase.get();
    }
    public List<DetalleClase> obtenerDisponibilidad(Long id) {
   	     Optional<Clase> clase = this.repositorioDeClases.findById(id);
   	     Optional<List<DetalleClase>> detalles = this.repositorioDeDetalleClase.findByClase(clase.get());
   	 return detalles.get();
   }
    @Transactional
    public void guardar(ClaseDto claseDto) {

        Clase clase = this.crearClase(claseDto);

        this.repositorioDeClases.save(clase);

        if( claseDto.getDisponibilidad()!= null) {

        	 for(Date disponibilidad : claseDto.getDisponibilidad()) {
        	     Calendar cal = Calendar.getInstance();
        		 cal.setTime(disponibilidad);
        		 cal.set(Calendar.HOUR, cal.get(Calendar.HOUR));

                 DetalleClase detalle = new DetalleClase();
                 detalle.setCupoRestante(claseDto.getCupo());
                 detalle.setFecha(cal.getTime());
                 detalle.setClase(clase);
                 detalle.setUrl("/reunion/"+ clase.getId());
                 detalle.setEstado(DetalleClase.EstadoClase.DISPONIBLE);
                 Producto producto = detalle;
                 producto.setPrecioProducto(clase.getPrecio());
                 producto.setNombreProducto(clase.getNombre());
				 this.repositorioDeProductos.save(producto);
            //   this.repositorioDeDetalleClase.save(detalle);
             }
        }
    }

    

    private Clase crearClase(ClaseDto claseDto) {
        Clase clase = new Clase();
        clase.setNombre(claseDto.getNombre());
        clase.setModo(claseDto.getModo());
        clase.setMetodo(claseDto.getMetodo());
        clase.setPrecio(BigDecimal.valueOf(claseDto.getPrecio()));
        clase.setDescripcion(claseDto.getDescripcion());
        clase.setCupo(claseDto.getCupo());
		clase.setFecha(LocalDateTime.now());

        Nivel nivel = new Nivel();
        nivel.setId(claseDto.getNivel());
        clase.setNivel(nivel);

        Materia materia = new Materia();
        materia.setId(claseDto.getMateria());
        clase.setMateria(materia);

		UsuarioProfesor usuarioProfesor = this.repositorioDeProfesor.buscarProfesorPorIdUser(claseDto.getId_profesor());
		clase.setProfesor(usuarioProfesor);
		
		clase.setPuntuacion(0);



        return clase;
    }

    public List<Clase> filtrar( MetodoClase tipo, Long nivel, ModoClase modo,String busqueda) {
    	
    	List<Clase> clases = repositorioDeClases.filtrarFecha(busqueda,tipo.name(),nivel,modo.name());


    	if (clases.isEmpty()) {
    		clases = this.repositorioDeClases.devolverTodos();
    	}
        return clases;
    }

    @Transactional
    public List<Clase> obtenerClasesPorParticular(Long userId) {
		UsuarioProfesor particular = repositorioDeProfesor.buscarProfesorPorIdUser(userId);
        List<Clase> clases = this.repositorioDeClases.filtrarPorParticular(particular.getId());
        return clases;
    }
    
    @Transactional
    public List<Usuario> participantes(Long id) {
        List<Usuario> usuarios = new ArrayList();
        List<Compra> compras = this.repositorioDeCompra.filtrarPorDetalleClase(id);
        Optional<DetalleClase> detalle = this.repositorioDeDetalleClase.findById(id);
    	if(compras.size() > 0) {
    		for(Compra c: compras) {
    				 usuarios.add(c.getUsuario());
    		  }
          }
    	Optional<Usuario> particular = this.repositorioDeUsuarios.findById(detalle.get().getClase().getProfesor().getId());
        usuarios.add(particular.get());
        return usuarios;
    }
    @Transactional
    public List<Compra> compras(Long id) {
    	List<Compra> compras= new ArrayList();
		Optional<Clase> clase=this.repositorioDeClases.findById(id);
		Optional<List<DetalleClase>> detalles=this.repositorioDeDetalleClase.findByClase(clase.get());
		for(DetalleClase d:detalles.get()) {
			//compras.addAll(this.repositorioDeCompra.filtrarPorDetalleClase(d.getId()));
		}
		return compras;
    }
    @Transactional
    public List<Producto> obtenerClasesPorAlumno(Long id) {
    	List<Producto> clases= new ArrayList();
    		 List<Compra> compras = this.repositorioDeCompra.filtrarPorUsuario(id);
    		 if(compras.size() > 0) {
    			 for(Compra c: compras) {
        			 clases.add(c.getProducto());
    			 }
    		 }
    	
       
        return clases;
    }
    @Transactional
    public EstadoClase obtenerEstado(Long id) {
       
        Optional<DetalleClase> detalle = this.repositorioDeDetalleClase.findById(id);
        return detalle.get().getEstado();
    }
    @Transactional	
    public Clase modificarClase(Long id,ClaseDto claseDto) {
 	     Optional<Clase> clase = this.repositorioDeClases.findById(id);
 	     clase.get().setNombre(claseDto.getNombre());
 	     clase.get().setDescripcion(claseDto.getDescripcion());
  	     Optional<List<DetalleClase>> disponibilidad = this.repositorioDeDetalleClase.findByClase(clase.get());

 	     boolean claseContratada = disponibilidad.get().stream()
	               .anyMatch(t -> (((DetalleClase) t).getEstado()).equals(DetalleClase.EstadoClase.NODISPONIBLE));
 	     if(!claseContratada) {

 	 	     clase.get().setModo(claseDto.getModo());
 	         clase.get().setMetodo(claseDto.getMetodo());
 	         clase.get().setPrecio(BigDecimal.valueOf(claseDto.getPrecio()));
 	         clase.get().setCupo(claseDto.getCupo());
 	         
 	         Nivel nivel = new Nivel();
 	         nivel.setId(claseDto.getNivel());
 	         clase.get().setNivel(nivel);

 	         Materia materia = new Materia();
 	         materia.setId(claseDto.getMateria());
 	         clase.get().setMateria(materia);

 	         clase.get().setFecha(LocalDateTime.now());
 	     }

 	    for(Date fecha: claseDto.getDisponibilidad()) {
 	    	Optional<DetalleClase>  detalleClase=this.repositorioDeDetalleClase.findByClaseAndFecha(clase,fecha);
 	    	  if(!detalleClase.isPresent()) {
 	    		DetalleClase detalle = new DetalleClase();
 	    		 Calendar cal = Calendar.getInstance();
        		 cal.setTime(fecha);
        		 cal.set(Calendar.HOUR, cal.get(Calendar.HOUR));
                detalle.setCupoRestante(clase.get().getCupo());
                detalle.setFecha(cal.getTime());
                detalle.setClase(clase.get());
                detalle.setEstado(DetalleClase.EstadoClase.DISPONIBLE);
                Producto producto = detalle;
                producto.setPrecioProducto(clase.get().getPrecio());
                producto.setNombreProducto(clase.get().getNombre());
				 this.repositorioDeProductos.save(producto); 	    	  }
 	     }

 	     for(DetalleClase detalle: disponibilidad.get()) {
 	    	 boolean fechaExists =  claseDto.getDisponibilidad().stream()
 	               .anyMatch(t -> t.equals(detalle.getFecha()));
 	    	 if(!fechaExists && detalle.getEstado().equals(DetalleClase.EstadoClase.DISPONIBLE)) {
               this.repositorioDeDetalleClase.delete(detalle);
	    	  }else if(!fechaExists && (detalle.getCupoRestante()-1) == 0){	
	  			throw new FechaNoDisponibleExcepcion("No puede agregar o eliminar una fecha que ya notiene cupo");
	    	  }
	     }
		return clase.get();
    }



    @Transactional
    public void eliminarClase(Long id) {
        try {
   
        	Clase clase = repositorioDeClases.getById(id);
        	Optional<List<DetalleClase>> detalleClase = repositorioDeDetalleClase.findByClase(clase);
        	List<DetalleClase> detalleClaseObj = detalleClase.get();
        	for (DetalleClase detalle : detalleClaseObj) {
        		if(detalle.getEstado() != DetalleClase.EstadoClase.INICIADA && 
        		detalle.getEstado() != DetalleClase.EstadoClase.NODISPONIBLE &&
        		!(detalle.getEstado() == DetalleClase.EstadoClase.DISPONIBLE && detalle.getCupoRestante() < clase.getCupo() )) {
            		this.repositorioDeDetalleClase.deleteById(detalle.getId());
        		}else {
          			throw new ExceptionEliminarClase("No se puede eliminar una clase que tenga detalle clases en uso.");
        		}
        	}
            this.repositorioDeClases.deleteById(id);        
            } catch (Throwable throwable) {
      			throw new ExceptionEliminarClase("No se puede eliminar una clase que tenga detalle clases en uso.");
            }
    }
    
    @Transactional
    public DetalleClase claseIniciada(Long id,String link) {
	     Optional<DetalleClase> claseDetalle = this.repositorioDeDetalleClase.findById(id);
	     Clase clase = claseDetalle.get().getClase();
	     
	     Usuario nuestroUsuario = this.repositorioDeUsuarios.nuestroUsuario("web.estudia.encasa@gmail.com");

	     if(claseDetalle.get()!= null) {
    		 Compra compra = this.repositorioDeCompra.obtenerPorDetalleClase(claseDetalle.get().getId());

	    	 claseDetalle.get().setEstado(DetalleClase.EstadoClase.INICIADA);
	    	 claseDetalle.get().setUrl(link);
	    	
	    		 Mensaje mensaje= new Mensaje();
		    	 mensaje.setContenido("<p>Hola! te avisamos que unas de tus clases ya ha iniciado</p>"
		    			 +" <h1 class='subtitle'>"+clase.getNombre()+"</h1>"
		    			 +" <h1 class='subtitle'>"+clase.getFecha()+"</h1>"
		    	 		+ "<a class='link-clase' href='"+link+"'"+"> Ir a mi clase<a>");
		    	 mensaje.setAsunto("Clase de "+clase.getNombre()+" iniciada");
		    	 mensaje.setEmisor(nuestroUsuario);
		    	 mensaje.setReceptor(compra.getUsuario());
		    	 mensaje.setEliminadoEmisor(false);
		         mensaje.setLeidoEmisor(false);
		         mensaje.setFecha(LocalDateTime.now());
		        
		    	 this.repositorioDeMensaje.save(mensaje);
	    	
	      }else {
	  			throw new ClaseNoExisteExcepcion("La clase no existe");
	     }
	    	return claseDetalle.get();
	
    }

    @Transactional
    public DetalleClase claseFinalizada(Long id) {
	     Optional<DetalleClase> clase = this.repositorioDeDetalleClase.findById(id);
	     if(clase.isPresent()) {
	    	 clase.get().setEstado(DetalleClase.EstadoClase.FINALIZADA);
	     }else {
	  		throw new ClaseNoExisteExcepcion("La clase no existe");

	     }
	     return clase.get();
    }

    @Transactional
    public DetalleClase detalleClasePorID(Long id) {
     
    	Optional<DetalleClase>  detalle=this.repositorioDeDetalleClase.findById(id);
    	if(detalle.isPresent()) {
    		return detalle.get();
    	}else {
    		return null;
    	}
    }


	public List<DetalleClase> obtenerDisponibilidadModelos(Long id) {
   	     List<DetalleClase> detalles = this.repositorioDeDetalleClase.findByModeloId(id);
   	 return detalles;
	}
}
