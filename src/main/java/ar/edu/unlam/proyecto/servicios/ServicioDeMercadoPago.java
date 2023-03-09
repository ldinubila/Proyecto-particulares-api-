package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.clientes.mercadopago.ClienteMercadoPago;
import ar.edu.unlam.proyecto.clientes.mercadopago.PreferenciaMP;
import ar.edu.unlam.proyecto.clientes.mercadopago.ProductoMP;
import ar.edu.unlam.proyecto.dtos.ProcesoDeCompraDto;
import ar.edu.unlam.proyecto.entidades.Compra;
import ar.edu.unlam.proyecto.entidades.ProcesoDeCompra;
import ar.edu.unlam.proyecto.entidades.Producto;
import ar.edu.unlam.proyecto.entidades.Usuario;
import ar.edu.unlam.proyecto.entidades.UsuarioAlumno;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeAlumno;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeProductos;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeUsuarios;
import excepciones.ExcepcionExisteCompra;
import excepciones.ExcepcionNoEsAlumno;
import excepciones.FechaNoDisponibleExcepcion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class ServicioDeMercadoPago implements PasarelaDePagos {

    private static final String PROVEEDOR = "MercadoPago";

    private ClienteMercadoPago cliente;
    private RepositorioDeProductos repositorioDeProductos;
    private RepositorioDeUsuarios repositorioDeUsuario;
    private ServicioDeCompras servicioDeCompras;

    @Autowired
    public ServicioDeMercadoPago(ClienteMercadoPago clienteMercadoPago,
                                 RepositorioDeProductos repositorioDeProductos,
                                 ServicioDeCompras servicioDeCompras,RepositorioDeUsuarios repositorioDeUsuario) {
        this.cliente = clienteMercadoPago;
        this.repositorioDeProductos = repositorioDeProductos;
        this.servicioDeCompras = servicioDeCompras;
        this.repositorioDeUsuario=repositorioDeUsuario;
    }

    @Override
    @Transactional
    public ProcesoDeCompraDto iniciarCompra(List<Long> ids, Long idComprador,Long tipo,Long fecha) {
     	 ProcesoDeCompraDto procesoDeCompra=new ProcesoDeCompraDto();
     	 if(!this.servicioDeCompras.existeCompra(idComprador,ids.get(0))) {
     		Optional<Usuario> user=this.repositorioDeUsuario.findById(idComprador);

        	if(user.get().getRol().name().equals("alumno")) {
            //Habría que crear una excepción custom y retornar un NotFound(404) al cliente
        	for(Long id : ids) {
            Producto producto = this.repositorioDeProductos.findById(id)
                                    .orElseThrow(IllegalArgumentException::new);
         
            PreferenciaMP preferenciaMP = this.crearPreferencia(producto,ids.size());

            Compra compra = this.crearCompra(producto, preferenciaMP, idComprador);
            if(tipo==2 && fecha!=null) {
                Producto detalleClase = this.repositorioDeProductos.findById(fecha)
                        .orElseThrow(IllegalArgumentException::new);
                Compra compraDetalleClase = this.crearCompra(detalleClase, preferenciaMP, idComprador);
            }
        
            // Creo que esto deberia llamarse a partir de otro endpoint, pero para simplificar...
            producto.finalizarCompra(servicioDeCompras,producto.getPrecioProducto(),tipo,fecha);
             
            procesoDeCompra.setIdCompra(compra.getId());
            procesoDeCompra.setIdExterno( preferenciaMP.getId());
            procesoDeCompra.setUrlExterna( preferenciaMP.getCheckoutUrl());
            procesoDeCompra.setProveedor(PROVEEDOR);
            }	
        	}else {
      			throw new ExcepcionNoEsAlumno("Debe ser un alumno para realizar la operacion");
        	}
     	}else {
  			throw new ExcepcionExisteCompra("La compra ya existe");

     	}
        return  procesoDeCompra;
    }

    private PreferenciaMP crearPreferencia(Producto producto, int cant) {
        ProductoMP productoMP = ProductoMP.nuevo()
                .conCantidad(cant)
                .conNombre(producto.getNombreProducto())
                .conPrecioUnitario(producto.getPrecioProducto().floatValue());
              

        return this.cliente.guardarPreferencia(productoMP);
    }

    private Compra crearCompra(Producto producto, PreferenciaMP preferenciaMP, Long idComprador) {
        ProcesoDeCompra procesoDeCompra = ProcesoDeCompra.nuevo()
                .paraProducto(producto)
                .conIdentificadorExterno(preferenciaMP.getId())
                .conIdUsuario(idComprador);
        return this.servicioDeCompras.crearProcesoDeCompra(procesoDeCompra);
    }
}
