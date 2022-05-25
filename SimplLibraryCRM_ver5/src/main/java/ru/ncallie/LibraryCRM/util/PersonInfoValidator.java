package ru.ncallie.LibraryCRM.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ncallie.LibraryCRM.models.PersonInfo;

@Component
public class PersonInfoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PersonInfo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonInfo personInfo = (PersonInfo) target;
        if (personInfo.getFullName().length() < 4) {
            errors.rejectValue("fullname", "", "Поле не заполненно или длинна < 4");
            return;
        }
        else if (!personInfo.getFullName().matches("[А-Я][а-я]{3,20} [А-Я][а-я]{3,20} [А-Я][а-я]{3,20}")) {
            errors.rejectValue("fullname", "", "Неверный формат");
            return;
        }
    }
}
