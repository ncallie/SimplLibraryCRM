package ru.ncallie.SimpLibraryCRM.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ncallie.SimpLibraryCRM.models.PersonInfo;
import ru.ncallie.SimpLibraryCRM.repositories.PersonInfoRepository;
import ru.ncallie.SimpLibraryCRM.utils.ExceptionAndHandler.PersonNotFoundInfoException;

@Service
public class PersonInfoService {
    private final PersonInfoRepository personInfoRepository;

    @Autowired
    public PersonInfoService(PersonInfoRepository personInfoRepository) {
        this.personInfoRepository = personInfoRepository;
    }

    public PersonInfo getPersonInfoByUserId(int id) {
       return personInfoRepository.findPersonInfoByPersonId(id).orElseThrow(PersonNotFoundInfoException::new);
    }
}
