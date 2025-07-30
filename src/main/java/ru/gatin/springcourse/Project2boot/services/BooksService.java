package ru.gatin.springcourse.Project2boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gatin.springcourse.Project2boot.models.Book;
import ru.gatin.springcourse.Project2boot.models.Person;
import ru.gatin.springcourse.Project2boot.repositories.BooksRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;



@Service
@Transactional(readOnly = true)
public class BooksService  {
     private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> showBooks(boolean sortByYear){
        if (sortByYear){
            return booksRepository.findAll(Sort.by("book_year"));
        } else {
            return booksRepository.findAll();
        }
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear){
        if (sortByYear){
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("book_year"))).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
        }
    }



    public Book showBookId(int id){
        Optional<Book> findBook = booksRepository.findById(id);
        return findBook.orElse(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void updateBook(int id, Book updatedBook){
        Book bookToBeUpdated = booksRepository.findById(id).get();
        updatedBook.setBook_id(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void deleteBook(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void release(int id){
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakeAt(null);
                }
        );
    }

    @Transactional
    public void assign(int id, Person selectedPerson){
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setTakeAt(new Date());
                }
        );

    }


    public Person getBookOwner(int id){
        Book book = booksRepository.getOne(id);
        return book.getOwner();
    }

    public List<Book> searchByName(String query){
        return booksRepository.findByNameStartingWith(query);
    }
}
