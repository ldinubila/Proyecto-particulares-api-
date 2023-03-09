package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.entidades.*;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeCompras;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeDetalleClase;
import ar.edu.unlam.proyecto.repositorios.RepositorioDePagoParticular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class ServicioDeCompras {

    private RepositorioDeCompras repositorioDeCompras;
    private RepositorioDeDetalleClase repositorioDeDetalleClase;
    private RepositorioDePagoParticular repositorioDePagoParticular;
    private ServicioDeReloj servicioDeReloj;

    @Autowired
    public ServicioDeCompras(RepositorioDeCompras repositorioDeCompras, 
    		RepositorioDeDetalleClase repositorioDeDetalleClase,
    		ServicioDeReloj servicioDeReloj,
    		RepositorioDePagoParticular repositorioDePagoParticular) {
        this.repositorioDeCompras = repositorioDeCompras;
        this.repositorioDeDetalleClase = repositorioDeDetalleClase;
        this.servicioDeReloj = servicioDeReloj;
        this.repositorioDePagoParticular=repositorioDePagoParticular;
    }

    @Transactional
    public Compra crearProcesoDeCompra(ProcesoDeCompra procesoDeCompra) {
        return this.repositorioDeCompras.save(crearCompra(procesoDeCompra));
    }

    private Compra crearCompra(ProcesoDeCompra procesoDeCompra) {
        Compra compra = new Compra();
        compra.setProducto(procesoDeCompra.getProducto());
        compra.setMonto(procesoDeCompra.getMonto());
        compra.setNombre(procesoDeCompra.getNombre());

        Usuario usuario = new Usuario();
        usuario.setId(procesoDeCompra.getIdUsurio());
        compra.setUsuario(usuario);

        compra.setEstado(Compra.EstadoCompra.EN_PROCESO);
        compra.setFecha(this.servicioDeReloj.ahoraEnUtc());
        compra.setIdentificadorExterno(procesoDeCompra.getIdentificadorExterno());

        return compra;
    }

    @Transactional
    public void finalizarCompra(OfertaDeResolucion ofertaDeResolucion,BigDecimal monto, Long tipo, Long fecha) {
	
         ofertaDeResolucion.setEstado(OfertaDeResolucion.EstadoOferta.ACEPTADA);
   	     Optional<DetalleClase> detalle = this.repositorioDeDetalleClase.findById(fecha);
         if(tipo == 2 && detalle.isPresent()) {
	 	    detalle.get().setEstado(DetalleClase.EstadoClase.NODISPONIBLE);
	    	  
         }
         BigDecimal porcentaje= new BigDecimal(90);
		 BigDecimal divisor= new BigDecimal(100);
		 BigDecimal multiplicacion=monto.multiply(porcentaje);
		 int scale = 2;
		 int roundingMode = 4; 
		 BigDecimal resultado=multiplicacion.divide(divisor, scale, roundingMode);
		 PagoParticular pagoParticular=new PagoParticular();
		 pagoParticular.setUsuario(ofertaDeResolucion.getUsuario());
		 pagoParticular.setFecha(this.servicioDeReloj.ahoraEnUtc());
		 pagoParticular.setProducto(ofertaDeResolucion);
		 pagoParticular.setMonto(resultado);
		 this.repositorioDePagoParticular.save(pagoParticular);
    }

    @Transactional
    public void finalizarCompra(DetalleClase detalleClase,BigDecimal monto, Long tipo, Long fecha) {
    	 Optional<DetalleClase> detalle = this.repositorioDeDetalleClase.findById(detalleClase.getId());
	      if(detalle.isPresent()) {
	    	  if( detalle.get().getEstado().equals(DetalleClase.EstadoClase.DISPONIBLE) && detalle.get().getCupoRestante() >= 1) {
	 	    		 detalle.get().setCupoRestante(detalle.get().getCupoRestante()-1);
	 	    		 if((detalle.get().getCupoRestante()) == 0) {
		 	    		 detalle.get().setEstado(DetalleClase.EstadoClase.NODISPONIBLE);
		 	    		 BigDecimal porcentaje= new BigDecimal(90);
		 	    		 BigDecimal divisor= new BigDecimal(100);
		 	    		 BigDecimal multiplicacion=monto.multiply(porcentaje);
		 	    		 int scale = 2;
		 	    		 int roundingMode = 4; 
		 	    		 BigDecimal resultado=multiplicacion.divide(divisor, scale, roundingMode);
		 	    		 PagoParticular pagoParticular=new PagoParticular();
		 	    		 pagoParticular.setUsuario(detalle.get().getClase().getProfesor().getUsuario());
		 	    		 pagoParticular.setFecha(this.servicioDeReloj.ahoraEnUtc());
		 	    		 pagoParticular.setProducto(detalle.get());
		 	    		 pagoParticular.setMonto(resultado);
		 	    		 this.repositorioDePagoParticular.save(pagoParticular);
	 	    		 }
	 	    	 }
	      }
    }

    @Transactional
    public Boolean existeCompra(Long idUser, Long idProducto) {
    	List<Compra> compras=this.repositorioDeCompras.existeCompraUserProducto(idUser,idProducto);
    	if(compras.size() > 0) {
    		return true;
    	}else {
    		return false;
    	}
    }


}
