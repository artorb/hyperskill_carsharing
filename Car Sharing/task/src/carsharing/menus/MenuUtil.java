package carsharing.menus;

import carsharing.impl.CarImpl;
import carsharing.impl.CompanyImpl;
import carsharing.impl.CustomerImpl;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;
import carsharing.util.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuUtil extends MenuEntity {
    private static CustomerImpl customerRepository = new CustomerImpl();
    private static CompanyImpl companyRepository = new CompanyImpl();
    private static CarImpl carRepository = new CarImpl();
    private static MenuUtil menuUtil = new MenuUtil();
    private static long companyID = 0;
    private static Car activeCar = null; // maybe also company = null if to check
    private static Customer activeCustomer = null;
    private static Company activeCompany = null;

    public static MenuUtil getInstance() {
        if (menuUtil == null) {
            menuUtil = new MenuUtil();
        }
        return menuUtil;
    }

    public static List<Customer> getCustomerRepository() {
        return customerRepository.findAll();
    }

    public static List<Company> getCompanyRepository() {
        return companyRepository.findAll();
    }

    public static List<Car> getCarRepository() {
        return carRepository.findAll();
    }

    private MenuUtil() {
    }

    /* <------------------------------------- UPDATE FUNCTIONS ------------------------------------------------------------> */

    private static void carAction(Long id) {
        List<Car> found = carRepository.findAll().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        found.forEach(x -> System.out.println("\nYou rented '" + x.getName() + "'\n"));
        activeCar = found.get(0);
        RentCarMenu.displayRentCarMenu();
    }

    public static void returnCar() {
        if (activeCar != null) { // идиот? нужно чтобы кастомер айди сохранялся и ты чекал если у него есть ссылки на кар
            System.out.println("You've returned a rented car!\n"); // here update SQL
            activeCar = null;
            // maybe here we NULL Company
        } else {
            System.out.println("You didn't rent a car!\n");
        }
        RentCarMenu.displayRentCarMenu();
    }

    private static void customerAction(Long id) {
        List<Customer> found = customerRepository.findAll().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        activeCustomer = found.get(0);
        // exec query here to put that his stuff is not null
//        found.forEach(x -> System.out.println("'" + x.getName() + "' company"));
        RentCarMenu.displayRentCarMenu();
    }

    private static void companyAction(Long id, boolean isCustomerMenu) {
        List<Company> found = companyRepository.findAll().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        companyID = found.get(0).getId();
        activeCompany = found.get(0);

        if (!isCustomerMenu) {
            found.forEach(x -> System.out.println("'" + x.getName() + "' company"));
            CarMenu.displayCarMenu();
        } else {
            listCars();
        }
        //print CARMENU list etc
        //TODO takes company ID to return cars
    }

    public static void rentCar() {
        if (activeCar != null && activeCompany != null) {
            System.out.println("You've already rented a car!\n");
            RentCarMenu.displayRentCarMenu();
        } else {
            listCompanies(true);
        }
    }

    /* <------------------------------------- UPDATE FUNCTIONS ------------------------------------------------------------> */

    /* <------------------------------------- READ FUNCTIONS --------------------------------------------------------------> */

    public static void printRentedCar() {
        if (activeCar == null) {
            System.out.println("You didn't rent a car!\n");
        } else {
            System.out.println("\nYou rented car:");
            System.out.println(activeCar.getName());
            System.out.println("Company:");
            System.out.println(activeCompany.getName() + "\n");
        }
        RentCarMenu.displayRentCarMenu();
    }

    public static void listCompanies(boolean isCustomerMenu) {
        if (companyRepository.findAll().isEmpty()) {
            System.out.println("The company list is empty!");
            ManagerMenu.displayManagerMenu();
        } else {
            List<MenuEntity> companyList = new ArrayList<>();
            List<Company> cmp = companyRepository.findAll();
            for (long i = 0; i < cmp.size(); i++) {
                Company company = cmp.get((int) i);
                companyList.add(new MenuEntity(company.getName(), i + 1, () -> companyAction(company.getId(), isCustomerMenu)));
            }
            MenuEntity back = new MenuEntity("Back\n", 0, ManagerMenu::displayManagerMenu);
            companyList.add(0, back);
            System.out.println("Choose a company:");
            printListOf(companyList);
        }
    }

    public static void listCars(Company company) {
        if (carRepository.findAll().isEmpty()) {
            System.out.println("The car list is empty!");
            CarMenu.displayCarMenu();
        } else {
            List<MenuEntity> carList = new ArrayList<>();
            List<Car> cars = carRepository.findAll();
            for (long i = 0; i < cars.size(); i++) {
                Car car = cars.get((int) i);
                carList.add(new MenuEntity(car.getName(), i + 1, () -> carAction(car.getId())));
            }
            MenuEntity back = new MenuEntity("Back\n", 0, ManagerMenu::displayManagerMenu);
            carList.add(0, back);
            System.out.println("Choose a car:");
            printListOf(carList);
        }
    }

    public static void listCustomers() {
        if (customerRepository.findAll().isEmpty()) {
            System.out.println("The customer list is empty!\n");
            StartMenu.displayStartMenu();
        } else {
            List<MenuEntity> customerList = new ArrayList<>();
            List<Customer> customers = customerRepository.findAll();
            for (long i = 0; i < customers.size(); i++) {
                Customer customer = customers.get((int) i);
                customerList.add(new MenuEntity(customer.getName(), i + 1, () -> customerAction(customer.getId())));
            }
            MenuEntity back = new MenuEntity("Back\n", 0, ManagerMenu::displayManagerMenu);
            customerList.add(0, back);
            System.out.println("The customer list:");
            printListOf(customerList);
        }
    }

    private static void printListOf(List<MenuEntity> menu) {
        menu.stream().skip(1).forEach(item -> System.out.println(item.getOption() + ". " + item.getTitle()));
        menu.stream().findFirst().ifPresent(exitOption -> System.out.print(exitOption.getOption() + ". " + exitOption.getTitle()));
        try (Scanner scan = new Scanner(System.in)) {
            int option = scan.nextInt();
            menu.get(option).run();
        }
    }

    /* <------------------------------------- READ FUNCTIONS --------------------------------------------------------------> */

    /* <------------------------------------- CREATE FUNCTIONS ------------------------------------------------------------> */

    private void createCustomer() {
        System.out.println("Enter the customer name:");
        try (Scanner scan = new Scanner(System.in)) {
            String custName = scan.nextLine();
            customerRepository.save(new Customer(custName));
            System.out.println("The customer was added!\n");
            StartMenu.displayStartMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCompany() {
        System.out.println("Enter the company name:");
        try (Scanner scan = new Scanner(System.in)) {
            String compName = scan.nextLine();
            companyRepository.save(new Company(compName));
            System.out.println("The company was added!\n");
            ManagerMenu.displayManagerMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCar() {
        System.out.println("Enter the car name:");
        try (Scanner scan = new Scanner(System.in)) {
            String carName = scan.nextLine();
            carRepository.save(new Car(carName, companyID));
            System.out.println("The car was added!\n");
            CarMenu.displayCarMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertCompany() {
        menuUtil.createCompany();
    }

    public static void insertCustomer() {
        menuUtil.createCustomer();
    }

    public static void insertCar() {
        menuUtil.createCar();
    }

    /* <------------------------------------- CREATE FUNCTIONS ------------------------------------------------------------> */



}
