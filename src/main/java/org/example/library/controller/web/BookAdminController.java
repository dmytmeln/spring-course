package org.example.library.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.BookRequest;
import org.example.library.service.AuthorService;
import org.example.library.service.BookService;
import org.example.library.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/books")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class BookAdminController {

    private final BookService service;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @GetMapping
    public String getAllBooks(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            Model model
    ) {
        model.addAttribute("books", service.getAllBooks(categoryId, title));
        return "admin/books";
    }

    @GetMapping("/{bookId}")
    public String getBookDetails(
            Model model,
            @PathVariable Integer bookId
    ) {
        model.addAttribute("book", service.getExistingBookDto(bookId));
        return "admin/book-details";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("bookRequest", new BookRequest());
        model.addAttribute("authors", authorService.getAllAuthors(""));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/book-add";
    }

    @PostMapping("/add")
    public String addBook(
            @ModelAttribute("bookRequest") @Valid BookRequest bookRequest,
            Model model,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors(""));
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/book-add";
        }

        service.createBook(bookRequest);
        return "redirect:/admin/books";
    }

    @GetMapping("/{bookId}/update")
    public String updateBook(
            @PathVariable Integer bookId,
            Model model
    ) {
        model.addAttribute("bookResponse", service.getExistingBookDto(bookId));
        model.addAttribute("bookRequest", new BookRequest());
        model.addAttribute("authors", authorService.getAllAuthors(""));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/book-update";
    }

    @PostMapping("/{bookId}/update")
    public String updateBook(
            @ModelAttribute("bookRequest") @Valid BookRequest bookRequest,
            @PathVariable Integer bookId,
            Model model,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookResponse", service.getExistingBookDto(bookId));
            model.addAttribute("authors", authorService.getAllAuthors(""));
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/book-update";
        }

        service.updateBook(bookId, bookRequest);
        return "redirect:/admin/books/%d".formatted(bookId);
    }

    @GetMapping("/{bookId}/delete")
    public String deleteBook(@PathVariable Integer bookId) {
        service.deleteBook(bookId);
        return "redirect:/admin/books";
    }

}
