package dro.volkov.booker.user.data.service;

import dro.volkov.booker.MailService;
import dro.volkov.booker.general.service.FilterCrudService;
import dro.volkov.booker.user.data.entity.User;
import dro.volkov.booker.user.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserCrudService implements FilterCrudService<User> {

    private final UserRepository userRepository;
    private final MailService mailService;

    @Override
    public void save(User user) {
        if (user.isNew()) {
            mailService.sendActivationMessage(user.getEmail());
        } else {
            userRepository.save(user);
        }
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
        return userRepository.getUserByEmail(email);
    }
}