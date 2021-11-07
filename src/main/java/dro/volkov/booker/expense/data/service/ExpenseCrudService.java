package dro.volkov.booker.expense.data.service;

import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.expense.data.repository.ExpenseRepository;
import dro.volkov.booker.general.service.FilterCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseCrudService implements FilterCrudService<Expense> {

    private final ExpenseRepository expenseRepository;

    @Override
    public void delete(Expense contact) {
        expenseRepository.delete(contact);
    }

    @Override
    public void save(Expense contact) {
        if (contact == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        expenseRepository.save(contact);
    }

    @Override
    public List<Expense> findByFilter(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return expenseRepository.findAll();
        } else {
            return expenseRepository.search(stringFilter);
        }
    }


    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public long countExpense() {
        return expenseRepository.count();
    }
}