package org.example.controller;

import lombok.Getter;

import java.io.File;
import java.util.Scanner;

/**
 *
 * Класс для отслеживания текущего состояния программы
 *
 */

@Getter
public class ProgramStateController {

    private static ProgramStateController instance;
    private String fileName = "";
    private Scanner scanner;
    private boolean isFileValid;
    private boolean isFileDev;

    private ProgramStateController() {
        isFileValid = true;
        isFileDev = false;
    }

    public static ProgramStateController getInstance() {
        if (instance == null) {
            instance = new ProgramStateController();
        }

        return instance;
    }

    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }

    public boolean getIsFileValid(){
        return isFileValid;
    }
    public void setIsFileValid(boolean isFileValid){
        this.isFileValid = isFileValid;
    }

    public boolean getIsFileDev(){
        return isFileDev;
    }
    public void setIsFileDev(boolean isFileDev){
        this.isFileDev = isFileDev;
    }

    public boolean setFileName(String fileName) {
        File file;

        try {
            file = new File(fileName);
        } catch (Exception e) {
            System.out.println("Файл не существет, проверьте правильность имени и попробуйте ещё");
            return false;
        }

        if (!file.exists()) {
            System.out.println("Файл не существет, проверьте правильность имени и попробуйте ещё");
            return false;
        }

        this.fileName = fileName;

        return true;
    }
}
