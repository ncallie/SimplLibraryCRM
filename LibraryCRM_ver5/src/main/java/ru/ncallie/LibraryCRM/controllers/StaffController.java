package ru.ncallie.LibraryCRM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ncallie.LibraryCRM.models.Person;
import ru.ncallie.LibraryCRM.models.PersonInfo;
import ru.ncallie.LibraryCRM.services.PersonInfoService;
import ru.ncallie.LibraryCRM.services.PersonService;
import ru.ncallie.LibraryCRM.services.RegistrationService;

import javax.validation.Valid;

@Controller
@RequestMapping("/staff")
public class StaffController {
    private final PersonService personService;
    private final PersonInfoService personInfoService;
    private final RegistrationService registrationService;

    @Autowired
    public StaffController(PersonService personService, PersonInfoService personInfoService, RegistrationService registrationService) {
        this.personService = personService;
        this.personInfoService = personInfoService;
        this.registrationService = registrationService;
    }

    @GetMapping("/")
    public String adminPage() {
        return "staff/panel";
    }

    @GetMapping("/show")
    public String showPage(Model model) {
        model.addAttribute("people", personService.findAll());
        return "staff/show_user";
    }

    @GetMapping("/show/{id}")
    public String showPersonPage(@PathVariable("id") int id, Model model) {
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        PersonInfo personInfo = personInfoService.findOne(id);
        if (personInfo != null) {
            model.addAttribute("person_info", personInfo);
            model.addAttribute("books", personInfo.getBooks());
        }

        return "/staff/show_one";
    }

    @GetMapping("/show/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        return "staff/edit";
    }

    @GetMapping("/reg")
    public String createPersonPage(@ModelAttribute("person") Person person) {
        return "staff/reg";
    }

    @PostMapping("/reg")
    public String performRegistration(@ModelAttribute("person") @Valid Person person) {
        person.setRole("ROLE_USER");
        registrationService.register(person);
        return "redirect:/staff/";
    }

    @PatchMapping("/update/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personService.update(id, person);
        return "redirect:/staff/show";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id")int id) {
        personService.delete(id);
        return "redirect:/staff/show";
    }
}
