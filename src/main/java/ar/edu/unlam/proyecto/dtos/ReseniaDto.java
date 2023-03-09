package ar.edu.unlam.proyecto.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReseniaDto {
	private int puntaje;
	private String comentario;
	private Long id_usuario;
	private Long id_producto;
}

