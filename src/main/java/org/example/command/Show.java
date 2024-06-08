package org.example.command;

import org.example.model.LabWork;
import org.example.service.LabWorkService;
import org.example.service.LabWorkServiceImpl;

import java.util.LinkedHashSet;

/**
 *
 * Команда вывода всех элементов коллекции
 *
 */

public class Show extends Command {
    private final LabWorkService labWorkService;

    public Show() {
        labWorkService = new LabWorkServiceImpl();

        argSize = 0;
        name = "show";
        description = "Выводит все элементы коллекции";
    }

    @Override
    public String execute(Object object, int personId, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        String stringCollection = "";

        LinkedHashSet<LabWork> collection = labWorkService.getCollection();

        if (collection.isEmpty()){
            return "Коллекция пуста";
        }

        for (LabWork labWork: collection){
            stringCollection += labWork;
        }

        return stringCollection;
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
