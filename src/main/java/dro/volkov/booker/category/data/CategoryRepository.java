package dro.volkov.booker.category.data;

import dro.volkov.booker.category.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("""
            SELECT c FROM Category c
            WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            """)
    List<Category> search(@Param("searchTerm") String searchTerm);
}
