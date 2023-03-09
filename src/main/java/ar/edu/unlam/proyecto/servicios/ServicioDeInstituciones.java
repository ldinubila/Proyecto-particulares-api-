package ar.edu.unlam.proyecto.servicios;


import ar.edu.unlam.proyecto.entidades.Institucion;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeInstituciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDeInstituciones {

    private RepositorioDeInstituciones repositorioDeInstituciones;

    @Autowired
    public ServicioDeInstituciones(RepositorioDeInstituciones repositorioDeInstituciones) {
        this.repositorioDeInstituciones = repositorioDeInstituciones;
    }

    public List<Institucion> obtenerTodas(){
        return this.repositorioDeInstituciones.findAll();
    }

}
