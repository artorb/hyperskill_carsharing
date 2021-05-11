package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("ALL")
public class DatabaseHandler {
    private static String JDBC_DRIVER = "org.h2.Driver";
    private static String DB_URL = "jdbc:h2:/home/ar/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/";
    private Connection connection = null;

    public void createDBCar() {
        String sql = "CREATE TABLE IF NOT EXISTS CAR" +
                "(ID INT PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR UNIQUE NOT NULL," +
                "COMPANY_ID INT NOT NULL," +
                "CONSTRAINT fk_company FOREIGN KEY(COMPANY_ID) " +
                "REFERENCES COMPANY(ID));";

        execQuery(sql);
    }

    public void createDBCustomer() {
        String sql = "CREATE TABLE IF NOT EXISTS CUSTOMER" +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                " NAME VARCHAR(255) NOT NULL UNIQUE, " +
                " RENTED_CAR_ID INTEGER , " +
                "CONSTRAINT fk_car FOREIGN KEY(RENTED_CAR_ID) " +
                "REFERENCES CAR(ID));";

        execQuery(sql);
    }

    public void createDBCompany() {
        String sql = "CREATE TABLE IF NOT EXISTS COMPANY" +
                "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
                " NAME VARCHAR(255) NOT NULL UNIQUE, " +
                " PRIMARY KEY(ID));";

        execQuery(sql);
    }

    public Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
        } catch (Exception e) {
            e.getMessage();
        }
        return connection;
    }

    public DatabaseHandler() {
    }

    public DatabaseHandler(String[] args) {
        String dbName = "carsharing";
        if (args.length >= 2 && args[0].equals("-databaseFileName")) {
            dbName = args[1];
        }
        DB_URL += dbName;
        createDBCompany();
        createDBCar();
        createDBCustomer();
    }

    public boolean execQuery(String sql) {
        try (Connection conn = getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
