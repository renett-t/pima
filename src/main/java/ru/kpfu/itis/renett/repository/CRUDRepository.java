package ru.kpfu.itis.renett.repository;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T> {
    void save(T entity);
    void update(T entity);
    void delete(T entity);
    Optional<T> findById(int id);
    List<T> findAll();
}
