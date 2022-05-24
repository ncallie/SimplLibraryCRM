package ru.ncallie.LibraryCRM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ncallie.LibraryCRM.models.Person;
import ru.ncallie.LibraryCRM.models.PersonInfo;
import ru.ncallie.LibraryCRM.services.PersonInfoService;
import ru.ncallie.LibraryCRM.services.PersonService;
import ru.ncallie.LibraryCRM.services.RegistrationService;
import ru.ncallie.LibraryCRM.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final PersonService personService;
    private final PersonInfoService personInfoService;

    @Autowired
    public AdminController(PersonValidator personValidator, RegistrationService registrationService, PersonService personService, PersonInfoService personInfoService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.personService = personService;
        this.personInfoService = personInfoService;
    }

    @GetMapping("/")
    public String adminPage() {
        return "admin/panel";
    }

    @GetMapping("/reg")
    public String createPersonPage(@ModelAttribute("person") Person person) {
        return "admin/reg";
    }

    @PostMapping("/reg")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "/admin/reg";
        registrationService.register(person);
        return "redirect:/admin/";
    }

    @GetMapping("/show")
    public String showPage(Model model) {
        model.addAttribute("people", personService.findAll());
        return "admin/show";
    }

    @GetMapping("/show/{id}")
    public String showPersonPage(@PathVariable("id") int id, Model model) {
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        PersonInfo personInfo = personInfoService.findOne(id);
        if (personInfo != null)
            model.addAttribute("person_info", personInfo);
        return "/admin/show_one";
    }

    @GetMapping("/show/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        return "admin/edit";
    }

    @PatchMapping("/update/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personService.update(id, person);
        return "redirect:/admin/show";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id")int id) {
        personService.delete(id);
        return "redirect:/admin/show";
    }


}
