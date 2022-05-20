package ru.ncallie.models;

public class Person {
    private int person_id;
    private String name;
    private int birth_year;
    private String email;
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
