package ru.ncallie.SimpLibraryCRM.utils.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.ncallie.SimpLibraryCRM.models.Person;
import ru.ncallie.SimpLibraryCRM.services.PersonService;

import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private PersonService service;


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            UserDetails userDetails = service.loadUserByUsername(person.getUsername());
            if (person.getId() == service.getByUsername(userDetails.getUsername()).getId())
                return;
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("username", "", "Человек с таким именем пользователя (" + person.getUsername() + ") уже существует");
    }


    @Autowired
    public void setService(PersonService service) {
        this.service = service;
    }
}