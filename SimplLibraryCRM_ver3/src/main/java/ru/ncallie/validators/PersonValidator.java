package ru.ncallie.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ncallie.dao.PersonDAO;
import ru.ncallie.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;
    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person personToBeUpdate = (Person) target;
        Person personInBD = null;


        personInBD = personDAO.showEmail(personToBeUpdate.getEmail());
        if (personInBD != null) {
            if (personInBD.getPerson_id() != personToBeUpdate.getPerson_id())
                if (personInBD.getEmail().equals(personToBeUpdate.getEmail()))
                    errors.rejectValue("email", "", "Email уже занят");
        }

        personInBD = personDAO.showPhone(personToBeUpdate.getPhone());
        if (personInBD != null) {
            if (personInBD.getPerson_id() != personToBeUpdate.getPerson_id())
                if (personInBD.getPhone().equals(personToBeUpdate.getPhone()))
                    errors.rejectValue("phone", "", "Номер телефона уже занят");
        }
    }
}
