package dro.volkov.booker.expense_2;

import dro.volkov.booker.category.data.entity.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class ExpenseFilter {

    private Double price;

    private String description;

    private LocalDate date;

    private Category category;
}
