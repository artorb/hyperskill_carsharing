package carsharing.menus;


import carsharing.dao.CustomerDaoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerMenu extends MenuOption {
    private static final MenuOption back = new MenuOption("Back\n", 0, StartMenu::displayStartMenu);
    private static final MenuOption customerList = new MenuOption("Customer list", 1, CustomerDaoImpl.getCustomerRepository()::printCustomers);
    private static final MenuOption createCustomer = new MenuOption("Create a customer", 2, CustomerDaoImpl.getCustomerRepository()::createCustomer);
    private static final List<MenuOption> customerMenu = new ArrayList<>(Arrays.asList(back, customerList, createCustomer));

    public static void displayCustomerMenu() {
        OptionEntity.display(customerMenu);
    }

    public void run() {
        try {
            displayCustomerMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
