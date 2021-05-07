package carsharing.dao;


import carsharing.model.Car;

import java.util.List;

public interface CarDAO extends CarsharingEntity<Car> {
    List<Car> findByCompanyId(Long companyId);
}
