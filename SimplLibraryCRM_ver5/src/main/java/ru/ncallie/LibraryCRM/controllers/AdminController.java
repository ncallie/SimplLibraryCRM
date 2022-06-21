package ru.ncallie.LibraryCRM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
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
@RequestMapping("/admin")
public class AdminController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final PersonService personService;
    private final PersonInfoService personInfoService;
    private final PersonInfoValidator personInfoValidator;
    private final Validator validator;

    @Autowired
    public AdminController(PersonValidator personValidator, RegistrationService registrationService, PersonService personService, PersonInfoService personInfoService, PersonInfoValidator personInfoValidator, Validator validator) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.personService = personService;
        this.personInfoService = personInfoService;
        this.personInfoValidator = personInfoValidator;
        this.validator = validator;
    }

    @GetMapping()
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
            return "admin/reg";
        registrationService.register(person);
        return "redirect:/admin/";
    }

    @GetMapping("/users")
    public String showPage(Model model) {
        model.addAttribute("people", personService.findAll());
        return "admin/users";
    }


    @GetMapping("/users/{id}")
    public String showPersonPage(@PathVariable("id") Integer id, Model model) {
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        PersonInfo personInfo = personInfoService.findOne(id);
        if (personInfo != null)
            model.addAttribute("person_info", personInfo);
        return "admin/user";
    }

    @GetMapping("/users/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("person", personService.findOne(id));
        return "admin/edit_user";
    }


    @PatchMapping("/users/{id}")
    public String update(Model model, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") Integer id) {
        validator.validate(person.getPersonInfo(), bindingResult);
        personValidator.validate(person, bindingResult);
        Errors errors = new BeanPropertyBindingResult(person.getPersonInfo(), "person");
        personInfoValidator.validate(person, errors);
        bindingResult.addAllErrors(errors);
        if (bindingResult.hasErrors())
            return "admin/edit_user";
        personService.update(id, person);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") Integer id) {
        personService.delete(id);
      return "redirect:/admin/users";
    }


}
