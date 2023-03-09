package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.entidades.TipoDeResolucion;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeTiposDeResolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDeTipoDeResolucion {

    private RepositorioDeTiposDeResolucion repositorioDeTiposDeResolucion;

    @Autowired
    public ServicioDeTipoDeResolucion(RepositorioDeTiposDeResolucion repositorioDeTiposDeResolucion) {
        this.repositorioDeTiposDeResolucion = repositorioDeTiposDeResolucion;
    }

    public List<TipoDeResolucion> obtenerTodos() {
        return this.repositorioDeTiposDeResolucion.findAll();
    }
}
