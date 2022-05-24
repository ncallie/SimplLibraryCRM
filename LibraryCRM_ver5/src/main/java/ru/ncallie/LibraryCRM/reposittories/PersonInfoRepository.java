package ru.ncallie.LibraryCRM.reposittories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ncallie.LibraryCRM.models.PersonInfo;

import java.util.Optional;

@Repository
public interface PersonInfoRepository extends JpaRepository<PersonInfo, Integer> {
    Optional<PersonInfo> findByPersonId(int person_id);
}
