package org.example.library.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.CategoryDto;
import org.example.library.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ok(service.getAllCategories());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid CategoryDto requestBody) {
        var createdCategoryResponse = service.createCategory(requestBody);
        var uri = ServletUriComponentsBuilder.fromPath("/api/categories/{categoryId}")
                .buildAndExpand(createdCategoryResponse.getId())
                .toUri();
        return created(uri).body(createdCategoryResponse);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
        return ok(service.getExistingCategoryDto(categoryId));
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Integer categoryId,
            @RequestBody @Valid CategoryDto requestBody
    ) {
        return ok(service.updateCategory(categoryId, requestBody));
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Integer categoryId) {
        service.deleteCategory(categoryId);
        return noContent().build();
    }

}
