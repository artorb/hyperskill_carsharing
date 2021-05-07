package carsharing.model;


public class Customer extends BusinessEntity {
    private Car rentedCar;

    public Customer(String name, Long id, Car rentedCar) {
        super(name, id);
        this.rentedCar = rentedCar;
    }

    public void setRentedCar(Car rentedCar) {
        this.rentedCar = rentedCar;
    }

    public Car getRentedCar() {
        return rentedCar;
    }

    public Customer(String name){
        super(name);
    }
}
