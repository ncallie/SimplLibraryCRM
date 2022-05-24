package ru.ncallie.LibraryCRM.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ncallie.LibraryCRM.models.Person;
import ru.ncallie.LibraryCRM.reposittories.PersonRepository;
import ru.ncallie.LibraryCRM.security.PersonDetails;

import java.util.List;
import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> byUsername = personRepository.findByUsername(username);
        if (byUsername.isEmpty())
            throw new UsernameNotFoundException("User not found!");
        return new PersonDetails(byUsername.get());
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
}
