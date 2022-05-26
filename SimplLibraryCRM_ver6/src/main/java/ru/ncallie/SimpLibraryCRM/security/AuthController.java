package ru.ncallie.SimpLibraryCRM.security;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.ncallie.SimpLibraryCRM.models.Person;
import ru.ncallie.SimpLibraryCRM.services.PersonService;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private PersonService service;

    public AuthController(PersonService service) {
        this.service = service;
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Person getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        Person user = (principal instanceof Person) ? (Person) principal : null;
        return Objects.nonNull(user) ? this.service.getByUsername(user.getUsername()) : null;
    }

}
