package dro.volkov.booker.expense.data;

import dro.volkov.booker.expense.data.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

//    @Query("""
//            SELECT e FROM Expense e
//            JOIN FETCH e.user
//            WHERE LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
//            """)
//
@Query("SELECT e FROM Expense e " +
        "WHERE LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ")
    List<Expense> search(@Param("searchTerm") String searchTerm);


//    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id = ?1 and m.user.id = ?2")
//    Expense getWithUsers(int id, int userId);
}
