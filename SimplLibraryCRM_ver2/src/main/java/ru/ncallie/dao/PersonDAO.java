package ru.ncallie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ncallie.models.Book;
import ru.ncallie.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person",
                new BeanPropertyRowMapper<>(Person.class));
    }
    public Person show(int person_id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new BeanPropertyRowMapper<>(Person.class), person_id).
                stream().findAny().orElse(null);
    }


    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, birth_year, email, phone) VALUES(?, ?, ?, ?)",
                person.getName(), person.getBirth_year(), person.getEmail(), person.getPhone());
    }

    public void update(int person_id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, birth_year=?, email=?, phone=? WHERE person_id=?",
                person.getName(), person.getBirth_year(), person.getEmail(), person.getPhone(), person_id);
    }

    public void delete(int person_id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?",
                person_id);
    }

    public List<Book> getBookWherePersonId(int person_id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new BeanPropertyRowMapper<>(Book.class), person_id);
    }

    //Validation
    public Person showEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE email=?",
                new BeanPropertyRowMapper<>(Person.class), email).stream().findAny().orElse(null);
    }
    public Person showPhone(String phone) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE phone=?",
                new BeanPropertyRowMapper<>(Person.class), phone).stream().findAny().orElse(null);
    }
}
