package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.dtos.*;
import ar.edu.unlam.proyecto.entidades.*;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeAlumno;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeNiveles;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeUsuarios;
import ar.edu.unlam.proyecto.repositorios.RepositoriodeFotoPerfil;
import excepciones.ExcepcionMailRepetido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

@Service
public class ServicioDeAlumno {

    private RepositorioDeAlumno repositorioDeAlumno;
    private RepositorioDeUsuarios repositorioDeUsuarios;
    private RepositoriodeFotoPerfil repositorioDeFotoPerfil;
    private RepositorioDeNiveles repositorioDeNiveles;


    @Autowired
    public ServicioDeAlumno(RepositorioDeAlumno repositorioDeAlumno, RepositorioDeUsuarios repositorioDeUsuarios, RepositoriodeFotoPerfil repositorioDeFotoPerfil
    		,RepositorioDeNiveles repositorioDeNiveles) {
        this.repositorioDeAlumno = repositorioDeAlumno;
        this.repositorioDeUsuarios = repositorioDeUsuarios;
        this.repositorioDeFotoPerfil = repositorioDeFotoPerfil;
        this.repositorioDeNiveles = repositorioDeNiveles;
    }

    @Transactional
    public Usuario guardar(AlumnoDto alumnoDto) {
        List<Usuario> existe=this.repositorioDeUsuarios.filtrarPorMail(alumnoDto.getUsuario().getEmail());
        if(existe.size()== 0) {
        	 Usuario usuario = this.crearUsuario(alumnoDto.getUsuario());
             this.repositorioDeUsuarios.save(usuario);
             UsuarioAlumno alumno = this.crearAlumno(alumnoDto,usuario);
             this.repositorioDeAlumno.save(alumno);
            
             UsuarioDto usuarioDto = new UsuarioDto();
             usuarioDto.setFotoPerfil(alumnoDto.getUsuario().getFotoPerfil());

             	int index = (alumnoDto.getUsuario().getFotoPerfil().size()) - 1;
             	FotoPerfilDto foto = alumnoDto.getUsuario().getFotoPerfil().get(index);
             	FotoPerfil fotoPerfil = this.crearFotoPerfil(foto);
             	fotoPerfil.setUsuario(usuario);
             	this.repositorioDeFotoPerfil.save(fotoPerfil);
             	return usuario;
        }else {
  			throw new ExcepcionMailRepetido("Ya existe un usuario con el mail ingresado");
        }
       
    }

    @Transactional
    public List<UsuarioAlumno> obtenerTodos() {
        return this.repositorioDeAlumno.findAll();
    }


    @Transactional
    public UsuarioAlumno obtenerPorId(Long id) {
        UsuarioAlumno alumno = repositorioDeAlumno.buscarAlumnoPorIdUser(id);
        return alumno;
    }

    public List<FotoPerfil> obtenerFotoPerfilPorIdUsuario(Long usuarioId) {
        Optional<Usuario> usuario = this.repositorioDeUsuarios.findById(usuarioId);
        return this.repositorioDeFotoPerfil.findAllByUsuario(usuario.get());
    }

    @Transactional
    public Usuario modificarRegistro(AlumnoDto alumnoDto) {
  
            UsuarioAlumno alumno = this.modificarAlumno(alumnoDto);
            this.repositorioDeAlumno.save(alumno);
            Usuario usuario = this.modificarUsuario(alumnoDto.getUsuario(), alumno.getUsuario().getId());
            this.repositorioDeUsuarios.save(usuario);


 	        if (!alumnoDto.getUsuario().getFotoPerfil().isEmpty()) {
            int index = (alumnoDto.getUsuario().getFotoPerfil().size()) - 1;
        	FotoPerfilDto foto = alumnoDto.getUsuario().getFotoPerfil().get(index);
        	FotoPerfil fotoPerfil = this.crearFotoPerfil(foto);
        	fotoPerfil.setUsuario(usuario);
        	FotoPerfil fotoAntigua = this.repositorioDeFotoPerfil.buscarImagenPorUserId(alumnoDto.getIdUser());
            this.repositorioDeFotoPerfil.deleteById(fotoAntigua.getId());
        	this.repositorioDeFotoPerfil.save(fotoPerfil);
 	        }
        	return usuario;
          }
      



    private UsuarioAlumno crearAlumno(AlumnoDto alumnoDto, Usuario usuario) {
        UsuarioAlumno usuarioAlumno =  new UsuarioAlumno();
        usuarioAlumno.setUsuario(usuario);
        usuarioAlumno.setMateriasInteres(alumnoDto.getMateriasInteres());

        Nivel nivel = new Nivel();
        nivel.setId(alumnoDto.getNivelAcademico());
        usuarioAlumno.setNivel(nivel);

        return usuarioAlumno;
    }

    public Usuario crearUsuario(UsuarioDto usuarioDto) {
        Usuario usuario =  new Usuario();
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setDocumento(usuarioDto.getDocumento());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setContrasenia(usuarioDto.getContrasenia());
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setFechaNacimiento(usuarioDto.getFechaNacimiento());
        usuario.setRol(Usuario.rolUsuario.alumno);
        return usuario;
    }


    private FotoPerfil crearFotoPerfil(FotoPerfilDto fotoPerfilDto) {
        FotoPerfil fotoPerfil = new FotoPerfil();
        fotoPerfil.setExtension(fotoPerfilDto.getExtension());
        fotoPerfil.setDatos(fotoPerfilDto.getDatos());
        fotoPerfil.setTamanio(fotoPerfilDto.getTamanio());
        return fotoPerfil;
    }


    private UsuarioAlumno modificarAlumno(AlumnoDto alumnoDto) {
        UsuarioAlumno alumno = repositorioDeAlumno.buscarAlumnoPorIdUser(alumnoDto.getIdUser());
        alumno.setMateriasInteres(alumnoDto.getMateriasInteres());
        Optional<Nivel> nivel = repositorioDeNiveles.findById(alumnoDto.getNivelAcademico());
        Nivel nivelObj = nivel.get();
        alumno.setNivel(nivelObj);
        return alumno;
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

        return usuarioEncontrado;
    }



}
