package ru.ncallie.LibraryCRM.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Поле должно быть заполнено")
    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Min(value = 0, message = "Неверно указан год")
    @Max(value = 9999, message = "Неверно указан год")
    @Column(name = "year")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "person_info_id", referencedColumnName = "id")
    private PersonInfo owner;

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public PersonInfo getOwner() {
        return owner;
    }

    public void setOwner(PersonInfo owner) {
        this.owner = owner;
    }

}
