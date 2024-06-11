package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabWork implements Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть большe 0, Значение этого поля должно быть уникальным
    private String name; //Поле не может быть null, Cтрoка нe может быть nyстой
    private Coordinates coordinates; //Полe может быть null
    private LocalDateTime creationDate; //Поле может null, Знaчение этого поля должно генерироваться автоматически
    private long minimalPoint; //Значение поля должно быть большe 0
    private Long averagePoint; //Поле может быть null, Знaчение поля должно быть больше 0
    private Difficulty difficulty; //Поле не может быть null
    private Person author; //Полe можeт быть null


    @Override
    public String toString() {
        return "Id: " + getId().toString() +
                "\n" + "Name: " + getName()+
                "\n" + "Coordinates X: " + getCoordinates().getX().toString()+
                "\n" + "Coordinates Y: " + getCoordinates().getY().toString()+
                "\n" + "CreationDate: " + getCreationDate().toString()+
                "\n" + "MinimalPoint: " + getMinimalPoint()+
                "\n" + "AveragePoint: " + getAveragePoint().toString()+
                "\n" + "Difficulty: " + getDifficulty().name()+
                "\n" + "Author Name: " + getAuthor().getName()+
                "\n" + "Author Birthday: " + getAuthor().getBirthday().toString()+
                "\n" + "Author Height: " + getAuthor().getHeight()+
                "\n" + "Author HairColor: " + getAuthor().getHairColor().name()+
                "\n" + "Author Nationality: " + getAuthor().getNationality().name() + "\n";
    }
}
