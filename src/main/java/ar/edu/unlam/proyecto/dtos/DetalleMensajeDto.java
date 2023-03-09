package ar.edu.unlam.proyecto.dtos;

import java.util.ArrayList;
import java.util.List;


import ar.edu.unlam.proyecto.entidades.Mensaje;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetalleMensajeDto {
    private Mensaje mensaje;
    private List<Mensaje> respuestas=new ArrayList<>();
}
