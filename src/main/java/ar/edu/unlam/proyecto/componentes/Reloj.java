package ar.edu.unlam.proyecto.componentes;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;

@Component
public class Reloj {
    public ZonedDateTime horaLocalConTimezone() {
        return ZonedDateTime.now();
    }

    public Instant instante() {
        return  Instant.now();
    }
}
