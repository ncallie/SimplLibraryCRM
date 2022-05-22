package ru.ncallue.SimplLibraryCRM.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ncallue.SimplLibraryCRM.models.Person;
import ru.ncallue.SimplLibraryCRM.services.PersonService;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person personToBeUpdate = (Person) target;
        Person personInBD = null;


        personInBD = personService.getPersonByEmail(personToBeUpdate.getEmail());
        if (personInBD != null) {
            if (personInBD.getPerson_id() != personToBeUpdate.getPerson_id())
                if (personInBD.getEmail().equals(personToBeUpdate.getEmail()))
                    errors.rejectValue("email", "", "Email уже занят");
        }

        personInBD = personService.getPersonByPhone(personToBeUpdate.getPhone());
        if (personInBD != null) {
            if (personInBD.getPerson_id() != personToBeUpdate.getPerson_id())
                if (personInBD.getPhone().equals(personToBeUpdate.getPhone()))
                    errors.rejectValue("phone", "", "Номер телефона уже занят");
        }
    }
}
