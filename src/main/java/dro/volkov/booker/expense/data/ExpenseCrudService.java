package dro.volkov.booker.expense.data;

import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.general.data.FilterCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseCrudService implements FilterCrudService<Expense> {

    private final ExpenseRepository expenseRepository;

    @Override
    public void delete(Expense expense) {
        expenseRepository.delete(expense);
    }

    @Override
    public void save(Expense expense) {
        if (expense == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
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