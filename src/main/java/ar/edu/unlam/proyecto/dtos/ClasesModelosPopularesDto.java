package ar.edu.unlam.proyecto.dtos;


import ar.edu.unlam.proyecto.entidades.Clase;
import ar.edu.unlam.proyecto.entidades.Modelo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClasesModelosPopularesDto {
    private Clase clases;
    private Modelo modelos;
        private int cant;
}
