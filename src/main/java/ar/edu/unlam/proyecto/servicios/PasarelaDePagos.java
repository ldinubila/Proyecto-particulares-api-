package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.dtos.ProcesoDeCompraDto;
import ar.edu.unlam.proyecto.entidades.Producto;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface PasarelaDePagos {

	ProcesoDeCompraDto iniciarCompra(List<Long> idProducto, Long idComprador,Long tipo,Long fecha);
}
