package ar.edu.unlam.proyecto.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDeCompraDto {

    private Long idUsuario;
    private List<Long> ids;
    private Long tipo;
    private Long fecha;
}
