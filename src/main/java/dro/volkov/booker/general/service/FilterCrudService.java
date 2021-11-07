package dro.volkov.booker.general.service;

import java.util.List;

public interface FilterCrudService<T> {

    void save(T entity);

    void delete(T entity);

    List<T> findByFilter(String filter);
}
