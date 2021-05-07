package carsharing.model;

public class Car extends BusinessEntity{
    private Long companyId;

    public Car(String name, Long id, Long companyId) {
        super(name, id);
        this.companyId = companyId;
    }

    public Car(String name, Long companyId) {
        super(name);
        this.companyId = companyId;
    }

    public Long getCompanyId() {
        return companyId;
    }
}
