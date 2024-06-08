package org.example.command;

import org.example.model.LabWork;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;

/**
 *
 * Команда добавлющая элемент
 *
 */

public class Add extends Command {
    private final LabWorkService labWorkService;

    public Add() {
        labWorkService = new LabWorkServiceImpl();

        argSize = 0;
        name = "add";
        description = "Добавляет элемент в колекцию";
    }

    @Override
    public String execute(Object object, int personId, String... args) {

        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        if (object == null){
            return "Неверные аргументы";
        }

        LabWork lb = (LabWork) object;


        labWorkService.add(lb, personId);

        return "Коллекция успешно добавлена";

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
