package ar.edu.unlam.proyecto.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class OfertaDeResolucionDto {

    private Long idModelo;
    private Long idUsuario;
    private BigDecimal costo;
    @JsonProperty("tipoDeDemora")
    private Long idDemora;
    @JsonProperty("tipoResolucion")
    private Long idTipoResolucion;
    private List<Date> disponibilidad;

}

