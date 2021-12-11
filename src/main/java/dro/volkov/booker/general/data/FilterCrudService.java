package dro.volkov.booker.general.data;

import java.util.List;

public interface FilterCrudService<T> {

    void save(T entity);

    void delete(T entity);

    List<T> findByFilter(String filter);
}
