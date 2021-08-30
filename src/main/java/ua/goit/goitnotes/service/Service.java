package ua.goit.goitnotes.service;

import java.util.Set;
import java.util.UUID;

public interface Service<T> {

    T create(T entity);

    T update(T entity);

    void delete(UUID id);

    Set<T> findAll();

    T findById(UUID id);

    T findByName(String name);

}
