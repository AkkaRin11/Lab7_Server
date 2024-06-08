package org.example.command;


/**
 *
 * Команда возвращающая историю выполнения команд
 *
 */

public class History extends Command {
    private final org.example.command_support.History history;

    public History() {
        history = org.example.command_support.History.getInstance();

        argSize = 0;
        name = "history";
        description = "Выводит историю команд";
    }

    @Override
    public String execute(Object object, int personId, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }

        var list = history.getCommandHistory();

        String stringAnswer = "";

        for (Command to : list) {
            stringAnswer += to.name;
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
