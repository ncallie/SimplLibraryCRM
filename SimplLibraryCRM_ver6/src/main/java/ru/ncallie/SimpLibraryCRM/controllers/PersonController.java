package ru.ncallie.SimpLibraryCRM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.ncallie.SimpLibraryCRM.models.Person;
import ru.ncallie.SimpLibraryCRM.services.PersonService;
import ru.ncallie.SimpLibraryCRM.utils.ExceptionAndHandler.PersonErrorResponse;
import ru.ncallie.SimpLibraryCRM.utils.ExceptionAndHandler.PersonNotCreatedException;
import ru.ncallie.SimpLibraryCRM.utils.ExceptionAndHandler.PersonNotFoundException;
import ru.ncallie.SimpLibraryCRM.utils.validators.PersonValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class PersonController {

    private PersonService service;
    private PersonValidator personValidator;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getAllPerson() {
        return this.service.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getPerson(@PathVariable("id") int id) {
       return service.findOne(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createPerson(@RequestBody @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError error : fieldErrors) {
                errorMessage
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMessage.toString());
        }
        service.save(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError error : fieldErrors) {
                errorMessage
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMessage.toString());
        }
        service.update(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //=====================================================
    //===================EXCEPTIONS========================
    //=====================================================

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @Autowired
    public void setService(PersonService service) {
        this.service = service;
    }

    @Autowired
    public void setPersonValidator(PersonValidator personValidator) {
        this.personValidator = personValidator;
    }
}
