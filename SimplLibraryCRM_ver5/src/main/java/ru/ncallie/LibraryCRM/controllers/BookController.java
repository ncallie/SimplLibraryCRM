package ru.ncallie.LibraryCRM.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ncallie.LibraryCRM.models.Book;
import ru.ncallie.LibraryCRM.models.Person;
import ru.ncallie.LibraryCRM.models.PersonInfo;
import ru.ncallie.LibraryCRM.security.PersonDetails;
import ru.ncallie.LibraryCRM.services.BookService;
import ru.ncallie.LibraryCRM.services.PersonService;


import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books",bookService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("role", personDetails.getPerson().getRole());
        return "books/books";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") Integer book_id, @ModelAttribute("person") Person personInfo) {
        Book book = bookService.findOne(book_id);
        PersonInfo owner = book.getOwner();
        model.addAttribute("book", book);
        if (owner != null)
            model.addAttribute("owner", owner);
        else{
            model.addAttribute("people", personService.findAll());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("role", personDetails.getPerson().getRole());
        return "books/book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id")Integer id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit_book";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors())
            return "books/new";
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")Integer id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id")Integer id) {
        bookService.release(id);
        return "redirect:/books/"+id;
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id")Integer id, @ModelAttribute("person") PersonInfo personInfo) {
        bookService.assign(id, personInfo);
        return "redirect:/books/"+id;
    }
}
