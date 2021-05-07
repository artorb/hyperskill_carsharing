package carsharing.menus;

import carsharing.model.Company;
import carsharing.model.Customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RentCarMenu extends MenuEntity {
    private static final MenuEntity back = new MenuEntity("Back\n", 0, StartMenu::displayStartMenu); // DONE
    private static final MenuEntity returnCar = new MenuEntity("Rent a car", 1, MenuUtil::rentCar); // DONE ?
    private static final MenuEntity returnRentedCar = new MenuEntity("Return a rented car", 2, MenuUtil::returnCar); // DONE
    private static final MenuEntity myRentedCar = new MenuEntity("My rented car", 3, MenuUtil::printRentedCar); // DONE
    private static final List<MenuEntity> rentCarMenu = new ArrayList<>(Arrays.asList(back, returnCar, returnRentedCar, myRentedCar));




    public static void displayRentCarMenu() {
        Choosable.display(rentCarMenu);
    }

    public void run() {
        try {
            displayRentCarMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    private static void rentCar(Customer id, Company company){

    }

    private static void returnCar(Customer id) {

    }

    private static void printRentedCar(Customer id){
        if(id.getRentedCar() != null){
            System.out.println("YOuVERenTed " + id.getRentedCar().getName());
        }
        // if
    }
}
