package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.entidades.Carrera;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeCarreras;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDeCarreras {

    private RepositorioDeCarreras repositorioDeCarreras;

    public ServicioDeCarreras(RepositorioDeCarreras repositorioDeCarreras) {
        this.repositorioDeCarreras = repositorioDeCarreras;
    }

    public List<Carrera> obtenerTodas(){
        return this.repositorioDeCarreras.findAll();
    }


}
