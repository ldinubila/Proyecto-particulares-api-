package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.entidades.TipoDeDemora;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeDemoras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioTiposDeDemoras {

    private RepositorioDeDemoras repositorioDeDemoras;

    @Autowired
    public ServicioTiposDeDemoras(RepositorioDeDemoras repositorioDeDemoras) {
        this.repositorioDeDemoras = repositorioDeDemoras;
    }

    public List<TipoDeDemora> obtenerTodas() {
        return this.repositorioDeDemoras.findAll();
    }
}
