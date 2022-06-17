package ru.ncallie.LibraryCRM.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ncallie.LibraryCRM.models.Person;
import ru.ncallie.LibraryCRM.services.PersonDetailsService;
import ru.ncallie.LibraryCRM.services.PersonService;

@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService, PersonService personService) {
        this.personDetailsService = personDetailsService;
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Person byNameBD = personService.findByName(person.getUsername());
        if (byNameBD == null) return;
        if (byNameBD.getId() == person.getId()) return;
        errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
    }
}
