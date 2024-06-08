package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates implements Serializable {
    private Double x; //Максимальное значение поля: 365, Поле не может быть null
    private Long y; //Значение поля должно быть больше -592, Поле не может быть null
}
