package ru.ncallie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ncallie.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
       return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE person_id=?",
               new BeanPropertyRowMapper<>(Person.class), person_id);
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
}
