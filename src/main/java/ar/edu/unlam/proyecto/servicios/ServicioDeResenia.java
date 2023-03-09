package ar.edu.unlam.proyecto.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlam.proyecto.dtos.ReseniaDto;
import ar.edu.unlam.proyecto.entidades.Clase;
import ar.edu.unlam.proyecto.entidades.DetalleClase;
import ar.edu.unlam.proyecto.entidades.Modelo;
import ar.edu.unlam.proyecto.entidades.Producto;
import ar.edu.unlam.proyecto.entidades.Resenia;
import ar.edu.unlam.proyecto.entidades.Usuario;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeClases;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeDetalleClase;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeModelos;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeProductos;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeResenia;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeUsuarios;
import excepciones.ExcepcionLaReseniaYaExiste;
import excepciones.FechaNoDisponibleExcepcion;

@Service
public class ServicioDeResenia {
	  private RepositorioDeResenia repositorioDeResenia;
	  private RepositorioDeProductos repositorioDeProducto;
	  private RepositorioDeUsuarios repositorioDeUsuarios;
	  private RepositorioDeClases repositorioDeClases;
	  private RepositorioDeModelos repositorioDeModelo;
	  private RepositorioDeDetalleClase repositorioDeDetalleClases;

	    @Autowired
	    public ServicioDeResenia(RepositorioDeResenia repositorioDeResenia,
	    		 RepositorioDeProductos repositorioDeProducto,
	    		 RepositorioDeUsuarios repositorioDeUsuarios,
	    		 RepositorioDeClases repositorioDeClases,
	    		 RepositorioDeDetalleClase repositorioDeDetalleClases,
	    		 RepositorioDeModelos repositorioDeModelo) {
	        this.repositorioDeResenia = repositorioDeResenia;
	        this.repositorioDeProducto=repositorioDeProducto;
	        this.repositorioDeUsuarios=repositorioDeUsuarios;
	        this.repositorioDeClases=repositorioDeClases;
	        this.repositorioDeDetalleClases=repositorioDeDetalleClases;
	        this.repositorioDeDetalleClases=repositorioDeDetalleClases;
	    }

		public Resenia guardar(ReseniaDto resenia) {
			Resenia nueva= new Resenia();
			if(this.repositorioDeResenia.existeResenia(resenia.getId_producto(),resenia.getId_usuario()) == null) {
				Producto producto=this.repositorioDeProducto.findById(resenia.getId_producto()).get();
				Usuario usuario=this.repositorioDeUsuarios.findById(resenia.getId_usuario()).get();
				nueva.setPuntaje(resenia.getPuntaje());
				nueva.setComentario(resenia.getComentario());
				nueva.setFecha(LocalDateTime.now());
				nueva.setProducto(producto);
				nueva.setUsuario(usuario);
				
				Optional<Clase> esClase= this.repositorioDeClases.findById(resenia.getId_producto());
				if(esClase.isPresent()) {
					int puntuacionActual=esClase.get().getPuntuacion();
					int puntuacion=(puntuacionActual + resenia.getPuntaje()) / 5;
					esClase.get().setPuntuacion(Math.round(puntuacion));
				}
				/*Optional<Modelo> esModelo= this.repositorioDeModelo.findById(resenia.getId_producto());
                if(esModelo.isPresent()) {
					//a modelo le falta puntuacion
				}*/
				
			}else {
	  			throw new ExcepcionLaReseniaYaExiste("Ya se resenio este producto");

			}
		
			return this.repositorioDeResenia.save(nueva);
		}
		public List<Resenia> obtener(Long id ) {
			List<Resenia> resenias= new ArrayList();
			Optional<Clase> clase=this.repositorioDeClases.findById(id);
			Optional<List<DetalleClase>> detalles=this.repositorioDeDetalleClases.findByClase(clase.get());
			for(DetalleClase d:detalles.get()) {
				resenias.addAll(this.repositorioDeResenia.obtenerPorProducto(d.getId()));
			}
			return resenias;
		}
		
		public List<Resenia> obtenerIdUser(Long id ) {
		
			return this.repositorioDeResenia.obtenerPorUser(id);
		}

		public Object obtenerPorParticular(Long id) {
			return this.repositorioDeResenia.obtenerPorParticular(id);
		}



		

}
