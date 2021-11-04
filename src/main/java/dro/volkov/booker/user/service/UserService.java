package dro.volkov.booker.user.service;

import dro.volkov.booker.general.service.EntityCrudService;
import dro.volkov.booker.user.entity.User;
import dro.volkov.booker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements EntityCrudService<User> {

    private final UserRepository userRepository;

    @Override
    public void save(User entity) {
        userRepository.save(entity);
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Override
    public List<User> findByFilter(String filter) {
        return userRepository.search(filter);
    }
}
