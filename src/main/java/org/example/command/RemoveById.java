package org.example.command;

import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;

import static org.example.utils.Validation.checkIntNumber;

/**
 *
 * Команда удаляющая элемент по id
 *
 */

public class RemoveById extends Command {
    private final LabWorkService labWorkService;

    public RemoveById() {
        labWorkService = new LabWorkServiceImpl();

        argSize = 1;
        name = "remove_by_id";
        description = "Удаляет элемент по id";
    }

    @Override
    public String execute(Object object, int personId, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        boolean flag;

        if (checkIntNumber(args[0])) {
            flag = labWorkService.removeById(Integer.parseInt(args[0]), personId);
        } else {
            return "Введённый аргумент не является целым числом";
        }

        if (flag) {
            return "Элемент был успешно удалён";
        } else {
            return "Элемент не был удалён";
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
