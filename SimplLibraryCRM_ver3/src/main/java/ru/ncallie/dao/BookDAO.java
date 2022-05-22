package ru.ncallie.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ncallie.models.Book;
import ru.ncallie.models.Person;



import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> index () {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Book show(int book_id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, book_id);
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Transactional
    public void update(int book_id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.update(book);
    }

    @Transactional
    public void delete(int book_id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        session.remove(book);
    }

    @Transactional(readOnly = true)
    public Optional<Person> getOwner(int book_id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        Person owner = book.getOwner();
        if (owner == null)
            return Optional.empty();
        return Optional.of(owner);
    }

    @Transactional
    public void release(int book_id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        book.setOwner(null);
    }

    @Transactional
    public void assign(int book_id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        book.setOwner(person);
    }
}
