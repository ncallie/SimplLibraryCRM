package ru.ncallie.LibraryCRM.controllers;

import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ncallie.LibraryCRM.models.Book;
import ru.ncallie.LibraryCRM.models.Person;
import ru.ncallie.LibraryCRM.models.PersonInfo;
import ru.ncallie.LibraryCRM.security.PersonDetails;
import ru.ncallie.LibraryCRM.services.PersonService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final PersonService personService;

    public UserController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping()
    public String showUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        PersonInfo personInfo = personService.findByPersonId(person.getId());
        if (personInfo != null) {
            Hibernate.initialize(personInfo.getBooks());
            List<Book> books = personInfo.getBooks();
            model.addAttribute("books", books);
        }
        else
            model.addAttribute("books", Collections.EMPTY_LIST);
        model.addAttribute("person", person);
        model.addAttribute("person_info", personInfo);
        return "user/user_info";
    }
}
