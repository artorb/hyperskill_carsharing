package carsharing.menus;



import carsharing.dao.CustomerDaoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartMenu extends MenuOption {
    private static final MenuOption quit = new MenuOption("Exit\n", 0, () -> System.exit(0));
    private static final MenuOption loginManager = new MenuOption("Log in as a manager", 1, ManagerMenu::displayManagerMenu);
    private static final MenuOption loginCustomer = new MenuOption("Log in as a customer", 2, CustomerDaoImpl.getCustomerRepository()::printCustomers);
    private static final MenuOption createCustomer = new MenuOption("Create a customer", 3, CustomerDaoImpl.getCustomerRepository()::createCustomer);
    private static final List<MenuOption> startMenu = new ArrayList<>(Arrays.asList(quit, loginManager, loginCustomer, createCustomer));

    public static void displayStartMenu() {
        OptionEntity.display(startMenu);
    }

    public void run() {
        try {
            displayStartMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

}
