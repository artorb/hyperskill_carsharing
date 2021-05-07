package carsharing;

import carsharing.menus.StartMenu;
import carsharing.util.DatabaseHandler;

public class Application {
    private StartMenu startMenu = new StartMenu();
    DatabaseHandler hander;

    public Application(String... args) {
        hander = new DatabaseHandler(args);
        hander.createDBCompany();
        hander.createDBCar();
        hander.createDBCustomer();
    }

    public void start() {
        startMenu.run();
    }
}
