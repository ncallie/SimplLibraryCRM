package ru.ncallie.LibraryCRM.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ncallie.LibraryCRM.models.PersonInfo;
import ru.ncallie.LibraryCRM.reposittories.PersonInfoRepository;

import java.util.Optional;

@Service
public class PersonInfoService {
    private final PersonInfoRepository personInfoRepository;

    @Autowired
    public PersonInfoService(PersonInfoRepository personInfoRepository) {
        this.personInfoRepository = personInfoRepository;
    }

    public PersonInfo findOne(Integer person_id) {
        Optional<PersonInfo> byId = personInfoRepository.findByPersonId(person_id);
        return byId.orElse(null);
    }

    public PersonInfo findByEmail(String email) {
       return personInfoRepository.findByEmail(email);
    }

    public PersonInfo findByPhone(String phone) {
        return personInfoRepository.findByPhone(phone);
    }
}
