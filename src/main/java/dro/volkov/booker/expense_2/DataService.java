package dro.volkov.booker.expense_2;

import java.util.List;

public interface DataService<T> {

    T save(T entity);

    void delete(T entity);

    List<T> getAll();

    List<T> findByFilter(Object filter);

    List<T> fetch(int offset, int limit, Object filter);

    int getCount(Object filter);
}
