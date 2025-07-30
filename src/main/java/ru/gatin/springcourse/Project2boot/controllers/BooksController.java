package ru.gatin.springcourse.Project2boot.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gatin.springcourse.Project2boot.models.Book;
import ru.gatin.springcourse.Project2boot.models.Person;
import ru.gatin.springcourse.Project2boot.services.BooksService;
import ru.gatin.springcourse.Project2boot.services.PeopleService;


@Controller
@RequestMapping("/books")
public class BooksController {
    //private final BookDAO bookDAO;
   // private final PersonDAO personDAO;

    private final PeopleService peopleService;
    private final BooksService booksService;


    public BooksController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        //this.bookDAO = bookDAO;
        //this.personDAO = personDAO;
    }

    @GetMapping()
    public String showBooks(Model model, @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                            @RequestParam(value = "sort_by_year", required = false) boolean sortByYear){
        if (page == null || booksPerPage == null) {
            model.addAttribute("books", booksService.showBooks(sortByYear));
        }
        else {
            model.addAttribute("books", booksService.findWithPagination(page, booksPerPage, sortByYear));
        }
        return "books/showBooks";
    }


    @GetMapping("/{id}")
    public String showBooksId(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.showBookId(id));

        Person bookOwner = booksService.getBookOwner(id);

        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", peopleService.showPeople());
        return "books/showBookId";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/newBook";
    }

    @GetMapping("/{id}/update")
    public String update(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.showBookId(id));
        return "books/updateBook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){
        booksService.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping
    public String crate(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "people/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson ) {
        booksService.assign(id, selectedPerson);
       return  "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("books", booksService.searchByName(query));
        return "books/search";
    }
}
