package carsharing.dao;

import carsharing.DatabaseHandler;
import carsharing.models.Customer;
import carsharing.menus.RentCarMenu;
import carsharing.menus.StartMenu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CustomerDaoImpl implements CarsharingEntity<Customer> {
    private DatabaseHandler handler;
    private static CustomerDaoImpl customerRepository = new CustomerDaoImpl(new DatabaseHandler());


    private static Customer activeCustomer = null;

    public static Customer getActiveCustomer() {
        return Objects.requireNonNullElseGet(activeCustomer, () -> new Customer("NULL"));
    }

    public static void setActiveCustomer(Customer activeCustomer) {
        CustomerDaoImpl.activeCustomer = activeCustomer;
    }

    public static CustomerDaoImpl getCustomerRepository() {
        return customerRepository;
    }

    public CustomerDaoImpl(DatabaseHandler handler) {
        this.handler = handler;
    }

    @Override
    public Customer get(long id) {
        return getAll().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(new Customer("NULL"));
    }

    public void printCustomers() {
        List<Customer> customers = getAll();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!\n");
            StartMenu.displayStartMenu();
        } else {
            IntStream.range(0, getAll().size()).mapToObj(index -> String.format("%d. %s", index + 1, getAll().get(index))).forEach(System.out::println);
            System.out.println("0. Back\n");
            try (Scanner scan = new Scanner(System.in)) {
                int opt = scan.nextInt();
                if (opt > 0 && opt <= customers.size()) {
                    setActiveCustomer(getAll().get(opt - 1));
                    RentCarMenu.displayRentCarMenu();
                } else if (opt == 0) {
                    StartMenu.displayStartMenu();
                } else {
                    System.out.println("Incorrect option!");
                    StartMenu.displayStartMenu();
                }
            }
        }
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList();
        String sql = "SELECT * FROM CUSTOMER ORDER BY ID ASC";
        try (Connection conn = handler.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                while (rs.next()) {
                    list.add(new Customer(rs.getString("NAME"), rs.getLong("ID")));
                }
                rs.close();
            }
        } catch (Exception ignored) {
        }
        return list;
    }

    @Override
    public void save(Customer name) {
        String sql = "INSERT INTO CUSTOMER (NAME) VALUES ('" + name.getName() + "')";
        handler.execQuery(sql);
    }

    public void createCustomer() {
        System.out.println("Enter the customer name:");
        try (Scanner scan = new Scanner(System.in)) {
            String customer = scan.nextLine();
            save(new Customer(customer));
            System.out.println("The customer was added!\n");
            StartMenu.displayStartMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
