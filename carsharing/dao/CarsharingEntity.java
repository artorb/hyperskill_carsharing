package carsharing.dao;

import java.util.List;
import java.util.Optional;

public interface CarsharingEntity<T> {
    T get(long id);
    List<T> getAll();
    void save(T name);
}
