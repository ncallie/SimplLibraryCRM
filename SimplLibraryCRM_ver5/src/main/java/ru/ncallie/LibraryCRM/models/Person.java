package ru.ncallie.LibraryCRM.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 5, max = 20, message = "Имя должно быть длинной от 5 до 20")
    @Pattern(regexp = "\\w+", message = "Логин должен сожержать только латинские буквы, цифры, символы ")
    @Column(name = "username")
    private String username;

    @Size(min = 5, message = "Пароль должен быть длинной от 5 до 18")
    @NotEmpty(message = "Пароль не должен быть пустым")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToOne(mappedBy = "person")
    private PersonInfo personInfo;

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public String getRoleSimp() {
        switch (role) {
            case "ROLE_USER":
                return "Пользователь";
            case "ROLE_STAFF":
                return "Сотрудник";
            case "ROLE_ADMIN":
                return "Администратор";
        }
        return "Неизвестно";
    }
}
