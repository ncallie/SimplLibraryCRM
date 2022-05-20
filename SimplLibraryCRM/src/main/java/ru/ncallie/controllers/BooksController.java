package ru.ncallie.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ncallie.dao.BookDAO;
import ru.ncallie.models.Book;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;

    @Autowired
    public BooksController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books",bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{book_id}")
    public String show(Model model, @PathVariable("book_id") int book_id) {
        model.addAttribute("book", bookDAO.show(book_id));
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String edit(Model model, @PathVariable("book_id")int book_id) {
        model.addAttribute("book", bookDAO.show(book_id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}")
    public String update(@ModelAttribute("book") Book book, @PathVariable("book_id") int book_id) {
        bookDAO.update(book_id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id")int book_id) {
        bookDAO.delete(book_id);
        return "redirect:/books";
    }
}
