package no.kristiania.webshop;


import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;



public class OrderDaoTest {

    @Test
    void shouldFindSavedOrders() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:myTestDatabase;DB_CLOSE_DELAY=-1"); //ADD: DB_CLOSE_DELAY=-1 to keep the DB open

//        dataSource.getConnection().createStatement().executeUpdate(
//                "create table ORDERS (id serial primary key, name varchar (1000) not null)"
//        ); --- REPLACED BY flyway

        Flyway.configure().dataSource(dataSource).load().migrate();

        Order order = new Order();
        order.setName("Test");
        OrderDao dao = new OrderDao(dataSource);

        dao.insert(order);
        assertThat(dao.listAll()).contains(order);

    }
}
