package ru.ncallie.LibraryCRM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import ru.ncallie.LibraryCRM.models.Person;
import ru.ncallie.LibraryCRM.models.PersonInfo;
import ru.ncallie.LibraryCRM.services.PersonInfoService;
import ru.ncallie.LibraryCRM.services.PersonService;
import ru.ncallie.LibraryCRM.services.RegistrationService;
import ru.ncallie.LibraryCRM.util.PersonInfoValidator;
import ru.ncallie.LibraryCRM.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/staff")
public class StaffController {
    private final PersonService personService;
    private final PersonInfoService personInfoService;
    private final RegistrationService registrationService;
    private final Validator validator;
    private final PersonValidator personValidator;
    private final PersonInfoValidator personInfoValidator;

    @Autowired
    public StaffController(PersonService personService, PersonInfoService personInfoService, RegistrationService registrationService, Validator validator, PersonValidator personValidator, PersonInfoValidator personInfoValidator) {
        this.personService = personService;
        this.personInfoService = personInfoService;
        this.registrationService = registrationService;
        this.validator = validator;
        this.personValidator = personValidator;
        this.personInfoValidator = personInfoValidator;
    }

    @GetMapping()
    public String adminPage() {
        return "staff/panel";
    }

    @GetMapping("/reg")
    public String createPersonPage(@ModelAttribute("person") Person person) {
        return "staff/reg";
    }

    @PostMapping("/reg")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "staff/reg";
        person.setRole("ROLE_USER");
        registrationService.register(person);
        return "redirect:/staff/";
    }

    @GetMapping("/users")
    public String showPage(Model model) {
        model.addAttribute("people", personService.findAll());
        return "staff/users";
    }

    @GetMapping("/users/{id}")
    public String showPersonPage(@PathVariable("id") Integer id, Model model) {
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        PersonInfo personInfo = personInfoService.findOne(id);
        if (personInfo != null) {
            model.addAttribute("person_info", personInfo);
            model.addAttribute("books", personInfo.getBooks());
        }
        return "staff/user";
    }

    @GetMapping("/users/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("person", personService.findOne(id));
        return "staff/edit_user";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") Integer id) {
        validator.validate(person.getPersonInfo(), bindingResult);
        personValidator.validate(person, bindingResult);
        Errors errors = new BeanPropertyBindingResult(person.getPersonInfo(), "person");
        personInfoValidator.validate(person, errors);
        bindingResult.addAllErrors(errors);
        if (bindingResult.hasErrors())
            return "staff/edit_user";
        personService.update(id, person);
        return "redirect:/staff/users";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id")Integer id) {
        personService.delete(id);
        return "redirect:/staff/users";
    }
}
