package ru.ncallie.LibraryCRM.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ncallie.LibraryCRM.models.Person;
import ru.ncallie.LibraryCRM.models.PersonInfo;
import ru.ncallie.LibraryCRM.services.PersonInfoService;

@Component
public class PersonInfoValidator implements Validator {
    private final PersonInfoService personInfoService;


    public PersonInfoValidator(PersonInfoService personService) {

        this.personInfoService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PersonInfo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        PersonInfo personInfo = person.getPersonInfo();
        if (!personInfo.getFullName().matches("[А-Я][а-я]{3,20} [А-Я][а-я]{3,20} [А-Я][а-я]{3,20}")) {
            errors.rejectValue("fullName", "", "Неверный формат ФИО");
            return;
        }
        PersonInfo byBD = personInfoService.findByEmail(personInfo.getEmail());
        if (byBD != null)
            if (!byBD.getPerson().getId().equals(person.getId())) errors.rejectValue("email", "", "Email уже занят");

        byBD = personInfoService.findByPhone(personInfo.getPhone());

        if (byBD != null) if (!byBD.getPerson().getId().equals(person.getId()))
            errors.rejectValue("phone", "", "Номер телефона уже занят");
    }
}
