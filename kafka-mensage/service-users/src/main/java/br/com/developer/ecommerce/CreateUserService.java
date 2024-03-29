package br.com.developer.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class CreateUserService {

    private final Connection connection;

    CreateUserService() throws SQLException {
        String url = "jdbc:sqlite:users_database.db";
        connection = DriverManager.getConnection(url);
        try {
            connection.createStatement().execute("create table Users (" +
                "uuid varchar(200) primary key," +
                "email varchar(200))");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {

        var createUserService = new CreateUserService();
        try (var service = new KafkaService<>(CreateUserService.class.getSimpleName(),
            "ECOMMERCE_NEW_ORDER",
            createUserService::parse,
            Order.class,
            Map.of())) {
            service.run();
        }
    }

    private void parse(final ConsumerRecord<String, Message<Order>> record) throws SQLException {
        System.out.println("-------------------------------------------");
        System.out.println("Processing new order, checking for new user");
        System.out.println(record.value());

        var message = record.value();
        var order = message.getPayload();
        if (isNewUser(order.getEmail())) {
            insertNewUser(order.getEmail());
        }
    }

    private void insertNewUser(final String email) throws SQLException {
        var insert = connection.prepareStatement("insert into Users (uuid, email) " +
            "values (?,?)");
        insert.setString(1, UUID.randomUUID().toString());
        insert.setString(2, "email");
        insert.execute();
        System.out.println("Usuário uuid e " + email + " adicionado");
    }

    private boolean isNewUser(final String email) throws SQLException {
        var exists = connection.prepareStatement("select uuid from Users " +
            "where email = ?");
        exists.setString(1, email);
        var results = exists.executeQuery();
        return !results.next();
    }

}
