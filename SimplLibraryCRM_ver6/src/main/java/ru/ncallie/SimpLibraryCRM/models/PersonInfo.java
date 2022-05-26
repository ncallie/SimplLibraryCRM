package ru.ncallie.SimpLibraryCRM.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "person_info")
public class PersonInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Поле ФИО должно быть заполнено")
    @Pattern(regexp = "[А-Я][а-я]{3,20} [А-Я][а-я]{3,20} [А-Я][а-я]{3,20}", message = "Неверный формат")
    @Column(name = "full_name")
    private String fullName;

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

    @OneToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

}
