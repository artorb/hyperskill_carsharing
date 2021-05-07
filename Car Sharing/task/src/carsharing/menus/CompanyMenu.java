package carsharing.menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyMenu extends MenuEntity {
    private static final MenuEntity back = new MenuEntity("Back\n", 0, ManagerMenu::displayManagerMenu);
    private static final MenuEntity carList = new MenuEntity("Choose a company:", 1, () -> MenuUtil.listCompanies(false));
    private static final MenuEntity createCar = new MenuEntity("Create a car", 2, MenuUtil::insertCar);
    private static final List<MenuEntity> companyMenu = new ArrayList<>(Arrays.asList(back, carList, createCar));

    public static void displayCompanyMenu() {
//        if (MenuUtil.getCompanies().isEmpty()) {
//            System.out.println("The company list is empty!");
//            ManagerMenu.displayManagerMenu();
//            return;
//        }
        Choosable.display(companyMenu);
    }


    public void run() {
        try {
            displayCompanyMenu();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
