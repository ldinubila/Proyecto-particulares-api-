package ar.edu.unlam.proyecto.dtos;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EstadisticasModeloClaseDto {
    List<Long> modelos;
    List<Long> clases;
    BigDecimal totalClases= new BigDecimal(0);
    BigDecimal totalModelos= new BigDecimal(0);
}
