package ru.gatin.springcourse.Project2boot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;

    @Column(name = "fio")
    @NotEmpty(message = "Поле ФИО не должно быть пустым")
    @Size(min = 2, max = 50, message = "FIO should be between 2 and 50 characters")
    private String fio;

    @Column(name = "person_year")
    @Max(value = 2026, message = "Год рождения не может быть больше 2026 года ")
    private int person_year;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(){}

    public Person( String fio, int person_year) {
        this.fio = fio;
        this.person_year = person_year;
    }

    public Person(List<Book> books) {
        this.books = books;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }


    public int getPerson_year() {
        return person_year;
    }

    public void setPerson_year( int person_year) {
        this.person_year = person_year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
