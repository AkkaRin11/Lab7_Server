package org.example.command;

import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;

/**
 *
 * Командадля отчистки коллекции
 *
 */

public class Clear extends Command {
    private final LabWorkService labWorkService;

    public Clear() {
        labWorkService = new LabWorkServiceImpl();

        argSize = 0;
        name = "clear";
        description = "Очищает колекцию";
    }

    @Override
    public String execute(Object object, int personId, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        labWorkService.clear();

        return "Коллекция успешно очищена";
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
