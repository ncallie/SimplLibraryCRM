package ru.ncallie.LibraryCRM.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncallie.LibraryCRM.models.Book;
import ru.ncallie.LibraryCRM.models.PersonInfo;
import ru.ncallie.LibraryCRM.reposittories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonInfoService personInfoService;

    @Autowired
    public BookService(BookRepository bookRepository, PersonInfoService personInfoService) {
        this.bookRepository = bookRepository;
        this.personInfoService = personInfoService;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findOne(Integer book_id) {
        Optional<Book> byId = bookRepository.findById(book_id);
        return byId.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(Integer id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void release(Integer id) {
        Book book = bookRepository.findById(id).get();
        book.setOwner(null);
    }

    @Transactional
    public void assign(Integer id, PersonInfo personInfo) {
        PersonInfo one = personInfoService.findOne(personInfo.getId());
        bookRepository.findById(id).get().setOwner(one);
    }
}