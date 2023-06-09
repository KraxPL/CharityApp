package pl.krax.charity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krax.charity.entities.Category;
import pl.krax.charity.repository.CategoryRepository;
import pl.krax.charity.service.CategoryService;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;

    @Override
    @Transactional
    public void create(Category category) {
        categoryRepo.save(category);
    }

    @Override
    @Transactional
    public void update(Category category) {
        categoryRepo.save(category);
    }

    @Override
    @Transactional
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    @Transactional
    public Optional<Category> findById(Long categoryId) {
        return categoryRepo.findById(categoryId);
    }

    @Override
    @Transactional
    public void delete(Long categoryId) {
        findById(categoryId).ifPresent(categoryRepo::delete);
    }

    @Override
    @Transactional
    public List<Category> findAllByIds(List<Long> categoriesIds) {
        return categoriesIds.stream()
                .map(this::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
