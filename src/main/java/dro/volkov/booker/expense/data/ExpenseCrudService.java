package dro.volkov.booker.expense.data;

import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.data.FilterCrudService;
import dro.volkov.booker.security.service.SecurityService;
import dro.volkov.booker.user.data.dict.Role;
import dro.volkov.booker.user.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseCrudService implements FilterCrudService<Expense> {

    private final ExpenseRepository expenseRepository;
    private final SecurityService securityService;

    @Override
    public void delete(Expense expense) {
        expenseRepository.delete(expense);
    }

    @Override
    public void save(Expense expense) {
        if (expense.isNew()) {
            securityService.getAuthenticatedUserId()
                    .map(User::new)
                    .ifPresentOrElse(expense::setUser, securityService::logout);
        }
        expenseRepository.save(expense);
    }

    @Override
    public List<Expense> findByFilter(String stringFilter) {
        if (securityService.hasRole(Role.ADMIN)) {
            return expenseRepository.search(stringFilter);
        } else if (securityService.hasRole(Role.USER)) {
            return securityService.getAuthenticatedUserId()
                    .map(useId -> expenseRepository.searchOwn(stringFilter, useId))
                    .orElse(Collections.emptyList());
        }
        return Collections.emptyList();
    }
}