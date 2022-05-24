package ru.ncallie.LibraryCRM.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ncallie.LibraryCRM.models.Person;
import ru.ncallie.LibraryCRM.models.PersonInfo;
import ru.ncallie.LibraryCRM.reposittories.PersonRepository;

@Component
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        String encode = passwordEncoder.encode(person.getPassword());
        person.setPassword(encode);
        personRepository.save(person);
    }
}
