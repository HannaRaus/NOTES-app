package ua.goit.goitnotes.service;

import java.util.List;
import java.util.UUID;

public interface ServiceInterface <T> {
    T findById(UUID uuid);
    List<T> findAll();
    void create(T entity);
    void update(T entity);
    void delete(T entity);
}
