package ru.ncallue.SimplLibraryCRM.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncallue.SimplLibraryCRM.models.Book;
import ru.ncallue.SimplLibraryCRM.models.Person;
import ru.ncallue.SimplLibraryCRM.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findOne(int person_id) {
        Optional<Person> byId = personRepository.findById(person_id);
        return byId.orElse(null);
    }

    public List<Book> getBookWherePersonId(int person_id) {
        Person one = findOne(person_id);
        return one.getBooks();
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(int person_id, Person person) {
        person.setPerson_id(person_id);
        personRepository.save(person);
    }

    @Transactional
    public void delete(int person_id) {
        personRepository.deleteById(person_id);
    }

    public Person getPersonByEmail(String email) {
        return personRepository.findPersonByEmail(email);
    }

    public Person getPersonByPhone(String phone) {
        return personRepository.findPersonByPhone(phone);
    }
}
