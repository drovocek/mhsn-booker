package dro.volkov.booker.user.data;

import dro.volkov.booker.general.data.FilterCrudService;
import dro.volkov.booker.user.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserCrudService implements FilterCrudService<User> {

    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findByFilter(String filter) {
        return userRepository.search(filter);
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public boolean emailNotExist(String email) {
        return getByEmail(email).isEmpty();
    }

    public boolean usernameNotExist(String username) {
        return userRepository.findUserByUsername(username).isEmpty();
    }
}
