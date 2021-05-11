package carsharing.menus;

import carsharing.dao.CarDaoImpl;
import carsharing.dao.CompanyDaoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerCarMenu extends MenuOption {
    private static final MenuOption back = new MenuOption("Back\n", 0, ManagerMenu::displayManagerMenu);
    private static final MenuOption carList = new MenuOption("Car list", 1, ()
            -> CarDaoImpl.getCarRepository().printCarsByCompanyCustomerMenu(CompanyDaoImpl.getActiveCompany()));
    private static final MenuOption createCar = new MenuOption("Create a car", 2, ()
            -> CarDaoImpl.getCarRepository().createCar(CompanyDaoImpl.getActiveCompany()));
    private static final List<MenuOption> carMenu = new ArrayList<>(Arrays.asList(back, carList, createCar));


    public static void displayCarMenu() {
        OptionEntity.display(carMenu);
    }

    public void run() {
        try {
            displayCarMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
