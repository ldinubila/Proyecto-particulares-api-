package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.entidades.ArchivoDeResolucion;
import ar.edu.unlam.proyecto.entidades.Resolucion;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeArchivosDeResolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDeResolucion {

    private final RepositorioDeArchivosDeResolucion repositorioDeArchivos;

    @Autowired
    public ServicioDeResolucion(RepositorioDeArchivosDeResolucion repositorioDeArchivos) {
        this.repositorioDeArchivos = repositorioDeArchivos;
    }

    public List<ArchivoDeResolucion> obtenerArchivos(Resolucion resolucion) {
        return this.repositorioDeArchivos.findByResolucion(resolucion);
    }
}
