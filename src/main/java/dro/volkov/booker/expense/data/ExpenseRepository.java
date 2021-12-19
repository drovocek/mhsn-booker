package dro.volkov.booker.expense.data;

import dro.volkov.booker.expense.data.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query("""
            SELECT e FROM Expense e
            WHERE LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            """)
    List<Expense> search(String searchTerm);

    @Query("""
            SELECT e FROM Expense e
            JOIN FETCH e.user
            WHERE e.user.id = :userId
            AND
            LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
                                        """)
    List<Expense> searchOwn(String searchTerm, Integer userId);

//    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id = ?1 and m.user.id = ?2")
//    Expense getWithUsers(int id, int userId);

    @Query("SELECT e from Expense e WHERE e.user.id=:userId AND e.date >= :startDate AND e.date < :endDate")
    List<Expense> getBetweenHalfOpenByUser(LocalDate startDate, LocalDate endDate, int userId);

    @Query("SELECT e from Expense e WHERE e.date >= :startDate AND e.date < :endDate")
    List<Expense> getBetweenHalfOpenAll(LocalDate startDate, LocalDate endDate);
}
