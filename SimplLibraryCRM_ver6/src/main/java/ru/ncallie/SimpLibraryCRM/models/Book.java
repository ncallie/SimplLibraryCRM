package ru.ncallie.SimpLibraryCRM.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
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
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_info_id", referencedColumnName = "id")
    private PersonInfo owner;
}
