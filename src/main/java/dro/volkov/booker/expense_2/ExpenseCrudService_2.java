package dro.volkov.booker.expense_2;

import dro.volkov.booker.dashboard.DateScale;
import dro.volkov.booker.security.service.SecurityService;
import dro.volkov.booker.user.data.dict.Role;
import dro.volkov.booker.user.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@RequiredArgsConstructor
@Service
public class ExpenseCrudService_2 implements DataService<Expense_2> {

    private final ExpenseRepository_2 expenseRepository;
    private final SecurityService securityService;

    @Override
    public void delete(Expense_2 expense) {
        expenseRepository.delete(expense);
    }

    @Override
    public Expense_2 save(Expense_2 expense) {
        if (expense.isNew()) {
            securityService.getAuthenticatedUserId()
                    .map(User::new)
                    .ifPresentOrElse(expense::setUser, securityService::logout);
            return null;
        } else {
            return expenseRepository.save(expense);
        }
    }

    @Override
    public List<Expense_2> findByFilter(Object filter) {
//        if (securityService.hasRole(Role.ADMIN)) {
//            return expenseRepository.search(stringFilter);
//        } else if (securityService.hasRole(Role.USER)) {
//            return securityService.getAuthenticatedUserId()
//                    .map(useId -> expenseRepository.searchOwn(stringFilter, useId))
//                    .orElse(Collections.emptyList());
//        }
        return getAll();
    }

    @Override
    public List<Expense_2> getAll() {
        return expenseRepository.findAll();
    }

    public List<Expense_2> findByScale(DateScale dateScale) {
        LocalDate startDate;
        LocalDate endDate;
        LocalDate now = LocalDate.now();

        if (dateScale.equals(DateScale.DAY)) {
            startDate = now;
            endDate = now;
        } else if (dateScale.equals(DateScale.WEEK)) {
            startDate = now.with(DayOfWeek.MONDAY);
            endDate = now.with(DayOfWeek.SUNDAY);
        } else if (dateScale.equals(DateScale.MONTH)) {
            startDate = now.withDayOfMonth(1);
            endDate = now.withDayOfMonth(now.lengthOfMonth());
        } else {
            startDate = now.with(firstDayOfYear());
            endDate = now.with(lastDayOfYear());
        }

        if (securityService.hasRole(Role.ADMIN)) {
            return expenseRepository.getBetweenHalfOpenAll(startDate, endDate);
        } else if (securityService.hasRole(Role.USER)) {
            return securityService.getAuthenticatedUserId()
                    .map(useId -> expenseRepository.getBetweenHalfOpenByUser(startDate, endDate, useId))
                    .orElse(Collections.emptyList());
        }
        return Collections.emptyList();
    }
}