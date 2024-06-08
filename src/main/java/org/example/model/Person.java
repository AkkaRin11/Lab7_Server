package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
    private String name; //Полe нe может быть null, Cтрoкa нe может быть nycтой
    private Date birthday; //Поле может быть null
    private long height; //Значение поля должно быть больше 0
    private Color hairColor; //Поле нe может быть null
    private Country nationality; //Поле может быть null

}
