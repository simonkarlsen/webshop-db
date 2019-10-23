package no.kristiania.webshop;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class WebShop {

    private final OrderDao orderDao;
    private PGSimpleDataSource dataSource;
    private BufferedReader input  = new BufferedReader(new InputStreamReader(System.in));
    private ProductDao productDao;


    public WebShop() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("webshop.properties"));

        dataSource = new PGSimpleDataSource();

        dataSource.setUrl("jdbc:postgresql://localhost:5432/mywebshopdb");
        dataSource.setUser("mywebshop");
        dataSource.setPassword(properties.getProperty("dataSource.password"));

        Flyway.configure().dataSource(dataSource).load().migrate();

        productDao = new ProductDao(dataSource);
        orderDao = new OrderDao(dataSource);

    }

    public static void main(String[] args) throws SQLException, IOException {
        new WebShop().run();
    }

    private void run() throws IOException, SQLException {
        System.out.println("Choose action: create [product]|create [order]");
        String action = input.readLine();

        switch (action.toLowerCase()) {
            case "product":
                insertProduct();
                break;
            case "order":
                insertOrder();
                break;
        }

        System.out.println("Current products " + productDao.listAll());
    }

    private void insertOrder() throws IOException, SQLException {
        System.out.println("Please type the name of a new order");
        Order order = new Order();
        order.setName(input.readLine());
        orderDao.insert(order);
    }

    private void insertProduct() throws IOException, SQLException {
        Product product = new Product();
        System.out.println("Please type the name of a new product: ");
        product.setName(input.readLine());
        System.out.println("Please type the price: ");
        product.setPrice(Double.valueOf(input.readLine()));
        productDao.insert(product);

    }


}
