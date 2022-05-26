package ru.ncallie.SimpLibraryCRM.services;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ncallie.SimpLibraryCRM.models.Person;
import ru.ncallie.SimpLibraryCRM.repositories.PersonRepository;
import ru.ncallie.SimpLibraryCRM.security.PersonDetails;
import ru.ncallie.SimpLibraryCRM.utils.ExceptionAndHandler.PersonNotFoundException;

import java.util.List;
import java.util.Objects;

@Service
public class PersonService implements UserDetailsService {
    private PersonRepository personRepository;
    private PasswordEncoder passwordEncoder;


    public List<Person> findAll() {
        return this.personRepository.findAll();
    }

    public Person findOne(int id) {
        return this.personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    public void save(Person person) {
        String encode = passwordEncoder.encode(person.getPassword());
        person.setPassword(encode);
        personRepository.save(person);
    }


    public Person getByUsername(String login) {
        return this.personRepository.getByUsername(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person u = getByUsername(username);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", u));
        }
        return new PersonDetails(u);
    }

    public void update(Person person) {
        String encode = passwordEncoder.encode(person.getPassword());
        person.setPassword(encode);
        personRepository.save(person);
    }

    public void delete(int id) {
        try {
            personRepository.deleteById(id);
        }catch (Exception ignore) {
            throw new PersonNotFoundException();
        }
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


}
