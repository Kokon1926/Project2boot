package ru.gatin.springcourse.Project2boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gatin.springcourse.Project2boot.models.Person;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
