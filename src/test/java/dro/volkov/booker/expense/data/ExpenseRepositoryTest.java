package dro.volkov.booker.expense.data;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import dro.volkov.booker.category.data.CategoryRepository;
import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.expense.data.entity.Expense;
import dro.volkov.booker.user.data.UserRepository;
import dro.volkov.booker.user.data.dict.Role;
import dro.volkov.booker.user.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static dro.volkov.booker.util.StrUtil.asUsername;

@DataJpaTest
class ExpenseRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void search() {
        System.out.println(userRepository.count());
        userRepository.search("");
    }

    @BeforeEach
    public void populateDb() {
        Logger logger = LoggerFactory.getLogger(getClass());

        int seed = 123;

        logger.info("Expense generating demo data");
        ExampleDataGenerator<User> userGenerator = new ExampleDataGenerator<>(User.class, LocalDateTime.now());
        userGenerator.setData(User::setEmail, DataType.EMAIL);
        userGenerator.setData(User::setLastAccess, DataType.DATETIME_LAST_30_DAYS);
        userGenerator.setData(User::setRegistration, DataType.DATETIME_LAST_1_YEAR);

        List<User> users = userGenerator.create(100, seed).stream()
                .peek(user -> user.setRole(Role.USER))
                .peek(user -> user.setEnabled(true))
                .peek(user -> user.setActive(true))
                .peek(user -> user.setUsername(asUsername(user.getEmail())))
                .peek(user -> user.setPassword(passwordEncoder.encode("user")))
                .collect(Collectors.toList());

        User admin = new User();
        admin.setEmail("admin@admin.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(Role.ADMIN);
        admin.setActive(true);
        admin.setEnabled(true);
        admin.setUsername(asUsername(admin.getEmail()));

        User user = new User();
        user.setEmail("user@user.com");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRole(Role.USER);
        user.setActive(true);
        user.setEnabled(true);
        user.setUsername(asUsername(user.getEmail()));

        users.add(admin);
        users.add(user);

        List<User> usersWithId = userRepository.saveAll(users);

        List<Category> categories = List.of(
                new Category("food", "#e55a10", "some description"),
                new Category("alcohol", "#1310e5", "some description"),
                new Category("sport", "#e51030", "some description")
        );
        List<Category> categoriesWithId = categoryRepository.saveAll(categories);
        Random rand = new Random();

        ExampleDataGenerator<Expense> expenseGenerator = new ExampleDataGenerator<>(Expense.class, LocalDateTime.now());
        expenseGenerator.setData(Expense::setDescription, DataType.SENTENCE);
        expenseGenerator.setData(Expense::setDate, DataType.DATE_LAST_30_DAYS);
        Random r = new Random(seed);

        List<Expense> expenses = expenseGenerator.create(50, seed).stream()
                .peek(expense -> expense.setPrice(generatePrice(r)))
                .peek(expense -> expense.setCategory(categoriesWithId.get(rand.nextInt(categoriesWithId.size()))))
                .peek(expense -> expense.setUser(usersWithId.get(rand.nextInt(usersWithId.size()))))
                .collect(Collectors.toList());

        expenseRepository.saveAll(expenses);
        logger.info("Expense generated demo data");
    }

    private double generatePrice(Random r) {
        return r.nextInt(100, 200);
    }

}