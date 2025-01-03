package org.example.library.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.AuthorDto;
import org.example.library.service.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/authors")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AuthorAdminController {

    private final AuthorService service;

    @GetMapping
    public String getAllAuthors(
            @RequestParam(value = "fullName", defaultValue = "") String fullName,
            Model model
    ) {
        model.addAttribute("authors", service.getAllAuthors(fullName));
        return "admin/authors";
    }

    @GetMapping("/{authorId}")
    public String getAuthorDetails(
            Model model,
            @PathVariable Integer authorId
    ) {
        model.addAttribute("author", service.getExistingAuthorDto(authorId));
        return "admin/author-details";
    }

    @GetMapping("/add")
    public String addAuthor(Model model) {
        model.addAttribute("author", new AuthorDto());
        return "admin/author-add";
    }

    @PostMapping("/add")
    public String addAuthor(
            @ModelAttribute("author") @Valid AuthorDto author,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/author-add";
        }

        service.createAuthor(author);
        return "redirect:/admin/authors";
    }

    @GetMapping("/{authorId}/update")
    public String updateAuthor(
            @PathVariable Integer authorId,
            Model model
    ) {
        model.addAttribute("author", service.getExistingAuthorDto(authorId));
        return "admin/author-update";
    }

    @PostMapping("/{authorId}/update")
    public String updateAuthor(
            @ModelAttribute("author") @Valid AuthorDto author,
            @PathVariable Integer authorId,
            Model model,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("author", service.getExistingAuthorDto(authorId));
            return "admin/author-update";
        }

        service.updateAuthor(authorId, author);
        return "redirect:/admin/authors/%d".formatted(authorId);
    }

    @GetMapping("/{authorId}/delete")
    public String deleteAuthor(@PathVariable Integer authorId) {
        service.deleteAuthor(authorId);
        return "redirect:/admin/authors";
    }

}
