package carsharing.dao;

import carsharing.DatabaseHandler;
import carsharing.menus.StartMenu;
import carsharing.models.Car;
import carsharing.models.Company;
import carsharing.models.Customer;
import carsharing.menus.ManagerCarMenu;
import carsharing.menus.ManagerMenu;
import carsharing.menus.RentCarMenu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CarDaoImpl implements CarsharingEntity<Car> {

    private DatabaseHandler handler;
    private static CarDaoImpl carRepository = new CarDaoImpl(new DatabaseHandler());

    public CarDaoImpl(DatabaseHandler handler) {
        this.handler = handler;
    }

    public static CarDaoImpl getCarRepository() {
        return carRepository;
    }

    public void printCarsByCompanyCustomerMenu(Company company) {
        List<Car> cars = getAll().stream().filter(car -> car.getCompany().getId().equals(company.getId())).collect(Collectors.toList());
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            ManagerCarMenu.displayCarMenu();
        } else {
            System.out.println("Choose a car:");
            IntStream.range(1, cars.size()).mapToObj(index -> String.format("%d. %s", index, cars.get(index))).forEach(System.out::println);
            System.out.println("0. Back");
            try (Scanner scan = new Scanner(System.in)) {
                int opt = scan.nextInt();
                if (opt == 0 || opt < 0) {
                    ManagerMenu.displayManagerMenu();
                } else if (opt < cars.size() - 1) {
                    System.out.println("You rented '" + cars.get(opt - 1).getName() + "'");
                    CustomerDaoImpl.getActiveCustomer().setRentedCar(cars.get(opt - 1));
                    RentCarMenu.displayRentCarMenu();
                }
            }
        }
    }

    public void printCarsByCompanyManagerMenu(Company company) {
        List<Car> cars = getAll().stream().filter(car -> car.getCompany().getId().equals(company.getId())).collect(Collectors.toList());
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            ManagerCarMenu.displayCarMenu();
        } else {
            System.out.println("Car list:");
            IntStream.range(0, cars.size()).mapToObj(index -> String.format("%d. %s", index + 1, cars.get(index))).forEach(System.out::println);
            System.out.println("0. Back");
            try (Scanner scan = new Scanner(System.in)) {
                int opt = scan.nextInt();
                if (opt == 0) {
                    ManagerMenu.displayManagerMenu();
                } else {
                    printCarsByCompanyManagerMenu(company);
                }
            }
        }
    }

    public Car getByCustomer(Customer customer) {
        String sql = "SELECT * FROM CAR INNER JOIN CUSTOMER ON CAR.ID = CUSTOMER.RENTED_CAR_ID AND CUSTOMER.NAME = '" + customer.getName() + "'";
        try (Connection conn = handler.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                while (rs.next()) {
                    long car_id = rs.getLong("CAR.ID");
                    String car_name = rs.getString("CAR.NAME");
                    long company_id = rs.getLong("COMPANY_ID");
                    Company cmp = CompanyDaoImpl.getCompanyRepository().get(company_id);
                    return new Car(car_name, car_id, cmp);
                }
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean customerHasRentedCar(Customer customer) {
        String sql = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE NAME = '" + customer.getName() + "'";
        try (Connection conn = handler.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                while (rs.next()) {
                    Object id = rs.getObject("RENTED_CAR_ID");
                    return (id != null);
                }
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Car get(long id) {
        return getAll().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(new Car("NULL"));
    }

    public List<Car> getAllUnrented(){
        String sql = "SELECT * FROM CAR LEFT JOIN CUSTOMER C ON CAR.ID = C.RENTED_CAR_ID WHERE C.RENTED_CAR_ID IS NULL;";
        return getCars(sql);
    }

    @Override
    public List<Car> getAll() {
        String sql = "SELECT ID, NAME, COMPANY_ID FROM CAR ORDER BY ID ASC";
        return getCars(sql);
    }

    private List<Car> getCars(String sql) {
        List<Car> list = new ArrayList<>();
        try (Connection conn = handler.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                while (rs.next()) {
                    long companyId = rs.getLong("COMPANY_ID");
                    long carId = rs.getLong("ID");
                    String carName = rs.getString("NAME");
                    Company company = new CompanyDaoImpl(handler).getAll().stream().filter(cmp -> cmp.getId().equals(companyId)).findFirst().get(); // car always has company_id
                    list.add(new Car(carName, carId, company));
                }
                rs.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }

    public void rentCar(Customer customer) {
        if (customerHasRentedCar(customer)) {
            System.out.println("You've already rented a car!\n");
            RentCarMenu.displayRentCarMenu();
        }
        List<Company> companies = CompanyDaoImpl.getCompanyRepository().getAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose a company:");
            IntStream.range(0, companies.size()).mapToObj(index -> String.format("%d. %s", index + 1, companies.get(index))).forEach(System.out::println);
            System.out.println("0. Back\n");
            try (Scanner scan = new Scanner(System.in)) {
                int opt = scan.nextInt();
                if (opt == 0) {
                    ManagerMenu.displayManagerMenu();
                } else if (opt > 0 && opt <= companies.size()) {
                    List<Car> cars = getAllUnrented();
                    if (cars.isEmpty()) {
                        System.out.println("The car list is empty!");
                    } else {
                        IntStream.range(0, cars.size()).mapToObj(index -> String.format("%d. %s", index + 1, cars.get(index))).forEach(System.out::println);
                        System.out.println("0. Back");
                        try (Scanner scanner = new Scanner(System.in)) {
                            int option = scan.nextInt();
                            if (option == 0 || option < 0) {
                                RentCarMenu.displayRentCarMenu();
                            } else if (option <= cars.size()) {
                                Car car = cars.get(opt - 1);
                                String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = " +
                                        car.getId() + " WHERE NAME = " +
                                        "'" + customer.getName() + "';";
                                handler.execQuery(sql);
                                System.out.println("You rented '" + cars.get(option - 1).getName() + "'");
                                CustomerDaoImpl.getActiveCustomer().setRentedCar(car);
                                RentCarMenu.displayRentCarMenu();
                            } else {
                                System.out.println("Incorrect option!");
                                RentCarMenu.displayRentCarMenu();
                            }
                        }
                    }
                } else {
                    System.out.println("Incorrect option!");
                    StartMenu.displayStartMenu();
                }
            }
        }
    }

    public void createCar(Company company) {
        System.out.println("Enter the car name:");
        try (Scanner scan = new Scanner(System.in)) {
            String name = scan.nextLine();
            save(new Car(name, company));
            System.out.println("The car was added!\n");
            ManagerCarMenu.displayCarMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnRentedCar(Customer customer) {
        if (!customerHasRentedCar(customer))
        {
            System.out.println("You didn't rent a car!\n");
        } else {
            System.out.println("You've returned a rented car!\n");
            String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE NAME = '" + customer.getName() + "';";
            handler.execQuery(sql);
            customer.setRentedCar(new Car("NULL"));
        }
        RentCarMenu.displayRentCarMenu();
    }

    public void printRentedCar(Customer customer) {
        if (!customerHasRentedCar(customer)) {
            System.out.println("You didn't rent a car!\n");
        } else {
            System.out.println("\nYou rented a car:");
            System.out.println(getByCustomer(customer).getName());
            System.out.println("Company:");
            System.out.println(getByCustomer(customer).getCompany().getName() + "\n");
        }
        RentCarMenu.displayRentCarMenu();
    }

    @Override
    public void save(Car car) {
        String sql = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('" + car.getName() + "', " + car.getCompany().getId() + ")";
        handler.execQuery(sql);
    }
}
