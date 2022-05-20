package ru.ncallie.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ncallie.dao.BookDAO;
import ru.ncallie.dao.PersonDAO;
import ru.ncallie.models.Book;
import ru.ncallie.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books",bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{book_id}")
    public String show(Model model, @PathVariable("book_id") int book_id, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(book_id));
        Optional<Person> owner = bookDAO.getOwner(book_id);
        if (owner.isPresent())
            model.addAttribute("owner", owner.get());
        else{
            model.addAttribute("people", personDAO.index());
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
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String edit(Model model, @PathVariable("book_id")int book_id) {
        model.addAttribute("book", bookDAO.show(book_id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("book_id") int book_id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(book_id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id")int book_id) {
        bookDAO.delete(book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/release")
    public String release(@PathVariable("book_id")int book_id) {
        bookDAO.release(book_id);
        return "redirect:/books/"+book_id;
    }
    @PatchMapping("/{book_id}/assign")
    public String assign(@PathVariable("book_id")int book_id, @ModelAttribute("person") Person person) {
        bookDAO.assign(book_id, person);
        return "redirect:/books/"+book_id;
    }
}
