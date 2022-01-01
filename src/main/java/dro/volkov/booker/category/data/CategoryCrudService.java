package dro.volkov.booker.category.data;

import dro.volkov.booker.category.data.entity.Category;
import dro.volkov.booker.general.data.FilterCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryCrudService implements FilterCrudService<Category> {

    private final CategoryRepository categoryRepository;

    @Override
    public void delete(Category contact) {
        categoryRepository.delete(contact);
    }

    @Override
    public Category save(Category contact) {
        return categoryRepository.save(contact);
    }

    @Override
    public List<Category> findByFilter(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return categoryRepository.findAll();
        } else {
            return categoryRepository.search(stringFilter);
        }
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}