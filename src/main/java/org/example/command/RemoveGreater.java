package org.example.command;

import org.example.model.LabWork;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;

/**
 *
 * Команда удаляющая все элементы больше данного
 *
 */

public class RemoveGreater extends Command {
    private final LabWorkService labWorkService;
    public RemoveGreater() {
        labWorkService = new LabWorkServiceImpl();

        argSize = 0;
        name = "remove_greater";
        description = "Удаляет все элементы элементы из коллекции, перевыщающие заданный";
    }

    @Override
    public String execute(Object object, int personId, String... args) {
        if (!isSizeCorrect(args.length)) {
            return"Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        if (object == null){
            return "Неверные аргументы";
        }

        LabWork labWork = (LabWork) object;

        boolean flag = labWorkService.removeGreater(labWork, personId);

        if (flag){
            return "Подходящие по условию объекты были удалены";
        } else {
            return "Подходящие по условию объекты не были найдены";
        }

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
