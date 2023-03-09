package ar.edu.unlam.proyecto.dtos;

import java.util.List;

import ar.edu.unlam.proyecto.entidades.Producto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClasesModelosRecientes {
    List<Producto> clases;
    List<Producto> modelos;
}
