package carsharing.menus;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartMenu extends MenuEntity {
    private static final MenuEntity quit = new MenuEntity("Exit\n", 0, () -> System.exit(0));
    private static final MenuEntity loginManager = new MenuEntity("Log in as a manager", 1, ManagerMenu::displayManagerMenu);
    private static final MenuEntity loginCustomer = new MenuEntity("Log in as a customer", 2, MenuUtil::listCustomers);
//    private static final MenuEntity loginCustomer = new MenuEntity("Log in as a customer", 2, CustomerMenu::displayCustomerMenu);
    private static final MenuEntity createCustomer = new MenuEntity("Create a customer", 3, MenuUtil::insertCustomer);
    private static final List<MenuEntity> startMenu = new ArrayList<>(Arrays.asList(quit, loginManager, loginCustomer, createCustomer));

    public static void displayStartMenu() {
        Choosable.display(startMenu);
    }

    public void run() {
        try {
            displayStartMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
