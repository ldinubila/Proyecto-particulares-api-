package ar.edu.unlam.proyecto.componentes;

import ar.edu.unlam.proyecto.entidades.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Random;

public class DatosPrecargados {

    @Autowired
    private Reloj reloj;

    private static final Random random = new Random(System.currentTimeMillis());

    public static long numeroRandomEntre(long minimo, long maximo) {
        return random.longs(minimo, maximo).findFirst().getAsLong();
    }

    public static int numeroRandomEntre(int minimo, int maximo) {
        return random.ints(minimo, maximo).findFirst().getAsInt();
    }

    public static Usuario crear(String nombre, String apellido, String email, String password, Usuario.rolUsuario rol) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDocumento(numeroRandomEntre(20_000_000L, 40_000_000L));
        usuario.setEmail(email);
        usuario.setContrasenia(password);
        usuario.setBloqueado(false);
        usuario.setRol(rol);
        usuario.setFechaNacimiento(fechaDeNacimientoRandom());
        usuario.setTelefono(numeroRandomEntre(1_000_000L, 9_999_999L));
        return usuario;
    }

    public static UsuarioProfesor crear(String experiencia, String localidad, Usuario usuario) {
        UsuarioProfesor usuarioProfesor = new UsuarioProfesor();
        usuarioProfesor.setExperiencia(experiencia);
        usuarioProfesor.setLocalidad(localidad);
        usuarioProfesor.setUsuario(usuario);
        return usuarioProfesor;
    }

    private static UsuarioAlumno crear(String intereses, Niveles niveles, Usuario usuario) {
        UsuarioAlumno alumno = new UsuarioAlumno();
        alumno.setUsuario(usuario);
        alumno.setMateriasInteres(intereses);
        alumno.setNivel(niveles.instancia());
        return alumno;
    }

    private static Date fechaDeNacimientoRandom() {
        int anios = numeroRandomEntre(18, 75);
        return Date.valueOf(LocalDate.now().minusYears(anios));
    }

    private static FotoPerfil crear(String archivo, String extension, double tamanio, Usuario usuario) {
        FotoPerfil foto = new FotoPerfil();
        foto.setDatos(getDatos(archivo));
        foto.setExtension(extension);
        foto.setTamanio(tamanio);
        foto.setUsuario(usuario);
        return foto;
    }

    private static byte[] getDatos(String archivo) {
        try {
            ClassLoader classLoader = DatosPrecargados.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(archivo);
            return Base64.getDecoder().decode(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la foto");
        }
    }

    private static Clase crear(Modos modo, String nombre, String descripcion, UsuarioProfesor profesor, Nivel nivel, Clase.MetodoClase metodo, Materia materia) {
        Clase clase = new Clase();
        clase.setCupo(modo.cupo());
        clase.setModo(modo.modoClase());
        clase.setFecha(LocalDateTime.now().plusDays(numeroRandomEntre(0, 2)));
        clase.setMetodo(metodo);
        clase.setPrecio(new BigDecimal(numeroRandomEntre(500L, 2500L)));
        clase.setNombre(nombre);
        clase.setDescripcion(descripcion);
        clase.setPuntuacion(numeroRandomEntre(0, 5));
        clase.setMateria(materia);
        clase.setNivel(nivel);
        clase.setProfesor(profesor);
        return clase;
    }

    private static Producto crear(Clase clase, DetalleClase.EstadoClase estado) {
        DetalleClase detalleClase = new DetalleClase();
        detalleClase.setClase(clase);
        detalleClase.setEstado(estado);
        detalleClase.setFecha(fechaDeClaseRandom());
        detalleClase.setUrl("/reunion/" + clase.getNombre().replace(StringUtils.SPACE, "") + "-" + numeroRandomEntre(100L, 999l));
        Producto producto = detalleClase;
        producto.setPrecioProducto(clase.getPrecio());
        producto.setNombreProducto(clase.getNombre());
        return producto;
    }

    private static java.util.Date fechaDeClaseRandom() {
        ZonedDateTime enUTC = LocalDateTime.now()
                .withNano(0)
                .withSecond(0)
                .withMinute(0)
                .withHour(numeroRandomEntre(19, 22))
                .atZone(ZoneOffset.UTC);
        return java.util.Date.from(enUTC.toInstant());
    }

    // ENUMERADOS

    public enum Modos {
        INDIVIDUAL {
            @Override
            Clase.ModoClase modoClase() {
                return Clase.ModoClase.INDIVIDUAL;
            }

            @Override
            int cupo() {
                return 1;
            }
        },
        GRUPAL {
            @Override
            Clase.ModoClase modoClase() {
                return Clase.ModoClase.GRUPAL;
            }

            @Override
            int cupo() {
                return numeroRandomEntre(5, 8);
            }
        };

        abstract Clase.ModoClase modoClase();
        abstract int cupo();
    }

    public enum Niveles {
        PRIMARIA(1L),
        SECUNDARIA(2L),
        UNIVERSITARIO(3L),
        TERCIARIO(4L);

        private Long id;
        Niveles(Long id) {
            this.id = id;
        }

        public Nivel instancia() {
            Nivel nivel = new Nivel();
            nivel.setId(this.id);
            return nivel;
        }
    }

    public enum Materias {
        ALGEBRA(2L),
        INGLES(11L),
        PROGRAMACION(16L);

        private Long id;
        Materias(Long id) {
            this.id = id;
        }
        public Materia instancia() {
            Materia materia = new Materia();
            materia.setId(this.id);
            return materia;
        }
    }

    public enum Profesores {
        SERGIO("Sergio", "Bonavento", "sergio.bonavento@gmail.com", "Sergio123!", "Doy clases de Programacion y Algebra en escuelas y universidades", "La Matanza"),
        ROMINA("Romina", "Pi", "romina@gmail.com", "Romina123!", "Profesora de Ingles", "Merlo");

        private Usuario usuario;
        private UsuarioProfesor usuarioProfesor;

        Profesores(String nombre, String apellido, String email, String password, String experiencia, String localidad) {
            this.usuario = DatosPrecargados.crear(nombre, apellido, email, password, Usuario.rolUsuario.particular);
            this.usuarioProfesor = DatosPrecargados.crear(experiencia, localidad, this.usuario);
        }

        public Usuario comoUsuario() {
            return this.usuario;
        }

        public UsuarioProfesor comoProfesor() {
            return this.usuarioProfesor;
        }
    }

    public enum Alumnos {
        FACUNDO("Facundo", "Piraino", "facundo.piraino@gmail.com", "Facu123!", "Computación, Jueguitos", Niveles.PRIMARIA);

        private Usuario usuario;
        private UsuarioAlumno usuarioAlumno;

        Alumnos(String nombre, String apellido, String email, String password, String intereses, Niveles nivel) {
            this.usuario = DatosPrecargados.crear(nombre, apellido, email, password, Usuario.rolUsuario.alumno);
            this.usuarioAlumno = DatosPrecargados.crear(intereses, nivel, this.usuario);
        }

        public Usuario comoUsuario() {
            return this.usuario;
        }

        public UsuarioAlumno comoAlumno() {
            return this.usuarioAlumno;
        }
    }

    public enum Administradores {
        ADMIN1("Lucas", "Di Nubila", "estudiaencasa.admin@gmail.com", "Lucas123!");

        private Usuario usuario;

        Administradores(String nombre, String apellido, String email, String password) {
            this.usuario = DatosPrecargados.crear(nombre, apellido, email, password, Usuario.rolUsuario.administrador);
        }

        public Usuario comoUsuario() {
            return this.usuario;
        }
    }

    public enum FotosDePerfil {
        FOTO_SERGIO("templates/sergio.txt", "image/jpg", 68895d, Profesores.SERGIO.usuario),
        FOTO_FACU("templates/facu.txt", "image/png", 64520d, Alumnos.FACUNDO.usuario),
        FOTO_ROMINA("templates/romina.txt", "image/png", 33070d, Profesores.ROMINA.usuario);

        private FotoPerfil fotoPerfil;
        FotosDePerfil(String archivo, String extension, double tamanio, Usuario usuario) {
            this.fotoPerfil = DatosPrecargados.crear(archivo, extension, tamanio, usuario);
        }

        public FotoPerfil foto() {
            return this.fotoPerfil;
        }
    }

    public enum Clases {
        CLASE_PROGRA_SERGIO(Modos.INDIVIDUAL, "Clase de prgramacion basica", "Aprendé como hacer un for", Profesores.SERGIO, Niveles.UNIVERSITARIO, Clase.MetodoClase.ONLINE, Materias.PROGRAMACION, DetalleClase.EstadoClase.DISPONIBLE),
        CLASE_ALGEBRA_SERGIO(Modos.GRUPAL, "Algebra I", "Algebra...", Profesores.SERGIO, Niveles.UNIVERSITARIO, Clase.MetodoClase.ONLINE, Materias.ALGEBRA, DetalleClase.EstadoClase.DISPONIBLE),
        CLASE_INGLES_ROMINA(Modos.INDIVIDUAL, "Ingles", "Clases a medida según tus necesidades", Profesores.ROMINA, Niveles.UNIVERSITARIO, Clase.MetodoClase.ONLINE, Materias.INGLES, DetalleClase.EstadoClase.DISPONIBLE);

        private Clase clase;
        private Producto detalle;

        Clases(Modos modo, String nombre, String descripcion, Profesores persona, Niveles niveles, Clase.MetodoClase metodo, Materias materias, DetalleClase.EstadoClase estado) {
            this.clase = DatosPrecargados.crear(modo, nombre, descripcion, persona.comoProfesor(), niveles.instancia(), metodo, materias.instancia());
            this.detalle = DatosPrecargados.crear(this.clase, estado);
        }

        public Clase instancia() {
            return this.clase;
        }

        public Producto detalle() {
            return this.detalle;
        }
    }
}
