package carsharing.models;

import java.util.Objects;

public class Customer extends BusinessEntity {
    private Car rentedCar;

    public Customer(String name, Long id, Car rentedCar) {
        super(name, id);
        this.rentedCar = rentedCar;
    }

    public Car getRentedCar() {
        return Objects.requireNonNullElseGet(rentedCar, () -> new Car("NULL") );
    }

    public void setRentedCar(Car rentedCar) {
        this.rentedCar = rentedCar;
    }

    public Customer(String name, Long id){
        super(name, id);
    }

    public Customer(String name){
        super(name);
    }
}
