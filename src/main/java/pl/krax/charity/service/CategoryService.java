package pl.krax.charity.service;

import pl.krax.charity.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    void create(Category category);
    void update(Category category);
    List<Category> findAll();
    Optional<Category> findById(Long categoryId);
    void delete (Long categoryId);
    List<Category> findAllByIds(List<Long> categoriesIds);
}
