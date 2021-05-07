package carsharing;


public class Main {

    public static String[] arguments = {""};

    public static void main(String[] args) {
        arguments = args;
        Application app = new Application(args);
        app.start();
//        StartMenu m = new StartMenu();
//        m.run();
//        DatabaseHandler handler = new DatabaseHandler(args);
//        CompanyImpl company = new CompanyImpl(handler);
//        CarImpl car = new CarImpl(handler);
//        CustomerImpl customer = new CustomerImpl(handler);
//        company.createDBCompany();
//        car.createDBCar();
//        customer.createDBCustomer();
//        MenuUtil menuUtil = new MenuUtil(company, customer, car);
        //        DatabaseHandler handler = new DatabaseHandler(args);
        //        CarImpl carRepo = new CarImpl(handler);
        //        CompanyImpl companyRepo = new CompanyImpl(handler);
        //        CustomerImpl customerRepo = new CustomerImpl(handler);
        //        companyRepo.createDBCompany();
        //        carRepo.createDBCar();
        //        customerRepo.createDBCustomer();
        //        Menu menu = new Menu(carRepo, companyRepo, customerRepo);
        //        menu.startMenuLoop();
    }
}

