package ar.edu.unlam.proyecto.controladores;

import ar.edu.unlam.proyecto.entidades.Materia;
import ar.edu.unlam.proyecto.servicios.ServicioDeMaterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("v1/materias")

public class ControladorDeMaterias {

    private ServicioDeMaterias servicioDeMaterias;
    @Autowired
    public ControladorDeMaterias(ServicioDeMaterias servicioDeMaterias){
        this.servicioDeMaterias = servicioDeMaterias;
    }
    @GetMapping
    public ResponseEntity<List<Materia>> obtenerTodas() {
        List<Materia> materias = this.servicioDeMaterias.obtenerTodas();
        return ResponseEntity.ok(materias);
    }
}

