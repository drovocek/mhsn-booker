package dro.volkov.booker.general.data;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import dro.volkov.booker.category.data.CategoryRepository;
import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.expense.data.ExpenseRepository;
import dro.volkov.booker.expense.data.entity.Expense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SpringComponent
public class MockDataGenerator {

    @Bean
    public CommandLineRunner loadMockData(ExpenseRepository expenseRepository,
                                          CategoryRepository categoryRepository
    ) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (expenseRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Expense generating demo data");
            List<Category> categories = List.of(
                    new Category("food", "#e55a10", "some description"),
                    new Category("alcohol", "#1310e5", "some description"),
                    new Category("sport", "#e51030", "some description")
            );
            List<Category> categoriesWithId = categoryRepository.saveAll(categories);
            Random rand = new Random();

            ExampleDataGenerator<Expense> expenseGenerator = new ExampleDataGenerator<>(Expense.class, LocalDateTime.now());
//            expenseGenerator.setData(Expense::setPrice, DataType.AMOUNT_OF_MONEY);
//            expenseGenerator.setData(Expense::setCategory, DataType.WORD);
            expenseGenerator.setData(Expense::setDescription, DataType.SENTENCE);
            expenseGenerator.setData(Expense::setDate, DataType.DATE_LAST_30_DAYS);
            expenseGenerator.setData(Expense::setUsername, DataType.FIRST_NAME);
            Random r = new Random(seed);

            List<Expense> expenses = expenseGenerator.create(50, seed).stream()
                    .peek(expense -> expense.setPrice(BigDecimal.valueOf(generatePrice(r))))
                    .peek(expense -> expense.setCategory(categoriesWithId.get(rand.nextInt(categoriesWithId.size()))))
                    .collect(Collectors.toList());

            expenseRepository.saveAll(expenses);
            logger.info("Expense generated demo data");
        };
    }

    private long generatePrice(Random r) {
        return r.nextInt(100, 200);
    }
}
