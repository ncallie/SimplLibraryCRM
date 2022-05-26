package ru.ncallie.SimpLibraryCRM.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ncallie.SimpLibraryCRM.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person getByUsername(String login);
}
