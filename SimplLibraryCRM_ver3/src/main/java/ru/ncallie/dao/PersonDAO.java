package ru.ncallie.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ncallie.models.Book;
import ru.ncallie.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional()
public class PersonDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        List<Person> from_person = session.createQuery("FROM Person", Person.class).getResultList();
        return from_person;
    }

    public Person show(int person_id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, person_id);
    }


    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    public void update(int person_id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.update(person);
    }

    public void delete(int person_id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, person_id);
        session.remove(person);

    }

    public List<Book> getBookWherePersonId(int person_id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, person_id);
        Hibernate.initialize(person.getBooks());
        List<Book> books = person.getBooks();
        if (books == null)
            books = new ArrayList<>();
        return books;
    }

    //Validation
    public Person showEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<Person> query = session.createQuery("FROM Person WHERE email= :paramEmail", Person.class);
        query.setParameter("paramEmail", email);
        return query.getResultList().stream().findAny().orElse(null);

    }
    public Person showPhone(String phone) {
        Session session = sessionFactory.getCurrentSession();
        Query<Person> query = session.createQuery("FROM Person WHERE email= :paramPhone", Person.class);
        query.setParameter("paramPhone", phone);
        return query.getResultList().stream().findAny().orElse(null);
    }
}
