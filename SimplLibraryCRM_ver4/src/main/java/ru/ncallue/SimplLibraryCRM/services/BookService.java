package ru.ncallue.SimplLibraryCRM.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncallue.SimplLibraryCRM.models.Book;
import ru.ncallue.SimplLibraryCRM.models.Person;
import ru.ncallue.SimplLibraryCRM.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findOne(int book_id) {
        Optional<Book> byId = bookRepository.findById(book_id);
        return byId.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int book_id, Book book) {
        book.setBook_id(book_id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int book_id) {
        bookRepository.deleteById(book_id);
    }

    @Transactional
    public void release(int book_id) {
        Book book = bookRepository.findById(book_id).get();
        book.setOwner(null);
    }

    @Transactional
    public void assign(int book_id, Person person) {
        bookRepository.findById(book_id).get().setOwner(person);
    }
}
