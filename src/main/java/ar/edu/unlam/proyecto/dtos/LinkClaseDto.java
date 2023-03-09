package ar.edu.unlam.proyecto.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ar.edu.unlam.proyecto.convertidores.ImagenBase64Deserializer;
import ar.edu.unlam.proyecto.entidades.Clase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinkClaseDto {
    private String link;
}
