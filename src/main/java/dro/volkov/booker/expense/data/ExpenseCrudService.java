package dro.volkov.booker.expense.data;

import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.data.FilterCrudService;
import dro.volkov.booker.security.service.SecurityService;
import dro.volkov.booker.user.data.UserCrudService;
import dro.volkov.booker.user.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpenseCrudService implements FilterCrudService<Expense> {

    private final ExpenseRepository expenseRepository;
    private final SecurityService securityService;
    private final UserCrudService userCrudService;

    @Override
    public void delete(Expense expense) {
        expenseRepository.delete(expense);
    }

    @Override
    public void save(Expense expense) {
        if (expense.getUser() == null || //For Mock data generate
                expense.isNew()) {
            UserDetails authenticatedUserDetails = securityService.getAuthenticatedUser();
            String authenticatedUsername = authenticatedUserDetails.getUsername();
            Optional<User> authenticatedUser = userCrudService.getByUsername(authenticatedUsername);
            if (authenticatedUser.isPresent()) {
                expense.setUser(authenticatedUser.get());
            } else {
                securityService.logout();
            }
        }
        expenseRepository.save(expense);
    }

    @Override
    public List<Expense> findByFilter(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return expenseRepository.findAll();
        } else {
            return expenseRepository.search(stringFilter);
        }
    }
}