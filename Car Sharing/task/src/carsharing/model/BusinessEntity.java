package carsharing.model;


public abstract class BusinessEntity {
    private String name;
    private Long id;

    public BusinessEntity(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public BusinessEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + "." + " " + name;
    }
}
