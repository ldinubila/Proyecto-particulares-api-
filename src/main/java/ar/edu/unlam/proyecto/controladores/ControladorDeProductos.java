package ar.edu.unlam.proyecto.controladores;

import ar.edu.unlam.proyecto.dtos.PedidoDeCompraDto;
import ar.edu.unlam.proyecto.dtos.ProcesoDeCompraDto;
import ar.edu.unlam.proyecto.entidades.Compra;
import ar.edu.unlam.proyecto.servicios.PasarelaDePagos;
import ar.edu.unlam.proyecto.servicios.ServicioDeMail;
import excepciones.ExcepcionExisteCompra;
import excepciones.ExcepcionNoEsAlumno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("v1/productos")
public class ControladorDeProductos {

    private PasarelaDePagos pasarelaDePagos;
    private ServicioDeMail servicioDeMail;


    @Autowired
    public ControladorDeProductos(PasarelaDePagos pasarelaDePagos,ServicioDeMail servicioDeMail) {
        this.pasarelaDePagos = pasarelaDePagos;
        this.servicioDeMail = servicioDeMail;
    }

    @PostMapping("{id}/compras")
    public ResponseEntity iniciarProcesoDeCompra(@PathVariable("id") Long idProducto,
                                                 @RequestBody PedidoDeCompraDto pedidoDeCompraDto) {
    	try {
        	ProcesoDeCompraDto procesoDeCompraDto = this.pasarelaDePagos.iniciarCompra(pedidoDeCompraDto.getIds(), pedidoDeCompraDto.getIdUsuario(), pedidoDeCompraDto.getTipo(), pedidoDeCompraDto.getFecha());
        	servicioDeMail.enviarMail(pedidoDeCompraDto.getIdUsuario());
        	return ResponseEntity.created(null).body(procesoDeCompraDto);            

    	}catch (ExcepcionExisteCompra e) {
			return ResponseEntity.badRequest().body("La compra ya existe");
    	}catch (ExcepcionNoEsAlumno e) {
			return ResponseEntity.badRequest().body("Debe ser un usuario alumno para realizar esta operación");
    	}

    }
    
}
