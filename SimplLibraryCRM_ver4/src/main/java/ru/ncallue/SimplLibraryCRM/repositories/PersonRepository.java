package ru.ncallue.SimplLibraryCRM.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ncallue.SimplLibraryCRM.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findPersonByEmail(String email);
    Person findPersonByPhone(String phone);
}
