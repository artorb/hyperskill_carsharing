package carsharing.menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerMenu extends MenuEntity {
    private static final MenuEntity back = new MenuEntity("Back\n", 0, StartMenu::displayStartMenu);
    private static final MenuEntity customerList = new MenuEntity("Customer list", 1, MenuUtil::listCustomers);
    private static final MenuEntity createCustomer = new MenuEntity("Create a customer", 2, MenuUtil::insertCustomer);
    private static final List<MenuEntity> customerMenu = new ArrayList<>(Arrays.asList(back, customerList, createCustomer));

    public static void displayCustomerMenu() {
        Choosable.display(customerMenu);
    }

    public void run() {
        try {
            displayCustomerMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
