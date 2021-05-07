package carsharing.util;

import carsharing.dao.CarDAO;
import carsharing.dao.CompanyDAO;
import carsharing.dao.CustomerDAO;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final Scanner SCANNER = new Scanner(System.in);
    private final CompanyDAO companyRepo;
    private final CarDAO carRepo;
    private final CustomerDAO customerRepo;
    private static String compIdOption = "";

    public Menu(CarDAO carRepo, CompanyDAO companyRepo, CustomerDAO customerRepo) {
        this.companyRepo = companyRepo;
        this.carRepo = carRepo;
        this.customerRepo = customerRepo;
    }

    private void insertCar() {
        System.out.println("\nEnter the car name:");
        String name = SCANNER.nextLine();
        carRepo.save(new Car(name, Long.parseLong(compIdOption)));
        System.out.println("The car was added!\n");
        printCarMenu();
    }

    private void insertCompany() {
        System.out.println("\nEnter the company name:");
        String name = SCANNER.nextLine();
        companyRepo.save(new Company(name));
        System.out.println("The company was created!");
        printManagerMenu();
    }

    private void startMenu() { // TODO DONE
        System.out.println("1. Log in as a manager\n" +
                "2. Log in as a customer\n" +
                "3. Create a customer\n" +
                "0. Exit");
        int startOpt = Integer.parseInt(SCANNER.nextLine());
        switch (startOpt) {
            case 0:
                SCANNER.close();
                System.exit(0);
            case 1:
                printManagerMenu();
                break;
            case 2:
                printCustomerMenu();
                break;
            case 3:
                insertCustomer();
                break;
            default:
                System.out.println("Please write 0 or 1.\n");
        }
    }

    private void insertCustomer() {
        System.out.println("\nEnter the customer name:");
        String name = SCANNER.nextLine();
        customerRepo.save(new Customer(name));
        System.out.println("The customer was added!\n");
        startMenu();
    }

    private void printCustomerMenu() {
        List<Customer> list = customerRepo.findAll();
        if(list.isEmpty()){
            System.out.println("The customer list is empty!\n");
            startMenu();
        } else {
            System.out.println("Customer list:");
            list.forEach(System.out::println);
            System.out.println("0. Back");
        }
        int custOpt = Integer.parseInt(SCANNER.nextLine());
        if (custOpt == 0) {
            startMenu();
        } else {
            printCustomerCarMenu(custOpt);
        }
    }

    private void printCustomerCarMenu(int option) {
        System.out.println("\n1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");
        int custCarOpt = Integer.parseInt(SCANNER.nextLine());
        switch (custCarOpt){
            case 0:
                printCustomerMenu();
                break;
            case 1:
                printCompanyMenu();
                break;
            case 2: // check if customer has rented cars then free the id
            case 3: // return rented car ids fro current customer
            default:
                printCustomerCarMenu(option);
        }
    }

    private void printManagerMenu() {
        System.out.println("\n1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
        int managerOpt = Integer.parseInt(SCANNER.nextLine());
        switch (managerOpt) {
            case 0:
                startMenu();
                break;
            case 1:
                printCompanyMenu();
                break;
            case 2:
                insertCompany();
                printManagerMenu();
        }
    }

    private void printCarMenu() {
        System.out.println("1. Car list\n" +
                "2. Create a car\n" +
                "0. Back");
        String carOpt = SCANNER.nextLine();
        switch (carOpt) {
            case "0":
                printManagerMenu();
                break;
            case "1":
                List<Car> cars = carRepo.findByCompanyId(Long.valueOf(compIdOption));
                if (cars.isEmpty()) {
                    System.out.println("\nThe car list is empty!");
                } else {
                    System.out.println("Car list:");
                    cars.forEach(System.out::println);
                }
                System.out.println();
                printCarMenu();
                break;
            case "2":
                insertCar();
                printCarMenu();
                break;
        }
    }

    private void printCompanyMenu() {
        List<Company> companies = companyRepo.findAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            printManagerMenu();
        } else {
            System.out.println("\nChoose the company:");
            for (Company company : companies) {
                System.out.println(company);
            }
            System.out.print("0. Back");
        }
        System.out.println();
        int companyId = Integer.parseInt(SCANNER.nextLine());
        if (companyId == 0) {
            printManagerMenu();
            return; // ?
        }
        Company chosen = companies.get(companyId - 1); // FIXME it should be just get(company id shown)
        compIdOption = String.valueOf(chosen.getId());
        companies.stream().filter(x -> x.getId().equals(chosen.getId())).forEach(y -> System.out.println("\n'" + y.getName() + "'" + " company"));
        printCarMenu();
    }

    public void startMenuLoop() {
        startMenu();
    }
}
