package dro.volkov.booker.expense_2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface ExpenseRepository_2 extends JpaRepository<Expense_2, Integer> {

    @Query("""
            SELECT e FROM Expense e
            WHERE LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            """)
    List<Expense_2> search(String searchTerm);

    @Query("""
            SELECT e FROM Expense e
            JOIN FETCH e.user
            WHERE e.user.id = :userId
            AND
            LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
                                        """)
    List<Expense_2> searchOwn(String searchTerm, Integer userId);

//    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id = ?1 and m.user.id = ?2")
//    Expense getWithUsers(int id, int userId);

    @Query("SELECT e from Expense e WHERE e.user.id=:userId AND e.date >= :startDate AND e.date < :endDate")
    List<Expense_2> getBetweenHalfOpenByUser(LocalDate startDate, LocalDate endDate, int userId);

    @Query("SELECT e from Expense e WHERE e.date >= :startDate AND e.date < :endDate")
    List<Expense_2> getBetweenHalfOpenAll(LocalDate startDate, LocalDate endDate);


    @Query("""
            SELECT COUNT(e) FROM Expense e
            JOIN FETCH e.user
            WHERE e.user.id = :userId
            AND e.category.id = :categoryId
            AND e.price = :price
            AND (:date IS NULL OR e.date = :date)
            AND LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
                                        """)
    int getCount(Integer categoryId, Double price, LocalDate date, String description);

    @Query("""
            SELECT COUNT(e) FROM Expense e
            JOIN FETCH e.user
            WHERE e.user.id = :userId
            AND e.category.id = :categoryId
            AND e.price = :price
            AND (:date IS NULL OR e.date = :date)
            AND LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            LIMIT :limit OFFSET :offset
                                        """)
    List<Expense_2> fetch(Integer categoryId, Double price, LocalDate date, String description, int offset, int limit);
}
