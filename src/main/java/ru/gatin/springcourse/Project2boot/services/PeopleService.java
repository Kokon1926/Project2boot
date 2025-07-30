package ru.gatin.springcourse.Project2boot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gatin.springcourse.Project2boot.models.Book;
import ru.gatin.springcourse.Project2boot.models.Person;
import ru.gatin.springcourse.Project2boot.repositories.PeopleRepository;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> showPeople(){
        return peopleRepository.findAll();
    }

    public Person showPersonId(int id){
        Optional<Person> findPerson = peopleRepository.findById(id);
        return findPerson.orElse(null);
    }

    @Transactional
    public void deletePerson(int id){
        peopleRepository.deleteById(id);
    }

    @Transactional
    public void savePerson(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void updatePerson(int id, Person updatedPerson){
        updatedPerson.setPerson_id(id);
        peopleRepository.save(updatedPerson);
    }

    public List<Book> getBookOfPersonId(int id){
       Optional<Person> person = peopleRepository.findById(id);
       if (person.isPresent()){
           Hibernate.initialize(person.get().getBooks());
           person.get().getBooks().forEach(book -> {
               long diffInMillies = Math.abs(book.getTakeAt().getTime() - new Date().getTime());
               if (diffInMillies > 864000000){
                   book.setExpired(true);
               }
           });

           return person.get().getBooks();
       } else {
           return Collections.emptyList();
       }


    }
}
