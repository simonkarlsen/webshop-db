package no.kristiania.webshop;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDaoTest {

    private JdbcDataSource dataSource = TestDatabase.testDataSource();


    @Test
    void shouldListInsertedProduct() throws SQLException {

        ProductDao dao = new ProductDao(dataSource);
        Product product = sampleProduct();
        dao.insert(product);
        assertThat(dao.listAll()).contains(product);

    }

    @Test
    void shouldSaveAllProductFields() throws SQLException {
        ProductDao dao = new ProductDao(dataSource);
        Product product = sampleProduct();
        assertThat(product).hasNoNullFieldsOrProperties();
        long id = dao.insert(product);
        assertThat(dao.retrieve(id))
                .isEqualToComparingFieldByField(product);
    }

    private Product sampleProduct() {
        Product product = new Product();
        product.setName(sampleProductName());
        product.setPrice(randomPrice()); 
        return product;
    }

    private Double randomPrice() {
        return new Random().nextInt(10) + 10.90;
    }

    private String sampleProductName() {
        String[] alternatives = {
                "apples", "bananas", "coconuts", "dates", "eeeeh"
        };
        return alternatives[new Random().nextInt(alternatives.length)];
    }
}
