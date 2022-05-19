package ru.ncallie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ncallie.models.Book;


import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
