package dro.volkov.booker.expense_2.general;

import java.util.List;

public interface DataService<T> {

    T save(T entity);

    void delete(T entity);

    List<T> findByFilterFields(T filter);
}
