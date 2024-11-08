package org.example.library.service;

import lombok.RequiredArgsConstructor;
import org.example.library.domain.Category;
import org.example.library.dto.CategoryDto;
import org.example.library.exception.NotFoundException;
import org.example.library.mapper.CategoryMapper;
import org.example.library.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<CategoryDto> getAllCategories() {
        return mapper.toDto(repository.findAll());
    }

    public Category getExistingCategory(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категорію не знайдено!"));
    }

    public CategoryDto getExistingCategoryDto(Integer id) {
        return mapper.toDto(getExistingCategory(id));
    }

    public CategoryDto createCategory(CategoryDto dto) {
        requireNotExistsByName(dto.getName());

        var savedCategory = repository.save(mapper.toEntity(dto));
        return mapper.toDto(savedCategory);
    }

    public CategoryDto updateCategory(Integer id, CategoryDto dto) {
        requireNotExistsByName(id, dto.getName());
        var updatedCategory = repository.save(mapper.toEntity(dto, id));
        return mapper.toDto(updatedCategory);
    }

    private void requireNotExistsByName(Integer id, String categoryName) {
        var existingCategory = getExistingCategory(id);
        if (!existingCategory.getName().equals(categoryName)) {
            requireNotExistsByName(categoryName);
        }
    }

    private void requireNotExistsByName(String name) {
        if (repository.existsByName(name)) {
            throw new IllegalArgumentException("Категорія з таким ім'ям вже існує!");
        }
    }

    public void deleteCategory(Integer id) {
        repository.deleteById(id);
    }

}
