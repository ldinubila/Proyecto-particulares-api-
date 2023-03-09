package ar.edu.unlam.proyecto.servicios;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.unlam.proyecto.clientes.mercadopago.ClienteMercadoPago;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlam.proyecto.dtos.ClasesModelosPopularesDto;
import ar.edu.unlam.proyecto.dtos.ClasesModelosRecientes;
import ar.edu.unlam.proyecto.dtos.EstadisticasDto;
import ar.edu.unlam.proyecto.dtos.EstadisticasModeloClaseDto;
import ar.edu.unlam.proyecto.entidades.Clase;
import ar.edu.unlam.proyecto.entidades.Compra;
import ar.edu.unlam.proyecto.entidades.DetalleClase;
import ar.edu.unlam.proyecto.entidades.Modelo;
import ar.edu.unlam.proyecto.entidades.OfertaDeResolucion;
import ar.edu.unlam.proyecto.entidades.Producto;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeClases;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeCompras;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeDetalleClase;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeMensaje;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeModelos;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeOfertasDeResolucion;
import ar.edu.unlam.proyecto.repositorios.RepositorioDePago;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeProductos;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeUsuarios;
@Service
public class ServicioDeEstadisticas {

	private final Logger logger = LoggerFactory.getLogger(ClienteMercadoPago.class.getSimpleName());

	private RepositorioDeClases repositorioDeClases;
	private RepositorioDeUsuarios repositorioDeUsuarios;
	private RepositorioDeOfertasDeResolucion repositorioDeOfertaDeResolcuion;
	private RepositorioDeDetalleClase repositorioDeDetalleClase;
	private RepositorioDeMensaje repositorioDeMensaje;
	private RepositorioDeProductos repositorioDeProductos;
	private RepositorioDeCompras repositorioDeCompra;
	private RepositorioDeModelos repositorioDeModelos;
	private ServicioDeReloj servicioDeReloj;

	    @Autowired
	    public ServicioDeEstadisticas(RepositorioDeClases repositorioDeClases,
	    		                RepositorioDeMensaje repositorioDeMensaje,
	    		                RepositorioDePago repositorioDePago,
	                            RepositorioDeDetalleClase repositorioDeDetalleClase,
	                            PasarelaDePagos pasarelaDePagos,
	                            RepositorioDeUsuarios repositorioDeUsuarios,
	                            RepositorioDeProductos repositorioDeProductos,
	                            RepositorioDeCompras repositorioDeCompra,
	                            RepositorioDeModelos repositorioDeModelos,
	                            RepositorioDeOfertasDeResolucion repositorioDeOfertaDeResolcuion,
								ServicioDeReloj servicioDeReloj) {
	        this.repositorioDeClases = repositorioDeClases;
	        this.repositorioDeDetalleClase = repositorioDeDetalleClase;
	        this.repositorioDeMensaje=repositorioDeMensaje;
	        this.repositorioDeUsuarios=repositorioDeUsuarios;
	        this.repositorioDeProductos=repositorioDeProductos;
	        this.repositorioDeCompra=repositorioDeCompra;
            this.repositorioDeModelos=repositorioDeModelos;
            this.repositorioDeOfertaDeResolcuion=repositorioDeOfertaDeResolcuion;
			this.servicioDeReloj = servicioDeReloj;
	    }

		private List<Compra> obtenerCompreasDel(int mes, int anio) {
			LocalDateTime fechaInicio = this.servicioDeReloj.primerDiaDelMes(mes, anio);
			LocalDateTime fechaFin = this.servicioDeReloj.ultimoDiaDelMes(mes, anio);

			return this.repositorioDeCompra.obtenerComprasEntre(fechaInicio, fechaFin);
		}

		public List<Compra> porCompras(EstadisticasDto body) {

			return this.obtenerCompreasDel(body.getMes(), body.getAnio());
		}

		public List<Compra> porMateriasPorParticular(Long idPublicador, EstadisticasDto body) {

			return this.obtenerCompreasDel(body.getMes(), body.getAnio())
					.stream().filter(compra -> compra.fueRecibidaPor(idPublicador))
					.collect(Collectors.toList());
		}
		
		public EstadisticasModeloClaseDto porClasesModelos(EstadisticasDto body) {
			 EstadisticasModeloClaseDto dto=new EstadisticasModeloClaseDto();

			int anio = body.getAnio();
			LocalDateTime fechaInicio = this.servicioDeReloj.primerDiaDelMes(Month.JANUARY.getValue(), anio);
			LocalDateTime fechaFin = this.servicioDeReloj.ultimoDiaDelMes(Month.DECEMBER.getValue(), anio);

			 List<Compra> compras = this.repositorioDeCompra.obtenerComprasEntre(fechaInicio, fechaFin);
			 List<Long> modelos=new ArrayList();
			 List<Long> clases=new ArrayList();

			 if(body.getAnio()!=  0) {
				 //List<Compra> comprasFilter= this.repositorioDeCompra.findAll().stream().filter(c -> c.getFecha().getYear() == body.getAnio()).collect(Collectors.toList());
				 for(int i=0; i < 12 ; i++) {
					 int mes=i+1;
					   List<Compra> countCompra = compras.stream().filter(c -> {
						   int mesLocal = this.servicioDeReloj.deUtcHacia(c.getFecha(), ZoneId.of("America/Argentina/Buenos_Aires"))
											.getMonth().getValue();
						   return c.getProducto() instanceof DetalleClase && mesLocal == mes;
					   }).collect(Collectors.toList());

					   List<Compra> countModelos=compras.stream().filter(c -> {
						   int mesLocal = this.servicioDeReloj.deUtcHacia(c.getFecha(), ZoneId.of("America/Argentina/Buenos_Aires"))
								   .getMonth().getValue();
						   return c.getProducto() instanceof OfertaDeResolucion && mesLocal == mes;
					   }).collect(Collectors.toList());
					   clases.add((long) countCompra.size());
					   modelos.add((long) countModelos.size());

					   for(Compra c : countCompra) {
						   BigDecimal suma = dto.getTotalClases().add(c.getMonto());
                           dto.setTotalClases(suma);
					   }
					   for(Compra c : countModelos) {
						   BigDecimal suma = dto.getTotalModelos().add(c.getMonto());
                           dto.setTotalModelos(suma);
					   }
				   }
					
			     }
			 dto.setModelos(modelos);
			 dto.setClases(clases);
			 return dto;
			 }

		public List<Compra> porClases(EstadisticasDto body) {
			return this.obtenerCompreasDel(body.getMes(), body.getAnio())
					.stream()
					.filter(c -> c.getProducto() instanceof DetalleClase).collect(Collectors.toList());
		}

		public List<Compra> porModelo(EstadisticasDto body) {

			 return this.obtenerCompreasDel(body.getMes(), body.getAnio())
					 .stream()
					 .filter(c -> c.getProducto() instanceof OfertaDeResolucion).collect(Collectors.toList());
		}

		public List<ClasesModelosPopularesDto> clasesMasPupularesDelMes() {
			List<Compra> compras=this.repositorioDeCompra.findAll();
			List<Compra> comprasFilter= new ArrayList();

			List<ClasesModelosPopularesDto> clasesPopulares= new ArrayList();
		
            int mesActual=LocalDateTime.now().getMonth().getValue();
            comprasFilter= compras.stream().filter(c -> c.getProducto() instanceof DetalleClase  && c.getFecha().getMonth().getValue() == mesActual).collect(Collectors.toList());
            for(Compra c:comprasFilter) {
       		 Optional<DetalleClase> detalleClase=this.repositorioDeDetalleClase.findById(c.getProducto().getId());
        		
            	boolean existeClase =  clasesPopulares.stream().anyMatch(t -> t.getClases().getId().equals(detalleClase.get().getClase().getId()));
            	if(!existeClase) {
            		int cant=0;
                    List<DetalleClase> detalles= this.repositorioDeDetalleClase.findByClase(detalleClase.get().getClase()).get();
                    for(DetalleClase d:detalles) {
                    	boolean existeDetalle =  compras.stream().anyMatch(t -> t.getProducto().getId().equals(d.getId()));
                    	if(existeDetalle) {
                    		cant++;
                    	}
                    }
            		ClasesModelosPopularesDto nuevo= new ClasesModelosPopularesDto();
            		nuevo.setClases(detalleClase.get().getClase());
            		nuevo.setCant(cant);
            		clasesPopulares.add(nuevo);            	}
            }
			return clasesPopulares;
		}
		
		public List<ClasesModelosPopularesDto> modelosMasPupularesDelMes() {
			List<Compra> compras=this.repositorioDeCompra.findAll();
			List<Compra> comprasFilter= new ArrayList();

			List<ClasesModelosPopularesDto> modelosPopulares= new ArrayList();
		
            int mesActual=LocalDateTime.now().getMonth().getValue();
            comprasFilter= compras.stream().filter(c -> c.getProducto() instanceof OfertaDeResolucion  && c.getFecha().getMonth().getValue() == mesActual).collect(Collectors.toList());
            for(Compra c:comprasFilter) {
       		     Optional<OfertaDeResolucion> modelo=this.repositorioDeOfertaDeResolcuion.findById(c.getProducto().getId());

            	 boolean existeClase =  modelosPopulares.stream().anyMatch(t -> t.getModelos().getId().equals(modelo.get().getId()));
            	if(!existeClase) {
                    int cant= comprasFilter.stream().filter(e -> e.getProducto().equals(c.getProducto())).collect(Collectors.toList()).size();
            		ClasesModelosPopularesDto nuevo= new ClasesModelosPopularesDto();
            		nuevo.setModelos(modelo.get().getModelo());
            		nuevo.setCant(cant);
            		modelosPopulares.add(nuevo);            	}
            }
			return modelosPopulares;
		}
		
		public ClasesModelosRecientes agregadosRecientemente() {
			ClasesModelosRecientes dto= new ClasesModelosRecientes();
			List<Compra> compras=this.repositorioDeCompra.findAllRecientes();
			List<Producto> clases= new ArrayList();
			List<Producto> modelos= new ArrayList();
           
			for(Compra c : compras) {
                 if(c.getProducto() instanceof OfertaDeResolucion) {
                     modelos.add(c.getProducto());
                 }
                 if(c.getProducto() instanceof DetalleClase) {
                	 clases.add(c.getProducto());
                 }
			}
			dto.setClases(clases);
			dto.setModelos(modelos	);
		    return dto;
		}

}
