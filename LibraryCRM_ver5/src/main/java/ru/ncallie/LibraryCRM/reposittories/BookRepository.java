package ru.ncallie.LibraryCRM.reposittories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ncallie.LibraryCRM.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
