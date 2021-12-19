package dro.volkov.booker.user.data;

import dro.volkov.booker.user.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
            SELECT u FROM User u
            WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            """)
//    @Query("select u from User u " +
//            "where lower(u.email) like lower(concat('%', :searchTerm, '%')) " +
//            "or lower(u.registration) like lower(concat('%', :searchTerm, '%'))")
    List<User> search(@Param("searchTerm") String searchTerm);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

//    @EntityGraph(attributePaths = {"expenses"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query("SELECT u FROM User u WHERE u.id=?1")
//    User getWithExpenses(int id);
}
