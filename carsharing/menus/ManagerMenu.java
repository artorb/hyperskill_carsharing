package carsharing.menus;

import carsharing.dao.CompanyDaoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerMenu extends MenuOption {
    private static final MenuOption back = new MenuOption("Back\n", 0, StartMenu::displayStartMenu);
    private static final MenuOption companyList = new MenuOption("Company list", 1, () -> CompanyDaoImpl.getCompanyRepository().printCompanies());
    private static final MenuOption createCompany = new MenuOption("Create a company", 2, CompanyDaoImpl.getCompanyRepository()::createCompany);
    private static final List<MenuOption> managerMenu = new ArrayList<>(Arrays.asList(back, companyList, createCompany));

    public static void displayManagerMenu() {
        OptionEntity.display(managerMenu);
    }

    public void run() {
        try {
            displayManagerMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
