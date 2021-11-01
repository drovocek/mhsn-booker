package dro.volkov.booker.expense.data.service;

import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.expense.data.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public List<Expense> findByFilter(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return expenseRepository.findAll();
        } else {
            return expenseRepository.search(stringFilter);
        }
    }

    public long countExpense() {
        return expenseRepository.count();
    }

    public void deleteExpense(Expense contact) {
        expenseRepository.delete(contact);
    }

    public void saveExpense(Expense contact) {
        if (contact == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        expenseRepository.save(contact);
    }
}