package org.example.utils;

/**
 *
 * Класс отвечающий за проверку валидности строк
 *
 */
public class Validation {
    public static boolean checkIntNumber(String str) {
        return str.matches("^[-+]?\\d+$");
    }

    public static boolean checkDouble(String str) {
        return str.matches("^[0-9]*[.][0-9]+$");
    }


}
