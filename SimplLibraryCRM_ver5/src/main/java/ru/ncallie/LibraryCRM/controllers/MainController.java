package ru.ncallie.LibraryCRM.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ncallie.LibraryCRM.security.PersonDetails;

@Controller
@RequestMapping("/main")
public class MainController {
    @GetMapping("/home")
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof PersonDetails) {
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            switch (personDetails.getPerson().getRole()) {
                case "ROLE_ADMIN":
                    return "redirect:/admin/";
                case "ROLE_USER":
                    return "redirect:/user/";
                case "ROLE_STAFF":
                    return "redirect:/staff/";

            }
        }
        return "redirect:/auth/login";
    }
}
