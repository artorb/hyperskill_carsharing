package carsharing.menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerMenu extends MenuEntity {
    private static final MenuEntity back = new MenuEntity("Back\n", 0, StartMenu::displayStartMenu); // DONE
    private static final MenuEntity companyList = new MenuEntity("Company list", 1, () -> MenuUtil.listCompanies(false)); // DONE ?
    private static final MenuEntity createCompany = new MenuEntity("Create a company", 2, MenuUtil::insertCompany); // DONE
    private static final List<MenuEntity> managerMenu = new ArrayList<>(Arrays.asList(back, companyList, createCompany));

    public static void displayManagerMenu() {
        Choosable.display(managerMenu);
    }

    public void run() {
        try {
            displayManagerMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
