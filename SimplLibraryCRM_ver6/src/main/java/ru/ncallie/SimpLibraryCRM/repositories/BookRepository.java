package ru.ncallie.SimpLibraryCRM.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ncallie.SimpLibraryCRM.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
