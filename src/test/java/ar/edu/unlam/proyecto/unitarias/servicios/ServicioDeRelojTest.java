package ar.edu.unlam.proyecto.unitarias.servicios;

import ar.edu.unlam.proyecto.componentes.Reloj;
import ar.edu.unlam.proyecto.servicios.ServicioDeReloj;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioDeRelojTest {

    @Test
    public void obtenerHoraLocalDeArgentinaEnUTC() {

        // 2021/11/01 a las 23:00:00.000
        ZonedDateTime horaEnArgentina = fechaEnArgentina(2021, 11, 1, 23, 23, 59, 0);

        Reloj reloj = mock(Reloj.class);
        when(reloj.horaLocalConTimezone()).thenReturn(horaEnArgentina);

        ServicioDeReloj servicioDeReloj = new ServicioDeReloj(reloj);

        LocalDateTime ahoraEnUtc = servicioDeReloj.ahoraEnUtc();
        assertThat(ahoraEnUtc).isEqualTo("2021-11-02T02:23:59");
    }

    private ZonedDateTime fechaEnArgentina(int anio, int mes, int dia, int horas, int minutos, int segundos, int milisegundos) {
        ZoneId zoneId = ZoneId.of("America/Argentina/Buenos_Aires");
        return ZonedDateTime.of(anio, mes, dia, horas, minutos, segundos, milisegundos, zoneId);
    }
}
