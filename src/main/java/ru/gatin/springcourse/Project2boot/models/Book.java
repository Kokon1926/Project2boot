package ru.gatin.springcourse.Project2boot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;

    @Column(name = "name")
    @NotEmpty(message = "название не должно быть пустым")
    @Size(min = 2, max = 30, message = "Название книги может быть от 2 до 30 символов")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "book_year")
    private int book_year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takeAt;

    @Transient
    private boolean expired;

    public Book(){};

    public Book( String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.book_year = year;
    }

    public Book(Person owner) {
        this.owner = owner;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int id) {
        this.book_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBook_year() {
        return book_year;
    }

    public void setBook_year(int year) {
        this.book_year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTakeAt() {
        return takeAt;
    }

    public void setTakeAt(Date takeAt) {
        this.takeAt = takeAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
