package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.entidades.Nivel;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeNiveles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDeNiveles {

    private RepositorioDeNiveles repositorioDeNiveles;

    @Autowired
    public ServicioDeNiveles (RepositorioDeNiveles repositorioDeNiveles){
        this.repositorioDeNiveles = repositorioDeNiveles;
    }

    public List<Nivel> obtenerTodos(){
        return this.repositorioDeNiveles.findAll();
    }
}
