package carsharing.menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarMenu extends MenuEntity {
    private static final MenuEntity back = new MenuEntity("Back\n", 0, ManagerMenu::displayManagerMenu);
    private static final MenuEntity carList = new MenuEntity("Car list", 1, MenuUtil::listCars);
    private static final MenuEntity createCar = new MenuEntity("Create a car", 2, MenuUtil::insertCar);
    private static final List<MenuEntity> carMenu = new ArrayList<>(Arrays.asList(back, carList, createCar));

    public static void displayCarMenu() {
        Choosable.display(carMenu);
    }

    public void run() {
        try {
            displayCarMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

}
