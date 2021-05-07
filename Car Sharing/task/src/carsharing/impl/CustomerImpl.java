package carsharing.impl;

import carsharing.Main;
import carsharing.model.Customer;
import carsharing.util.DatabaseHandler;
import carsharing.dao.CustomerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerImpl implements CustomerDAO {

    private DatabaseHandler handler = new DatabaseHandler(Main.arguments);

//    public CustomerImpl(DatabaseHandler handler) {
//        this.handler = handler;
//    }

//    public void createDBCustomer() {
//        String sql = "CREATE TABLE IF NOT EXISTS CUSTOMER" +
//                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
//                " NAME VARCHAR(255) NOT NULL UNIQUE, " +
//                " RENTED_CAR_ID INTEGER , " +
//                "CONSTRAINT fk_car FOREIGN KEY(RENTED_CAR_ID) " +
//                "REFERENCES CAR(ID));";
//
//        handler.execQuery(sql);
//    }


    @Override
    public List<Customer> findAll() {
        List<Customer> list = new ArrayList();
        String sql = "SELECT * FROM CUSTOMER ORDER BY ID ASC";
        try (Connection conn = handler.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                while (rs.next()) {
                    list.add(new Customer(rs.getString("NAME"), rs.getLong("ID"), rs.getLong("RENTED_CAR_ID")));
                }
                rs.close();
            }
        } catch (Exception exception) {
//            exception.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Customer name) {
        String sql = "INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID) VALUES ('" + name.getName() + "', " + name.getRentedCar().getId() + ")";
        handler.execQuery(sql);
    }
}
