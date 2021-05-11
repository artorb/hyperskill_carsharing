package carsharing.menus;


import carsharing.dao.CarDaoImpl;
import carsharing.dao.CustomerDaoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RentCarMenu extends MenuOption {
    private static final MenuOption back = new MenuOption("Back\n", 0, StartMenu::displayStartMenu);
    private static final MenuOption rentCar = new MenuOption("Rent a car", 1, () ->
            CarDaoImpl.getCarRepository().rentCar(CustomerDaoImpl.getActiveCustomer()));
    private static final MenuOption returnRentedCar = new MenuOption("Return a rented car", 2, () ->
            CarDaoImpl.getCarRepository().returnRentedCar(CustomerDaoImpl.getActiveCustomer()));
    private static final MenuOption myRentedCar = new MenuOption("My rented car", 3, () ->
            CarDaoImpl.getCarRepository().printRentedCar(CustomerDaoImpl.getActiveCustomer()));
    private static final List<MenuOption> rentCarMenu = new ArrayList<>(Arrays.asList(back, rentCar, returnRentedCar, myRentedCar));

    public static void displayRentCarMenu() {
        OptionEntity.display(rentCarMenu);
    }

    public void run() {
        try {
            displayRentCarMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
