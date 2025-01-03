package org.example.library.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.user.CreateUser;
import org.example.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("webAuthController")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;

    @GetMapping("/signin")
    public String showSignin(Model model) {
        model.addAttribute("createUser", new CreateUser());
        return "signin";
    }

    @PostMapping("/signin")
    public String processSignin(
            @ModelAttribute("createUser") @Valid CreateUser createUser,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "signin";
        }

        service.createUser(createUser);
        return "redirect:/login";
    }

}
