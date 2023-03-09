package ar.edu.unlam.proyecto.servicios;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import ar.edu.unlam.proyecto.dtos.FotoPerfilDto;
import ar.edu.unlam.proyecto.entidades.*;
import ar.edu.unlam.proyecto.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.unlam.proyecto.dtos.ArchivoDto;
import ar.edu.unlam.proyecto.dtos.DatosAcademicosDto;
import ar.edu.unlam.proyecto.dtos.ProfesorDto;
import ar.edu.unlam.proyecto.dtos.SolucionDeModeloDto;
import ar.edu.unlam.proyecto.dtos.UsuarioDto;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeArchivosDatosAcademicos;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeDatosAcademicos;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeProfesor;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeUsuarios;
import excepciones.ExcepcionMailRepetido;


@Service
public class ServicioDeProfesor {

	private RepositorioDeProfesor repositorioDeProfesor;
	private RepositorioDeUsuarios repositorioDeUsuarios;
	private RepositorioDeDatosAcademicos repositorioDeDatosAcademicos;
	private RepositorioDeArchivosDatosAcademicos repositorioDeArchivosDatosAcademicos;
	private RepositoriodeFotoPerfil repositorioDeFotoPerfil;

	@Autowired
	public ServicioDeProfesor(RepositorioDeProfesor repositorioDeProfesor, RepositorioDeUsuarios repositorioDeUsuarios,
			RepositorioDeDatosAcademicos repositorioDeDatosAcademicos, RepositoriodeFotoPerfil repositorioDeFotoPerfil,
			RepositorioDeArchivosDatosAcademicos repositorioDeArchivosDatosAcademicos) {
		this.repositorioDeProfesor = repositorioDeProfesor;
		this.repositorioDeUsuarios = repositorioDeUsuarios;
		this.repositorioDeDatosAcademicos = repositorioDeDatosAcademicos;
		this.repositorioDeFotoPerfil = repositorioDeFotoPerfil;
	    this.repositorioDeArchivosDatosAcademicos = repositorioDeArchivosDatosAcademicos;
	}

	@Transactional
	public Usuario guardar(ProfesorDto profesorDto) {
	    List<Usuario> existe=this.repositorioDeUsuarios.filtrarPorMail(profesorDto.getUsuario().getEmail());
	       if(existe.size()== 0) {
		Pattern patternEmail = Pattern.compile(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mather = patternEmail.matcher(profesorDto.getUsuario().getEmail());
		List<Usuario> mailEnUso = repositorioDeUsuarios.filtrarPorMail(profesorDto.getUsuario().getEmail());

		if (mather.find() != true || !mailEnUso.isEmpty()) {
			System.out.println("error al crear usuario.");
		} else {
			Usuario usuario = this.crearUsuario(profesorDto.getUsuario());
			this.repositorioDeUsuarios.save(usuario);
			UsuarioProfesor profesor = this.crearProfesor(profesorDto, usuario);
			this.repositorioDeProfesor.save(profesor);
			
			int index = (profesorDto.getUsuario().getFotoPerfil().size()) - 1;
        	FotoPerfilDto foto = profesorDto.getUsuario().getFotoPerfil().get(index);
        	FotoPerfil fotoPerfil = this.crearFotoPerfil(foto);
        	fotoPerfil.setUsuario(usuario);
        	this.repositorioDeFotoPerfil.save(fotoPerfil);
        	
		}
		return null;
	       }else {
	  			throw new ExcepcionMailRepetido("Ya existe un usuario con el mail ingresado");
	        }
	}

	private UsuarioProfesor crearProfesor(ProfesorDto profesorDto, Usuario usuario) {
		UsuarioProfesor usuarioProfesor = new UsuarioProfesor();
		usuarioProfesor.setUsuario(usuario);
		usuarioProfesor.setExperiencia(profesorDto.getExperiencia());
		return usuarioProfesor;

	}

	public Usuario crearUsuario(UsuarioDto usuarioDto) {
		Usuario usuario = new Usuario();
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setApellido(usuarioDto.getApellido());
		usuario.setDocumento(usuarioDto.getDocumento());
		usuario.setEmail(usuarioDto.getEmail());
		usuario.setContrasenia(usuarioDto.getContrasenia());
		usuario.setTelefono(usuarioDto.getTelefono());
		usuario.setFechaNacimiento(usuarioDto.getFechaNacimiento());
		usuario.setRol(Usuario.rolUsuario.particular);
		return usuario;
	}

	private FotoPerfil crearFotoPerfil(FotoPerfilDto fotoPerfilDto) {
		FotoPerfil fotoPerfil = new FotoPerfil();
		fotoPerfil.setExtension(fotoPerfilDto.getExtension());
		fotoPerfil.setDatos(fotoPerfilDto.getDatos());
		fotoPerfil.setTamanio(fotoPerfilDto.getTamanio());
		return fotoPerfil;
	}

	@Transactional
	public Usuario modificarRegistro(ProfesorDto profesorDto) {

			UsuarioProfesor profesor = this.modificarProfesor(profesorDto);
			this.repositorioDeProfesor.save(profesor);
			Usuario usuario = this.modificarUsuario(profesorDto.getUsuario(), profesor.getUsuario().getId());
			this.repositorioDeUsuarios.save(usuario);
			
	        if (!profesorDto.getUsuario().getFotoPerfil().isEmpty()) {
	        int index = (profesorDto.getUsuario().getFotoPerfil().size()) - 1;
        	FotoPerfilDto foto = profesorDto.getUsuario().getFotoPerfil().get(index);
        	FotoPerfil fotoPerfil = this.crearFotoPerfil(foto);
        	fotoPerfil.setUsuario(usuario);
        	FotoPerfil fotoAntigua = this.repositorioDeFotoPerfil.buscarImagenPorUserId(profesorDto.getIdUsuario());
            this.repositorioDeFotoPerfil.deleteById(fotoAntigua.getId());
        	this.repositorioDeFotoPerfil.save(fotoPerfil);
	        }
			return usuario;

		}
	      
	private UsuarioProfesor modificarProfesor(ProfesorDto profesorDto) {
		UsuarioProfesor particular = repositorioDeProfesor.buscarProfesorPorIdUser(profesorDto.getId());
		particular.setExperiencia(profesorDto.getExperiencia());
		particular.setLocalidad(profesorDto.getLocalidad());
		return particular;
	}

	public Usuario modificarUsuario(UsuarioDto usuarioDto, Long id) {
		Optional<Usuario> usuario = this.repositorioDeUsuarios.findById(id);
		Usuario usuarioEncontrado = usuario.get();
		usuarioEncontrado.setNombre(usuarioDto.getNombre());
		usuarioEncontrado.setApellido(usuarioDto.getApellido());
		usuarioEncontrado.setDocumento(usuarioDto.getDocumento());
		usuarioEncontrado.setEmail(usuarioDto.getEmail());
		usuarioEncontrado.setContrasenia(usuarioDto.getContrasenia());
		usuarioEncontrado.setTelefono(usuarioDto.getTelefono());
		usuarioEncontrado.setFechaNacimiento(usuarioDto.getFechaNacimiento());
	//	usuarioEncontrado.setFotoPerfil(usuarioDto.getFotoPerfil());
		return usuarioEncontrado;
	}

	  @Transactional
	    public void guardarDatosAcademicos(DatosAcademicosDto datosAcademicos) {
	        DatosAcademicos datos = this.crearDatoAcademico(datosAcademicos);
	        this.repositorioDeDatosAcademicos.save(datos);
	        int index = (datosAcademicos.getArchivos().size()) - 1;
        	ArchivoDto archivoDto = datosAcademicos.getArchivos().get(index);
        	ArchivoDeDatosAcademicos archivo = this.crearArchivo(archivoDto);
            archivo.setDatosAcademicos(datos);
            this.repositorioDeArchivosDatosAcademicos.save(archivo);
	    }
	  
	
    private ArchivoDeDatosAcademicos crearArchivo(ArchivoDto archivoDto) {
    	ArchivoDeDatosAcademicos archivo = new ArchivoDeDatosAcademicos();
          archivo.setExtension(archivoDto.getExtension());
          archivo.setDatos(archivoDto.getDatos());
          archivo.setTamanio(archivoDto.getTamanio());
          return archivo;
    }

	public DatosAcademicos crearDatoAcademico(DatosAcademicosDto datosAcademicos) {
		// validar fecha back
		DatosAcademicos datos = new DatosAcademicos();
		datos.setTitulo(datosAcademicos.getTitulo());
		datos.setDocumento(datosAcademicos.getDocumento());
		datos.setFechaInicio(datosAcademicos.getFechaInicio());
		datos.setFechaFin(datosAcademicos.getFechaFin());
		Optional<UsuarioProfesor> profesor = this.repositorioDeProfesor.findById(datosAcademicos.getIdProfesor());
		UsuarioProfesor profesorEncontrado = profesor.get();
		datos.setProfesor(profesorEncontrado);
		return datos;
	}

	@Transactional
	public void eliminarDatoAcademico(Long id) {
		Optional<DatosAcademicos> dato = this.repositorioDeDatosAcademicos.findById(id);
		DatosAcademicos datoAcademico = dato.get();
		List<ArchivoDeDatosAcademicos> archivo = this.repositorioDeArchivosDatosAcademicos.findAllByDatosAcademicos(datoAcademico);
		this.repositorioDeArchivosDatosAcademicos.deleteAll(archivo);
		this.repositorioDeDatosAcademicos.deleteById(id);;
	}
	
	@Transactional
	public void editarPerfilSave(ProfesorDto profesor) {
		UsuarioProfesor profesorEditar = this.editarPerfil(profesor);
		this.repositorioDeProfesor.save(profesorEditar);
	}

	public UsuarioProfesor editarPerfil(ProfesorDto profesor) {
	
		Optional<UsuarioProfesor> profesorBusqueda = this.repositorioDeProfesor.findById(profesor.getId());
		UsuarioProfesor usuarioProf = profesorBusqueda.get();
		usuarioProf.setLocalidad(profesor.getLocalidad());
		usuarioProf.setVideo(profesor.getVideo());
		usuarioProf.setExperiencia(profesor.getExperiencia());
		return usuarioProf;
	}
    @Transactional
    public List<UsuarioProfesor> obtenerTodos() {
        return this.repositorioDeProfesor.findAll();
    }

	
	public UsuarioProfesor obtenerPorId(Long id) {
		UsuarioProfesor particular = repositorioDeProfesor.buscarProfesorPorIdUser(id);
		return particular;
	}
	
	public UsuarioProfesor obtenerParticularPorId(Long id) {
		Optional<UsuarioProfesor> particular = repositorioDeProfesor.findById(id);
		return particular.get();
	}
	
	public List<DatosAcademicos> obtenerPorIdDetalle(Long id) {
		List<DatosAcademicos> particular = repositorioDeDatosAcademicos.buscarProfesorPorIdUser(id);
		return particular;
	}

	public List<UsuarioProfesor> todos() {
		
		return repositorioDeProfesor.findAll();
	}

    public List<ArchivoDeDatosAcademicos> obtenerArchivo(Long detalleAcademicoId) {
        Optional<DatosAcademicos> datos = this.repositorioDeDatosAcademicos.findById(detalleAcademicoId);
        return this.repositorioDeArchivosDatosAcademicos.findAllByDatosAcademicos(datos.get());
    }
	

}
