package ru.ncallie.SimpLibraryCRM.utils.ExceptionAndHandler;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String message) {
    super(message);
    }
}
