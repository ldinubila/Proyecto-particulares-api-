package ar.edu.unlam.proyecto.servicios;

import ar.edu.unlam.proyecto.componentes.Reloj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class ServicioDeReloj {

    private Reloj reloj;
    @Autowired
    public ServicioDeReloj(Reloj reloj) {
        this.reloj = reloj;
    }

    public LocalDateTime ahoraEnUtc() {
        ZonedDateTime fechaLocal = this.reloj.horaLocalConTimezone();
        ZonedDateTime fechaUtc = fechaLocal.withZoneSameInstant(ZoneOffset.UTC);
        return fechaUtc.toLocalDateTime();
    }

    public LocalDateTime primerDiaDelMes(int valorDelMes, int anio) {
        Month mes = Month.of(valorDelMes);
        ZonedDateTime fechaInicioLocal = ZonedDateTime.of(anio, mes.getValue(), 1, 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime fechaUtc = fechaInicioLocal.withZoneSameInstant(ZoneOffset.UTC);
        return fechaUtc.toLocalDateTime();
    }

    public LocalDateTime ultimoDiaDelMes(int valorDelMes, int anio) {
        Month mes = Month.of(valorDelMes);
        ZonedDateTime fechafinLocal = ZonedDateTime.of(anio, mes.getValue(), mes.maxLength(), 23, 59, 59, 999999999, ZoneId.systemDefault());
        ZonedDateTime fechaUtc = fechafinLocal.withZoneSameInstant(ZoneOffset.UTC);
        return fechaUtc.toLocalDateTime();
    }

    public LocalDateTime deUtcHacia(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.atZone(ZoneOffset.UTC)
                .withZoneSameInstant(zoneId)
                .toLocalDateTime();
    }
}
