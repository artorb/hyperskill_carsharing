package carsharing.impl;

import carsharing.Main;
import carsharing.model.Company;
import carsharing.util.DatabaseHandler;
import carsharing.dao.CompanyDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyImpl implements CompanyDAO {

    private DatabaseHandler handler = new DatabaseHandler(Main.arguments);

//    public CompanyImpl(DatabaseHandler handler) {
//        this.handler = handler;
//    }

//    public void createDBCompany() {
//        String sql = "CREATE TABLE IF NOT EXISTS COMPANY" +
//                     "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
//                     " NAME VARCHAR(255) NOT NULL UNIQUE, " +
//                     " PRIMARY KEY(ID));";
//
//        handler.execQuery(sql);
//    }

    @Override
    public List<Company> findAll() {
        List<Company> list = new ArrayList();
        String sql = "SELECT ID, NAME FROM COMPANY ORDER BY ID ASC";
        try (Connection conn = handler.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                while (rs.next()) {
                    list.add(new Company(rs.getString("NAME"), rs.getLong("ID")));
                }
                rs.close();
            }
        } catch (Exception exception) {
//            exception.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Company company) {
        String sql = "INSERT INTO COMPANY (NAME) VALUES ('" + company.getName() + "');";
        handler.execQuery(sql);
    }
}
