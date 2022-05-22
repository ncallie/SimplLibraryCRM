package ru.ncallue.SimplLibraryCRM.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ncallue.SimplLibraryCRM.models.Person;
import ru.ncallue.SimplLibraryCRM.services.PersonService;
import ru.ncallue.SimplLibraryCRM.validators.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;
    private final PersonValidator personValidator;


    @Autowired
    public PeopleController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/index";
    }


    @GetMapping("/{person_id}")
    public String show(@PathVariable("person_id") int person_id, Model model) {
        model.addAttribute("books", personService.getBookWherePersonId(person_id));
        model.addAttribute("person", personService.findOne(person_id));
        return "people/show";
    }

    @GetMapping("/new")
        public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{person_id}/edit")
    public String edit(Model model, @PathVariable("person_id") int person_id) {
        model.addAttribute("person", personService.findOne(person_id));
        return "people/edit";
    }
    @PatchMapping("/{person_id}")
    public String update(@ModelAttribute("person") @Valid Person person,  BindingResult bindingResult, @PathVariable("person_id") int person_id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        personService.update(person_id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{person_id}")
    public String delete(@PathVariable("person_id") int person_id) {
        personService.delete(person_id);
        return "redirect:/people";
    }
}
