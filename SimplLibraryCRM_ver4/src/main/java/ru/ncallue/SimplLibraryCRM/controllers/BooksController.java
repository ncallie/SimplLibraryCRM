package ru.ncallue.SimplLibraryCRM.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ncallue.SimplLibraryCRM.models.Book;
import ru.ncallue.SimplLibraryCRM.models.Person;
import ru.ncallue.SimplLibraryCRM.services.BookService;
import ru.ncallue.SimplLibraryCRM.services.PersonService;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BooksController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books",bookService.findAll());
        return "books/index";
    }

    @GetMapping("/{book_id}")
    public String show(Model model, @PathVariable("book_id") int book_id, @ModelAttribute("person") Person person) {
        Book book = bookService.findOne(book_id);
        Person owner = book.getOwner();
        model.addAttribute("book", book);
        if (owner != null)
            model.addAttribute("owner", owner);
        else{
            model.addAttribute("people", personService.findAll());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String edit(Model model, @PathVariable("book_id")int book_id) {
        model.addAttribute("book", bookService.findOne(book_id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("book_id") int book_id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        bookService.update(book_id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id")int book_id) {
        bookService.delete(book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/release")
    public String release(@PathVariable("book_id")int book_id) {
        bookService.release(book_id);
        return "redirect:/books/"+book_id;
    }
    @PatchMapping("/{book_id}/assign")
    public String assign(@PathVariable("book_id")int book_id, @ModelAttribute("person") Person person) {
        bookService.assign(book_id, person);
        return "redirect:/books/"+book_id;
    }
}
