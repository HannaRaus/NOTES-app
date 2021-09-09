package ua.goit.goitnotes.interfaces;

import java.util.List;
import java.util.UUID;

public interface CrudService<T> {

    T create(T entity);

    T update(T entity);

    void delete(UUID id);

    List<T> findAll();

    T findById(UUID id);

    T findByName(String name);

}
