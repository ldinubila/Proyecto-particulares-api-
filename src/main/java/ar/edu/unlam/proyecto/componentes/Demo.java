package ar.edu.unlam.proyecto.componentes;

import ar.edu.unlam.proyecto.repositorios.*;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.stream.Stream;

@Component
public class Demo {

    private enum Ambiente {
        DESARROLLO, TESTING, PRODUCCION;
    }

    private final Logger logger = LoggerFactory.getLogger(Demo.class.getSimpleName());

    private Ambiente ambiente;
    private boolean recrearAmbiente;
    private Flyway flyway;
    private RepositorioDeUsuarios repositorioDeUsuarios;
    private RepositorioDeProfesor repositorioDeProfesor;
    private RepositorioDeAlumno repositorioDeAlumno;
    private RepositorioDeProductos repositorioDeProductos;
    private RepositorioDeClases repositorioDeClases;
    private RepositorioDeDetalleClase repositorioDeDetalleClase;
    private RepositoriodeFotoPerfil repositoriodeFotoPerfil;

    @Autowired
    public Demo(@Value("${particulares.ambiente}") Ambiente ambiente,
                @Value("${particulares.ambiente.recrear}") boolean recrearAmbiente,
                Flyway flyway,
                RepositorioDeUsuarios repositorioDeUsuarios,
                RepositorioDeProfesor repositorioDeProfesor,
                RepositorioDeAlumno repositorioDeAlumno,
                RepositorioDeProductos repositorioDeProductos,
                RepositorioDeClases repositorioDeClases,
                RepositorioDeDetalleClase repositorioDeDetalleClase,
                RepositoriodeFotoPerfil repositoriodeFotoPerfil) {
        this.ambiente = ambiente;
        this.recrearAmbiente = recrearAmbiente;
        this.flyway = flyway;
        this.repositorioDeUsuarios = repositorioDeUsuarios;
        this.repositorioDeProfesor = repositorioDeProfesor;
        this.repositorioDeAlumno = repositorioDeAlumno;
        this.repositorioDeProductos = repositorioDeProductos;
        this.repositorioDeClases = repositorioDeClases;
        this.repositorioDeDetalleClase = repositorioDeDetalleClase;
        this.repositoriodeFotoPerfil = repositoriodeFotoPerfil;
    }

    @EventListener
    public void crearInformarcionDePrueba(ApplicationReadyEvent event) {
        insertarDatosParaDemo();
    }

    private void limpiarBaseDeDatos() {
        flyway.clean();
        flyway.migrate();
    }

    private void insertarDatosParaDemo() {
        logger.info("Preparando demo - insertando usuarios");

        this.crearProfesores();
        this.crearAlumnos();
        this.crearAdministradores();
        this.insertarFotosDePerfil();

        logger.info("Preparando demo - insertando clases");
        this.insertarClases();
    }

    private void insertarFotosDePerfil() {
        logger.info("Preparando demo - insertando fotos");
        Stream.of(DatosPrecargados.FotosDePerfil.values()).forEach((fotoPerfil -> {
            this.repositoriodeFotoPerfil.save(fotoPerfil.foto());
        }));
    }

    private void crearProfesores() {
        logger.info("Preparando demo - insertando profesores");
        Stream.of(DatosPrecargados.Profesores.values()).forEach((persona -> {
            this.repositorioDeUsuarios.save(persona.comoUsuario());
            this.repositorioDeProfesor.save(persona.comoProfesor());
        }));
    }

    private void crearAlumnos() {
        logger.info("Preparando demo - insertando alumnos");
        Stream.of(DatosPrecargados.Alumnos.values()).forEach((persona -> {
            this.repositorioDeUsuarios.save(persona.comoUsuario());
            this.repositorioDeAlumno.save(persona.comoAlumno());
        }));
    }

    private void crearAdministradores() {
        logger.info("Preparando demo - insertando administradores");
        Stream.of(DatosPrecargados.Administradores.values()).forEach((persona -> {
            this.repositorioDeUsuarios.save(persona.comoUsuario());
        }));
    }

    private void insertarClases() {
        Stream.of(DatosPrecargados.Clases.values()).forEach((clase -> {
            this.repositorioDeClases.save(clase.instancia());
            this.repositorioDeProductos.save(clase.detalle());
        }));
    }
}
