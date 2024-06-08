package org.example.utils;

import org.example.model.*;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.example.utils.Validation.checkIntNumber;

public class ObjectUtils {
    public static <T extends Enum<T>> T readEnum(Class<T> enumClass, String line) {

        T a = null;

        try {

            if (checkIntNumber(line)) {
                int enumNumber = Integer.parseInt(line);

                if (enumNumber > 0 && enumNumber <= enumClass.getEnumConstants().length) {
                    a = enumClass.getEnumConstants()[enumNumber - 1];
                }

            } else {
                a = T.valueOf(enumClass, line.toUpperCase());
            }
        } catch (Exception e){
            a = enumClass.getEnumConstants()[0];
        }

        return a;
    }
    public static LabWork readObjectFromList(List<String> lines){

        LabWork labWork = new LabWork((int) (Math.random() * Integer.MAX_VALUE),  null, null, null, -1, null, null, null);

        labWork.setName(lines.get(1));

        Coordinates coordinatesDto = new Coordinates(null, null);

        Double a = Double.valueOf(lines.get(2));

        if (a < 365) {
            coordinatesDto.setX(a);
        } else {
            return null;
        }

        Long b = Long.valueOf(lines.get(3));


        if (b > -592) {
            coordinatesDto.setY(b);
        } else {
            return null;
        }

        labWork.setCoordinates(coordinatesDto);


        Long c = Long.valueOf(lines.get(4));

        if (c > 0) {
            labWork.setMinimalPoint(c);
        } else {
            return null;
        }


        Long d = Long.valueOf(lines.get(5));

        if (d > 0) {
            labWork.setAveragePoint(d);
        } else {
            return null;
        }


        labWork.setDifficulty(readEnum(Difficulty.class, lines.get(6)));


        Person personDto = new Person(null, null, -1, null, null);


        personDto.setName(lines.get(7));


        String line = lines.get(8);
        System.out.println(line);
        String[] str = line.split("\\s+");

        if (str.length != 3) {
            return null;
        }

        Integer e = 0;
        if (checkIntNumber(str[0])) {

            try {
                e = Integer.parseInt(str[0]);
            } catch (Exception ignored) {
                return null;
            }

        } else {
            return null;
        }

        Integer f = 0;
        if (checkIntNumber(str[0])) {

            try {
                f = Integer.parseInt(str[0]);
            } catch (Exception ignored) {
                return null;
            }

        } else {
            return null;
        }

        Integer g = 0;
        if (checkIntNumber(str[0])) {

            try {
                g = Integer.parseInt(str[0]);
            } catch (Exception ignored) {
                return null;
            }

        } else {
            return null;
        }

        Date date;

        try {
            Calendar calendar = new GregorianCalendar(e, f - 1, g);
            date = calendar.getTime();
        } catch (Exception exception) {
            return null;
        }


        personDto.setBirthday(date);


        Long q = Long.valueOf(lines.get(9));


        if (q > 0) {
            personDto.setHeight(q);
        } else {
            return null;
        }

        personDto.setHairColor(readEnum(Color.class, lines.get(10)));

        personDto.setNationality(readEnum(Country.class, lines.get(11)));

        labWork.setAuthor(personDto);

        labWork.setCreationDate(LocalDateTime.now());

        return labWork;
    }
}
