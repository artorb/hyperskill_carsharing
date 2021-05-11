package carsharing.models;


import java.util.Objects;

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
        return Objects.requireNonNullElse(id, (long) -1);
    }

    public String getName() {
        return Objects.requireNonNullElse(name, "NULL");
    }

    @Override
    public String toString() {
        return name;
    }
}
