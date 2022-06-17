package ru.ncallie.LibraryCRM.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "person_info")
public class PersonInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Поле ФИО должно быть заполнено")
    @Pattern(regexp = "[А-Я][а-я]{3,20} [А-Я][а-я]{3,20} [А-Я][а-я]{3,20}", message = "Поле ФИО заполненно не корректно")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 1900, message = "Неверно указан год")
    @Max(value = 9999, message = "Неверно указан год")
    @Column(name = "birth_year")
    private Integer birth_year;

    @Email(message = "Email содержит ошибки")
    @NotEmpty(message = "Поле Email должно быть заполнено")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Поле Номер телефона должно быть заполнено")
    @Pattern(regexp = "\\+7\\d{10}", message = "Неверный формат номера телефона, формат:+79999999999")
    @Column(name = "phone")
    private String phone;

    @OneToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @OneToMany(mappedBy = "owner")
    private List<Book> books = new java.util.ArrayList<>();

    public PersonInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
