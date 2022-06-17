package ru.ncallie.LibraryCRM.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ncallie.LibraryCRM.models.Person;
import ru.ncallie.LibraryCRM.models.PersonInfo;
import ru.ncallie.LibraryCRM.reposittories.PersonInfoRepository;
import ru.ncallie.LibraryCRM.reposittories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonInfoRepository personInfoRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonInfoRepository personInfoRepository) {
        this.personRepository = personRepository;
        this.personInfoRepository = personInfoRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findOne(Integer id) {
        Optional<Person> byId = personRepository.findById(id);
        return byId.orElse(null);
    }

    public void update(Integer id, Person person) {
        person.setId(id);
        person.getPersonInfo().setPerson(person);
        Optional<PersonInfo> byPersonId = personInfoRepository.findByPersonId(id);
        if (byPersonId.isPresent())
            person.getPersonInfo().setId(byPersonId.get().getId());
        personInfoRepository.save(person.getPersonInfo());
        personRepository.save(person);
    }

    public void delete(Integer id) {
        personRepository.deleteById(id);
    }

    public PersonInfo findByPersonId(Integer id) {
        Optional<PersonInfo> byPersonId = personInfoRepository.findByPersonId(id);
        return byPersonId.orElse(null);

    }

    public Person findByName(String username) {
       return personRepository.findByUsername(username).orElse(null);
    }
}
