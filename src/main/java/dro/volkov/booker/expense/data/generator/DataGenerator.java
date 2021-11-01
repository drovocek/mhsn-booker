package dro.volkov.booker.expense.data.generator;

import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.expense.data.repository.ExpenseRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
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
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(ExpenseRepository expenseRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (expenseRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");
            ExampleDataGenerator<Expense> expenseGenerator = new ExampleDataGenerator<>(Expense.class, LocalDateTime.now());

//            expenseGenerator.setData(Expense::setPrice, DataType.AMOUNT_OF_MONEY);
            expenseGenerator.setData(Expense::setCategory, DataType.WORD);
            expenseGenerator.setData(Expense::setDescription, DataType.SENTENCE);
            expenseGenerator.setData(Expense::setDate, DataType.DATE_LAST_30_DAYS);
            expenseGenerator.setData(Expense::setUsername, DataType.FIRST_NAME);

            Random r = new Random(seed);
            List<Expense> expenses = expenseRepository.saveAll(expenseGenerator.create(50, seed)).stream()
                    .peek(expense -> expense.setPrice(BigDecimal.valueOf(generatePrice(r))))
                    .collect(Collectors.toList());

            expenseRepository.saveAll(expenses);

            logger.info("Generated demo data");
        };
    }

    private long generatePrice(Random r) {
        return (long) r.nextInt(100, 200);
    }

}
