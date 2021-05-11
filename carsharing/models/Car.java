package carsharing.models;

import java.util.Objects;

public class Car extends BusinessEntity {
    private Company company;

    public Car(String name) {
        super(name);
    }

    public Car(String name, Company company) {
        super(name);
        this.company = company;
    }

    public Car(String name, Long id, Company company) {
        super(name, id);
        this.company = company;
    }

    public Company getCompany() {
        return Objects.requireNonNullElse(company, new Company("NULL"));
    }
}
