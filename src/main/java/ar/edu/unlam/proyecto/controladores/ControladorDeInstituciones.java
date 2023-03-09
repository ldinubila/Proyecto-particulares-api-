package ar.edu.unlam.proyecto.controladores;


import ar.edu.unlam.proyecto.entidades.Institucion;
import ar.edu.unlam.proyecto.servicios.ServicioDeInstituciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1/instituciones")
public class ControladorDeInstituciones {

    private ServicioDeInstituciones servicioDeInstituciones;

    @Autowired
    public ControladorDeInstituciones(ServicioDeInstituciones servicioDeInstituciones) {
        this.servicioDeInstituciones = servicioDeInstituciones;
    }

    @GetMapping
    public ResponseEntity<List<Institucion>> obtenerTodos(){
        List<Institucion> institucion = this.servicioDeInstituciones.obtenerTodas();
        return ResponseEntity.ok(institucion);
    }


}
