package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.dtos.*;
import ar.edu.unlam.proyecto.entidades.*;
import ar.edu.unlam.proyecto.entidades.Clase.MetodoClase;
import ar.edu.unlam.proyecto.entidades.Clase.ModoClase;
import ar.edu.unlam.proyecto.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioDeModelos {

    private RepositorioDeModelos repositorioDeModelos;
    private RepositorioDeClases repositorioDeClases;
    private RepositorioDeDetalleClase repositorioDeDetalleClase;
    private RepositorioDeUsuarios repositorioDeUsuarios;
    private RepositorioDeArchivos repositorioDeArchivos;
    private RepositorioDeOfertasDeResolucion repositorioDeOfertasDeResolucion;
    private RepositorioDeResoluciones repositorioDeResoluciones;
    private RepositorioDeArchivosDeResolucion repositorioDeArchivosDeResolucion;
    private RepositorioDeProductos repositorioDeProductos;
    private RepositorioDeProfesor repositorioDeProesor;

    @Autowired
    public ServicioDeModelos(RepositorioDeModelos repositorioDeModelos,
                             RepositorioDeArchivos repositorioDeArchivos,
                             RepositorioDeOfertasDeResolucion repositorioDeOfertasDeResolucion,
                             RepositorioDeResoluciones repositorioDeResoluciones,
                             RepositorioDeArchivosDeResolucion repositorioDeArchivosDeResolucion,
                             RepositorioDeProductos repositorioDeProductos,
                             RepositorioDeUsuarios repositorioDeUsuarios,
                             RepositorioDeClases repositorioDeClases,
                             RepositorioDeDetalleClase repositorioDeDetalleClase,
                             RepositorioDeProfesor repositorioDeProesor) {
        this.repositorioDeModelos = repositorioDeModelos;
        this.repositorioDeArchivos = repositorioDeArchivos;
        this.repositorioDeOfertasDeResolucion = repositorioDeOfertasDeResolucion;
        this.repositorioDeResoluciones = repositorioDeResoluciones;
        this.repositorioDeArchivosDeResolucion = repositorioDeArchivosDeResolucion;
        this.repositorioDeProductos = repositorioDeProductos;
        this.repositorioDeUsuarios=repositorioDeUsuarios;
        this.repositorioDeClases=repositorioDeClases;
        this.repositorioDeDetalleClase=repositorioDeDetalleClase;
        this.repositorioDeProesor=repositorioDeProesor;
    }

    @Transactional
    public void guardar(ModeloDto modeloDto) {

        Modelo modelo = this.crearModelo(modeloDto);
        Optional<Usuario> usuario=this.repositorioDeUsuarios.findById(modeloDto.getUsuario());
        modelo.setUsuario(usuario.get());
        this.repositorioDeModelos.save(modelo);

        modeloDto.getArchivos().stream().forEach(archivoDto -> {
            Archivo archivo = this.crearArchivo(archivoDto);
            archivo.setModelo(modelo);
            this.repositorioDeArchivos.save(archivo);
        });
    }

    @Transactional
    public void guardarOfertaDeResolucion(OfertaDeResolucionDto ofertaDeResolucionDto) {
    	if(ofertaDeResolucionDto.getIdTipoResolucion()==2) {
    		Optional<Modelo> modelo=this.repositorioDeModelos.findById(ofertaDeResolucionDto.getIdModelo());
    		 Clase clase = new Clase();
    	        clase.setNombre("Clase explicación de modelo "+ modelo.get().getNombre());
    	        clase.setModo(ModoClase.INDIVIDUAL);
    	        clase.setMetodo(MetodoClase.ONLINE);
    	        clase.setPrecio(new BigDecimal(0));
    	        clase.setDescripcion("Clase especifica para explicación y resolución de modelos de examen");
    	        clase.setCupo(0);
    			clase.setFecha(LocalDateTime.now());
    	        clase.setNivel(modelo.get().getNivel());
    	        clase.setMateria(modelo.get().getMateria());
    			UsuarioProfesor usuarioProfesor = this.repositorioDeProesor.buscarProfesorPorIdUser(ofertaDeResolucionDto.getIdUsuario());
    			clase.setProfesor(usuarioProfesor);
    			clase.setPuntuacion(0);
    			clase.setModelo(modelo.get());
    	        this.repositorioDeClases.save(clase);

    			 if( ofertaDeResolucionDto.getDisponibilidad()!= null) {

    	        	 for(Date disponibilidad : ofertaDeResolucionDto.getDisponibilidad()) {
    	        	     Calendar cal = Calendar.getInstance();
    	        		 cal.setTime(disponibilidad);
    	        		 cal.set(Calendar.HOUR, cal.get(Calendar.HOUR));

    	                 DetalleClase detalle = new DetalleClase();
    	                 detalle.setCupoRestante(1);
    	                 detalle.setFecha(cal.getTime());
    	                 detalle.setClase(clase);
    	                 detalle.setUrl("/reunion/"+ clase.getId());
    	                 detalle.setEstado(DetalleClase.EstadoClase.DISPONIBLE);
    	                 Producto producto = detalle;
    	                 producto.setPrecioProducto(clase.getPrecio());
    	                 producto.setNombreProducto(clase.getNombre());
    					 this.repositorioDeProductos.save(producto);
    	             }
    	        }

    		
    	}
        OfertaDeResolucion ofertaDeResolucion = crearOfertaPendienteDeRespuesta(ofertaDeResolucionDto);
        this.repositorioDeProductos.save(ofertaDeResolucion);
    }
    //buscar modelos alumno
    @Transactional
    public List<Modelo> filtrarBuscarModelosAlumno(FiltroModeloDto filtros) {
        return this.repositorioDeModelos.filtrarBuscarModeloAlumno(filtros.getText());
   	
    }
    
    // buscar modelos alumno sin filtro
    @Transactional
    public List<Modelo> TodosModelosAlumno() {
         return this.repositorioDeModelos.todosModeloAlumno();
    }
    //mis modelos alumno
    @Transactional
    public List<Modelo> filtrarMisModelosAlumno(FiltroModeloDto filtros) {
    	  return this.repositorioDeModelos.filtrarMisModeloAlumno(filtros.getText(),filtros.getIdUser());
    }
    
    // mis modelos alumno sin filtro
    @Transactional
    public List<Modelo> TodosMisModelosAlumno(Long id) {
         return this.repositorioDeModelos.todosMisModeloAlumno(id);
    }
     // modelos particular
    @Transactional
    public List<Modelo> filtrarBuscarModelosParticular(FiltroModeloDto filtros) {
    	        return this.repositorioDeModelos.filtrarBuscarModelosParticular(filtros.getText());
    
    }
    //  modelos particular sin filtro
    @Transactional
    public List<Modelo> TodosModelosParticular() {
         return this.repositorioDeModelos.todosModeloParticular();
    }
    //mis modelos particular 
    @Transactional
    public List<Modelo> filtrarMisModelosParticular(FiltroModeloDto filtros) {
          return this.repositorioDeModelos.filtrarMisModelosParticular(filtros.getText(),filtros.getIdUser());
     	
    }
    
    //todos mis modelos particular sin filtro 
    @Transactional
    public List<Modelo> TodosMisModelosParticular(Long id) {
         return this.repositorioDeModelos.todosMisModeloParticular(id);
    }
    @Transactional
    public List<OfertaDeResolucion> obtenerPostulacionesPorModelo(Long id) {
    	
         return this.repositorioDeOfertasDeResolucion.obtenerPostulacionesPorModelo(id);
    	
    }
    private Modelo crearModelo(ModeloDto modeloDto) {
        Modelo modelo = new Modelo();
        modelo.setNombre(modeloDto.getNombre());
        Usuario usuario = new Usuario();
        usuario.setId(modeloDto.getUsuario());

        modelo.setPublico(modeloDto.isPublico());

        Institucion institucion = new Institucion();
        institucion.setId(modeloDto.getInstitucion());
        modelo.setInstitucion(institucion);

        Carrera carrera = new Carrera();
        carrera.setId(modeloDto.getCarrera());
        modelo.setCarrera(carrera);

        Nivel nivel = new Nivel();
        nivel.setId(modeloDto.getNivel());
        modelo.setNivel(nivel);

        Materia materia = new Materia();
        materia.setId(modeloDto.getMateria());
        modelo.setMateria(materia);

        modelo.setEstado(Modelo.EstadoModelo.ACTIVO);
        modelo.setFecha(LocalDateTime.now());
        return modelo;
    }

    private Archivo crearArchivo(ArchivoDto archivoDto) {

        Archivo archivo = new Archivo();
        archivo.setExtension(archivoDto.getExtension());
        archivo.setDatos(archivoDto.getDatos());
        archivo.setTamanio(archivoDto.getTamanio());
        return archivo;
    }

    public List<Modelo> obtenerTodos() {
        return this.repositorioDeModelos.findAll();
    }

    public List<Archivo> obtenerArchivosPorIdDeModelo(Long modeloId) {
        Optional<Modelo> modelo = this.repositorioDeModelos.findById(modeloId);
        return this.repositorioDeArchivos.findAllByModelo(modelo.get());
    }

    private OfertaDeResolucion crearOfertaPendienteDeRespuesta(OfertaDeResolucionDto ofertaDeResolucionDto) {
        OfertaDeResolucion ofertaDeResolucion = new OfertaDeResolucion();
        ofertaDeResolucion.setEstado(OfertaDeResolucion.EstadoOferta.PENDIENTE_DE_RESPUESTA);
        ofertaDeResolucion.setCosto(ofertaDeResolucionDto.getCosto());
        ofertaDeResolucion.setFechaDeCreacion(LocalDateTime.now());
        ofertaDeResolucion.setNombreProducto("Resolución de modelo");

        Modelo modelo = new Modelo();
        modelo.setId(ofertaDeResolucionDto.getIdModelo());
        ofertaDeResolucion.setModelo(modelo);

        TipoDeDemora demora = new TipoDeDemora();
        demora.setId(ofertaDeResolucionDto.getIdDemora());
        ofertaDeResolucion.setDemora(demora);

        TipoDeResolucion tipoResolucion = new TipoDeResolucion();
        tipoResolucion.setId(ofertaDeResolucionDto.getIdTipoResolucion());
        ofertaDeResolucion.setTipo(tipoResolucion);

        Usuario usuario = new Usuario();
        usuario.setId(ofertaDeResolucionDto.getIdUsuario());
        ofertaDeResolucion.setUsuario(usuario);
        return ofertaDeResolucion;
    }

    public Modelo obtenerModeloPorId(Long modeloId){
        Optional<Modelo> modelo = this.repositorioDeModelos.findById(modeloId);
        return modelo.get();
    }

    @Transactional
    public Resolucion resolverModelo(SolucionDeModeloDto solucionDeModeloDto) {
        Resolucion resolucion = this.crearResolucion(solucionDeModeloDto);
        this.repositorioDeResoluciones.save(resolucion);
        solucionDeModeloDto.getArchivos().stream().forEach(archivoDto -> {
            ArchivoDeResolucion archivo = this.crearArchivoDeResolucion(archivoDto);
            archivo.setResolucion(resolucion);
            this.repositorioDeArchivosDeResolucion.save(archivo);
        });
        return resolucion;
    }

    private ArchivoDeResolucion crearArchivoDeResolucion(ArchivoDto archivoDto) {
        ArchivoDeResolucion archivo = new ArchivoDeResolucion();
        archivo.setExtension(archivoDto.getExtension());
        archivo.setDatos(archivoDto.getDatos());
        archivo.setTamanio(archivoDto.getTamanio());
        return archivo;
    }

    private Resolucion crearResolucion(SolucionDeModeloDto solucionDeModeloDto) {
        Resolucion resolucion = new Resolucion();
        resolucion.setFecha(LocalDateTime.now());
        resolucion.setComentario(solucionDeModeloDto.getComentarioAdicional());

        Modelo modelo = new Modelo();
        modelo.setId(solucionDeModeloDto.getIdModelo());
        resolucion.setModelo(modelo);

        Usuario usuario = new Usuario();
        usuario.setId(solucionDeModeloDto.getIdUsuario());
        resolucion.setUsuario(usuario);

        return resolucion;
    }

    @Transactional
    public Modelo actualizarModelo(ModeloDto modeloDto) {
        Optional<Modelo> modeloOptional = this.repositorioDeModelos.findById(modeloDto.getId());
        Modelo modeloParaActualizar = modeloOptional.get();
        modeloParaActualizar.setEstado(modeloDto.getEstado());
        return modeloParaActualizar;
    }

    public List<Resolucion> obtenerResolucionesPorModelo(Long modeloId) {
        Modelo modeloResuelto = new Modelo();
        modeloResuelto.setId(modeloId);
        return this.repositorioDeResoluciones.findAllByModelo(modeloResuelto);
    }

    public List<Modelo> obtenerPorIdDeUsuario(Long idUsuario) {
        return this.repositorioDeModelos.obtenerTodosPorIdUsuario(idUsuario);
    }
}
