package ar.edu.unlam.proyecto.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class ProcesoDeCompraDto {

    private Long idCompra;
    private String idExterno;
    private String urlExterna;
    private String proveedor;
    public ProcesoDeCompraDto() {}
}
