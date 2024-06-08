package org.example.command;

import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;

/**
 *
 * Команда возвращающая информацию о коллекции
 *
 */
public class Info extends Command {
    private final LabWorkService labWorkService;

    public Info() {
        labWorkService = new LabWorkServiceImpl();

        argSize = 0;
        name = "info";
        description = "Выводит информацию о колекции";
    }

    @Override
    public String execute(Object object, int personId, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }


        return labWorkService.getCollectionInfo();
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
