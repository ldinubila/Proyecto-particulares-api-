package ar.edu.unlam.proyecto.integracion.repositorios;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:integration-test.properties")
public abstract class TestDeRepositorio {

    @Autowired
    protected DataSource dataSource;
}
