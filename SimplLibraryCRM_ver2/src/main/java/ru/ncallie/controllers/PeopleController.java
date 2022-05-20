package ru.ncallie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import ru.ncallie.dao.PersonDAO;
import ru.ncallie.models.Person;



@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }
    @GetMapping("/{person_id}")
    public String show(@PathVariable("person_id") int person_id, Model model) {
        model.addAttribute("books", personDAO.getBookWherePersonId(person_id));
        model.addAttribute("person", personDAO.show(person_id));
        return "people/show";
    }

    @GetMapping("/new")
        public String newPerson(@ModelAttribute("person")Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{person_id}/edit")
    public String edit(Model model, @PathVariable("person_id") int person_id) {
        model.addAttribute("person", personDAO.show(person_id));
        return "people/edit";
    }
    @PatchMapping("/{person_id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("person_id") int person_id) {
        personDAO.update(person_id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{person_id}")
    public String delete(@PathVariable("person_id") int person_id) {
        personDAO.delete(person_id);
        return "redirect:/people";
    }
}
