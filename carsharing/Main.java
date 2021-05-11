package carsharing;


import carsharing.dao.CarDaoImpl;
import carsharing.dao.CompanyDaoImpl;
import carsharing.dao.CustomerDaoImpl;
import carsharing.menus.StartMenu;

public class Main {

    public static void main(String[] args) {
        DatabaseHandler handler = new DatabaseHandler(args);
        CarDaoImpl car = new CarDaoImpl(handler);
        CustomerDaoImpl customer = new CustomerDaoImpl(handler);
        CompanyDaoImpl company = new CompanyDaoImpl(handler);
        StartMenu.displayStartMenu();
    }
}

