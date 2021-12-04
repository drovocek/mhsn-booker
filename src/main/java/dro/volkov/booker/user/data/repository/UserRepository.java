package dro.volkov.booker.user.data.repository;

import dro.volkov.booker.user.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u " +
            "where lower(u.email) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(u.registrationDate) like lower(concat('%', :searchTerm, '%'))")
    List<User> search(@Param("searchTerm") String searchTerm);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);
}
