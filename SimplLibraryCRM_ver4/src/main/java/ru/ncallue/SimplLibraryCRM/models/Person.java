package ru.ncallue.SimplLibraryCRM.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;

    @NotEmpty(message = "Поле ФИО должно быть заполнено")
    @Pattern(regexp = "[А-Я][а-я]{3,20} [А-Я][а-я]{3,20} [А-Я][а-я]{3,20}", message = "Неверный формат")
    @Column(name = "name")
    private String name;

    @Min(value = 1900, message = "Неверно указан год")
    @Max(value = 9999, message = "Неверно указан год")
    @Column(name = "birth_year")
    private int birth_year;

    @Email(message = "Email содержит ошибки")
    @NotEmpty(message = "Поле Email должно быть заполнено")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Поле Номер телефона должно быть заполнено")
    @Pattern(regexp = "\\+7\\d{10}", message = "Неверный формат номера телефона, формат:+79999999999")
    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    public Person(String name, int birth_year, String email, String phone) {
        this.name = name;
        this.phone = phone;
        this.birth_year = birth_year;
        this.email = email;
    }



    public Person() {

    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addBook(Book book) {
        if (books == null)
            books = new ArrayList<>();
        books.add(book);
    }
}