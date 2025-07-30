package ru.gatin.springcourse.Project2boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gatin.springcourse.Project2boot.models.Book;


import java.util.List;

@Repository
public interface BooksRepository   extends JpaRepository<Book, Integer> {
    List<Book> findByNameStartingWith(String name);
}
