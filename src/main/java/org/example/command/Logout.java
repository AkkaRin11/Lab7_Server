package org.example.command;

import org.example.service.LabWorkServiceImpl;

public class Logout extends Command{

    public Logout() {
        argSize = 0;
        name = "logout";
        description = "Выходит из акаунта";
    }

    @Override
    public String execute(Object object, int personId, String... args) {
        if (!isSizeCorrect(args.length)) {
            return "Неверное количество аргументов, ожидалось: " + argSize +
                    ", получено: " + args.length;
        }


        return "";
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
