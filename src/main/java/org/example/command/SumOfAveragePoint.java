package org.example.command;

import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;

/**
 *
 * Команда возвращающая сумму average_point
 *
 */
public class SumOfAveragePoint extends Command {
    private final LabWorkService labWorkService;

    public SumOfAveragePoint() {
        labWorkService = new LabWorkServiceImpl();

        argSize = 0;
        name = "sum_of_average_point";
        description = "Вывести сумму всех полей averagePoint в коллекции";
    }

    @Override
    public String execute(Object object, int personId, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }
        long sum = labWorkService.getSumOfAveragePoint();

        return "Сумма averagePoint в массиве: " + sum;
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
