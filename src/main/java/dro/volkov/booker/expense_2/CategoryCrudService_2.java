package dro.volkov.booker.expense_2;

import dro.volkov.booker.category.data.CategoryRepository;
import dro.volkov.booker.category.data.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryCrudService_2 implements DataService<Category> {

    private final CategoryRepository categoryRepository;

    @Override
    public void delete(Category contact) {
        categoryRepository.delete(contact);
    }

    @Override
    public List<Category> findByFilter(Object filter) {
        return null;
    }

    @Override
    public Category save(Category contact) {
        return categoryRepository.save(contact);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}