package org.example.library.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.CategoryDto;
import org.example.library.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CategoryAdminController {

    private final CategoryService service;

    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories", service.getAllCategories());
        return "admin/categories";
    }

    @GetMapping("/{categoryId}")
    public String getCategoryDetails(
            Model model,
            @PathVariable Integer categoryId
    ) {
        model.addAttribute("category", service.getExistingCategoryDto(categoryId));
        return "admin/category-details";
    }

    @GetMapping("/add")
    public String addCategory(Model model) {
        model.addAttribute("category", new CategoryDto());
        return "admin/category-add";
    }

    @PostMapping("/add")
    public String addCategory(
            @ModelAttribute("category") @Valid CategoryDto category,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/category-add";
        }

        service.createCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/{categoryId}/update")
    public String updateCategory(
            @PathVariable Integer categoryId,
            Model model
    ) {
        model.addAttribute("category", service.getExistingCategoryDto(categoryId));
        return "admin/category-update";
    }

    @PostMapping("/{categoryId}/update")
    public String updateCategory(
            @ModelAttribute("category") @Valid CategoryDto category,
            @PathVariable Integer categoryId,
            Model model,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", service.getExistingCategoryDto(categoryId));
            return "admin/category-update";
        }

        service.updateCategory(categoryId, category);
        return "redirect:/admin/categories/%d".formatted(categoryId);
    }

    @GetMapping("/{categoryId}/delete")
    public String deleteCategory(@PathVariable Integer categoryId) {
        service.deleteCategory(categoryId);
        return "redirect:/admin/categories";
    }

}
