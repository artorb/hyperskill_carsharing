package carsharing.impl;


import carsharing.Main;
import carsharing.model.Car;
import carsharing.util.DatabaseHandler;
import carsharing.dao.CarDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarImpl implements CarDAO {

    private DatabaseHandler handler = new DatabaseHandler(Main.arguments);

//    public CarImpl(DatabaseHandler handler) {
//        this.handler = handler;
//    }

//    public void createDBCar() {
//        String sql = "CREATE TABLE IF NOT EXISTS CAR" +
//                "(ID INT PRIMARY KEY AUTO_INCREMENT," +
//                "NAME VARCHAR UNIQUE NOT NULL," +
//                "COMPANY_ID INT NOT NULL," +
//                "CONSTRAINT fk_company FOREIGN KEY(COMPANY_ID) " +
//                "REFERENCES COMPANY(ID));";
//
//        handler.execQuery(sql);
//    }

    @Override
    public List<Car> findAll() {
        List<Car> list = new ArrayList();
        String sql = "SELECT ID, NAME, COMPANY_ID FROM CAR ORDER BY ID ASC";
        try (Connection conn = handler.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                while (rs.next()) {
                    list.add(new Car(rs.getString("NAME"), rs.getLong("ID"), rs.getLong("COMPANY_ID")));
                }
                rs.close();
            }
        } catch (Exception exception) {
//            exception.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Car name) {
        String sql = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('" + name.getName() + "', " + name.getCompanyId() + ")";
        handler.execQuery(sql);
    }

    @Override
    public List<Car> findByCompanyId(Long companyId) {
        return this.findAll().stream().filter(cmp -> cmp.getCompanyId().equals(companyId)).collect(Collectors.toList());
    }
}
