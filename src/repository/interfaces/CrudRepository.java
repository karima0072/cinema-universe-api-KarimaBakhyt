package repository.interfaces;

import java.util.List;

public interface CrudRepository<T, ID> {
    void create(T entity);
    T getById(ID id);
    List<T> getAll();
    void update(ID id, T entity);
    void deleteById(ID id);
}