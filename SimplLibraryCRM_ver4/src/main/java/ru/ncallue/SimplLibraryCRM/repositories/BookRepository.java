package ru.ncallue.SimplLibraryCRM.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ncallue.SimplLibraryCRM.models.Book;
import ru.ncallue.SimplLibraryCRM.models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
