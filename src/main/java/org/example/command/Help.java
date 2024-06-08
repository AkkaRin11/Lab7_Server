package org.example.command;


import org.example.controller.CommandController;

import java.util.Map;

/**
 *
 * Команда возвращающая список всех существующих команд
 *
 */

public class Help extends Command {

    public Help() {
        argSize = 0;
        name = "help";
        description = "Выводит все доступные команды с описанием";
    }

    @Override
    public String execute(Object object, int personId, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        String stringAnswer = "";
        for (Map.Entry<String, Command> entry : CommandController.getCommands().entrySet()) {
            stringAnswer += entry.getValue().toString() + "\n";
        }

        return stringAnswer;
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
