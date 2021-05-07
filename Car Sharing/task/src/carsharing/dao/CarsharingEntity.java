package carsharing.dao;

import java.util.List;

public interface CarsharingEntity<T> {
    List<T> findAll();
    void save(T name);
}
