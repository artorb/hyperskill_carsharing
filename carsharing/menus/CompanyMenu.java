package carsharing.menus;

import carsharing.dao.CarDaoImpl;
import carsharing.dao.CompanyDaoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyMenu extends MenuOption {
    private static final MenuOption back = new MenuOption("Back\n", 0, ManagerMenu::displayManagerMenu);
    private static final MenuOption carList = new MenuOption("Choose a company:", 1, ()
            -> CompanyDaoImpl.getCompanyRepository().printCompanies());
    private static final MenuOption createCar = new MenuOption("Create a car", 2, ()
            -> CarDaoImpl.getCarRepository().createCar(CompanyDaoImpl.getActiveCompany()));
    private static final List<MenuOption> companyMenu = new ArrayList<>(Arrays.asList(back, carList, createCar));

    public static void displayCompanyMenu() {
        OptionEntity.display(companyMenu);
    }

    public void run() {
        try {
            displayCompanyMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
