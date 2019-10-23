package no.kristiania.webshop;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {

    @Test
    void shouldListInsertedProduct() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:myTestDatabase;DB_CLOSE_DELAY=-1;"); //ADD: DB_CLOSE_DELAY=-1 to keep the DB open

        Flyway.configure().dataSource(dataSource).load().migrate();

        ProductDao dao = new ProductDao(dataSource);
        String product = sampleProduct();
        dao.insert(product);
        assertThat(dao.listAll()).contains(product);

    }

    private String sampleProduct() {
        String[] alternatives = {
                "apples", "bananas", "coconuts", "dates", "eeeeh"
        };
        return alternatives[new Random().nextInt(alternatives.length)];
    }
}
