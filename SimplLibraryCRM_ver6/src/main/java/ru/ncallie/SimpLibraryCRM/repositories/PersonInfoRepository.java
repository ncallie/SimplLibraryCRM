package ru.ncallie.SimpLibraryCRM.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ncallie.SimpLibraryCRM.models.PersonInfo;

import java.util.Optional;

@Repository
public interface PersonInfoRepository extends JpaRepository<PersonInfo, Integer> {
    Optional<PersonInfo> findPersonInfoByPersonId(int id);
}
