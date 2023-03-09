package ar.edu.unlam.proyecto.integracion.repositorios;

import ar.edu.unlam.proyecto.entidades.Compra;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeCompras;
import org.assertj.db.type.Changes;
import org.assertj.db.type.DateTimeValue;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.TimeValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;

import java.time.LocalDateTime;

import static org.assertj.db.api.Assertions.assertThat;

public class RepositorioDeComprasIntegrationTest extends TestDeRepositorio {

    @Autowired
    private RepositorioDeCompras repositorioDeCompras;

    @Test
    @Rollback(false)
    @DirtiesContext
    public void deberiaPersistirUnaCompraConTimestampEnUtc() {

        Changes cambios = new Changes(this.dataSource).setStartPointNow();

        LocalDateTime horaLocal = LocalDateTime.of(2021, 11, 14, 11, 8, 0, 0);
        Compra compra = new Compra();
        compra.setFecha(horaLocal);
        this.repositorioDeCompras.save(compra);
        TestTransaction.end();

        cambios.setEndPointNow();
        assertThat(cambios)
                .changeOfCreationOnTable("compra")
                .column("fecha").valueAtEndPoint().isEqualTo(DateTimeValue.of(DateValue.of(2021,11,14), TimeValue.of(11, 8, 0)));
    }
}
