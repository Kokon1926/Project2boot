package ru.gatin.springcourse.Project2boot.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gatin.springcourse.Project2boot.models.Person;
import ru.gatin.springcourse.Project2boot.services.PeopleService;


@Controller
@RequestMapping("/people")
public class PeopleController {

   // private final PersonDAO personDAO;
    private final PeopleService peopleService;

    public PeopleController( PeopleService peopleService) {
        this.peopleService = peopleService;
        //this.personDAO = personDAO;
    }

    @GetMapping()
    public String showPeople(Model model){
        model.addAttribute("people", peopleService.showPeople());
        return "people/showPeople";
    }

    @GetMapping("/{id}")
    public String showPersonId(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.showPersonId(id));
        model.addAttribute("books", peopleService.getBookOfPersonId(id));
        return "people/showPersonId";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/newPerson";
    }

    @GetMapping("/{id}/update")
    public String update(Model model, @PathVariable("id") int id){
        model.addAttribute("person", peopleService.showPersonId(id));
        return "people/updatePerson";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        peopleService.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.deletePerson(id);
        return "redirect:/people";
    }

    @PostMapping
    public String crate(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.savePerson(person);
        return "redirect:/people";
    }
}
