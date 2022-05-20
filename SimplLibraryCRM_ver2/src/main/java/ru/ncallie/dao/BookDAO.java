package ru.ncallie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.ncallie.models.Book;
import ru.ncallie.models.Person;



import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
    }



    public List<Book> index () {
       return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }
    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new BeanPropertyRowMapper<>(Book.class), id)
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book (name, author, year) VALUES(?, ?, ?)",
                book.getName(), book.getAuthor(),  book.getYear());
    }

    public void update(int book_id, Book book) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE book_id=?",
                book.getName(), book.getAuthor(), book.getYear(), book.getBook_id());
    }

    public void delete(int book_id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", book_id);
    }

    public Optional<Person> getOwner(int book_id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN person on book.person_id = person.person_id WHERE book_id=?", new BeanPropertyRowMapper<>(Person.class), book_id)
                .stream().findAny();
    }


    public void release(int book_id) {
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE book_id=?", book_id);
    }

    public void assign(int book_id, Person person) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", person.getPerson_id(), book_id);
    }
}
