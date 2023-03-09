package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.entidades.Materia;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeMaterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDeMaterias {

    private RepositorioDeMaterias repositorioDeMaterias;

    @Autowired
    public ServicioDeMaterias(RepositorioDeMaterias repositorioDeMaterias){
        this.repositorioDeMaterias = repositorioDeMaterias;
    }

    public List<Materia> obtenerTodas(){
        return this.repositorioDeMaterias.findAll();
    }
}
