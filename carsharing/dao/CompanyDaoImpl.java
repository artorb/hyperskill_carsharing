package carsharing.dao;

import carsharing.DatabaseHandler;
import carsharing.models.Car;
import carsharing.models.Company;
import carsharing.menus.ManagerCarMenu;
import carsharing.menus.ManagerMenu;
import carsharing.menus.StartMenu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CompanyDaoImpl implements CarsharingEntity<Company> {
    private DatabaseHandler handler;
    private static CompanyDaoImpl companyRepository = new CompanyDaoImpl(new DatabaseHandler());
    private static Company activeCompany = null;


    public static Company getActiveCompany() {
        return Objects.requireNonNullElseGet(activeCompany, () -> new Company("NULL"));
    }

    public static void setActiveCompany(Company activeCompany) {
        CompanyDaoImpl.activeCompany = activeCompany;
    }

    public static CompanyDaoImpl getCompanyRepository() {
        return companyRepository;
    }

    public CompanyDaoImpl(DatabaseHandler handler) {
        this.handler = handler;
    }


    public void printCompanies() {
        List<Company> companies = new ArrayList<>(getAll());
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            ManagerMenu.displayManagerMenu();
        } else {
            IntStream.range(0, companies.size()).mapToObj(index -> String.format("%d. %s", index + 1, companies.get(index))).forEach(System.out::println);
            System.out.println("0. Back\n");
            try (Scanner scan = new Scanner(System.in)) {
                int opt = scan.nextInt();
                if (opt == 0) {
                    ManagerMenu.displayManagerMenu();
//                    StartMenu.displayStartMenu();
                } else if (opt > 0 && opt <= companies.size()) {
                    // save the company.
                    setActiveCompany(companies.get(opt - 1));
                    System.out.println("'" + companies.get(opt - 1).getName() + "' company");
                    ManagerCarMenu.displayCarMenu();
                } else {
                    System.out.println("Incorrect option!");
                    StartMenu.displayStartMenu();
                }
            }
        }
    }

    public Company getByCar(Car car) {
        String sql = "SELECT COMPANY.ID, COMPANY.NAME FROM COMPANY INNER JOIN CAR ON COMPANY.ID = CAR.COMPANY_ID WHERE CAR.ID = " + car.getId();
        try (Connection conn = handler.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                while (rs.next()) {
                    long company_id = rs.getLong("COMPANY.ID");
                    String company_name = rs.getString("COMPANY.NAME");
                    return new Company(company_name, company_id);
                }
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Company get(long id) {
        return getAll().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(new Company("NULL"));
    }

    @Override
    public List<Company> getAll() {
        List<Company> list = new ArrayList();
        String sql = "SELECT ID, NAME FROM COMPANY ORDER BY ID ASC";
        try (Connection conn = handler.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
                while (rs.next()) {
                    list.add(new Company(rs.getString("NAME"), rs.getLong("ID")));
                }
                rs.close();
            }
        } catch (Exception ignored) {
        }
        return list;
    }

    @Override
    public void save(Company name) {
        String sql = "INSERT INTO COMPANY (NAME) VALUES ('" + name.getName() + "');";
        handler.execQuery(sql);
    }

    public void createCompany() {
        System.out.println("Enter the company name:");
        try (Scanner scan = new Scanner(System.in)) {
            String company = scan.nextLine();
            save(new Company(company));
            System.out.println("The company was added!\n");
            ManagerMenu.displayManagerMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
