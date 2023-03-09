package ar.edu.unlam.proyecto.controladores;

import ar.edu.unlam.proyecto.entidades.TipoDeDemora;
import ar.edu.unlam.proyecto.servicios.ServicioTiposDeDemoras;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("v1/demoras")
public class ControladorTiposDeDemora {

    private ServicioTiposDeDemoras servicioTiposDeDemoras;
    @Autowired
    public ControladorTiposDeDemora(ServicioTiposDeDemoras servicioTiposDeDemoras) {
        this.servicioTiposDeDemoras = servicioTiposDeDemoras;
    }

    @GetMapping
    public ResponseEntity<List<TipoDeDemora>> obtenerTodas() {
        return ResponseEntity.ok(this.servicioTiposDeDemoras.obtenerTodas());
    }
}
