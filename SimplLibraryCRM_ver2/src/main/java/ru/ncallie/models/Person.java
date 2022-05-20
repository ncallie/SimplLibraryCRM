package ru.ncallie.models;


import javax.validation.constraints.*;

public class Person {
    private int person_id;
    @NotEmpty(message = "Поле ФИО должно быть заполнено")
    @Pattern(regexp = "[А-Я][а-я]{3,20} [А-Я][а-я]{3,20} [А-Я][а-я]{3,20}", message = "Неверный формат")
    private String name;
    @Min(value = 1900, message = "Неверно указан год")
    @Max(value = 9999, message = "Неверно указан год")
    private int birth_year;
    @Email(message = "Email содержит ошибки")
    @NotEmpty(message = "Поле Email должно быть заполнено")
    private String email;
    @NotEmpty(message = "Поле Номер телефона должно быть заполнено")
    @Pattern(regexp = "\\+7\\d{10}", message = "Неверный формат номера телефона, формат:+79999999999")
    private String phone;


    public Person(int person_id, String name, int birth_year, String email, String phone) {
        this.person_id = person_id;
        this.name = name;
        this.phone = phone;
        this.birth_year = birth_year;
        this.email = email;
    }

    public Person() {

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
}
