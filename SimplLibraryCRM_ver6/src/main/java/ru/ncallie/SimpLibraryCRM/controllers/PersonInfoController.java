package ru.ncallie.SimpLibraryCRM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ncallie.SimpLibraryCRM.models.PersonInfo;
import ru.ncallie.SimpLibraryCRM.services.PersonInfoService;
import ru.ncallie.SimpLibraryCRM.utils.ExceptionAndHandler.PersonErrorResponse;
import ru.ncallie.SimpLibraryCRM.utils.ExceptionAndHandler.PersonNotFoundInfoException;

@RestController
@RequestMapping("/api/users/info")
public class PersonInfoController {
    private PersonInfoService personInfoService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonInfo getUserInfo(@PathVariable("id") int id) {
       return personInfoService.getPersonInfoByUserId(id);

    }

//    @PostMapping(path = "/id", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<HttpStatus> createPersonInfo(@PathVariable("id") int id, @RequestBody PersonInfo personInfo, BindingResult bindingResult) {
//
//    }


    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundInfoException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                        "More detailed information about the user is not set." +
                                "Or user with this id does not exist",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @Autowired
    public void setPersonInfoService(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }


}
