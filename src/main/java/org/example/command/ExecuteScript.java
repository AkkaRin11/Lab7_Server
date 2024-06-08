package org.example.command;

import org.example.controller.CommandController;
import org.example.controller.ProgramStateController;
import org.example.utils.ObjectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 *
 * Команда воплоняющая скрипт по имени
 *
 */
public class ExecuteScript extends Command {
    private final ProgramStateController programStateController;
    private final org.example.command_support.History history;
    private final Set<String> commandWithObject;

    public ExecuteScript() {
        programStateController = ProgramStateController.getInstance();
        history = org.example.command_support.History.getInstance();

        argSize = 0;
        name = "execute_script";
        description = "Выполнить скрипт";

        commandWithObject = new HashSet<>();
        commandWithObject.add("add");
        commandWithObject.add("add_if_max");
        commandWithObject.add("update");
        commandWithObject.add("remove_greater");
    }

    @Override
    public String execute(Object object, int personId, String... args) {

        String answer = "";

        List<String> lines = (List<String>) object;

        for (int i = 0; i < lines.size(); i++) {

            String[] str = lines.get(i).replaceAll("\n", "").split("\\s+");

            String[] ar = new String[str.length - 1];
            System.arraycopy(str, 1, ar, 0, str.length - 1);

            Object paramObject = null;

            if (commandWithObject.contains(str[0])){

                int it = 0;
                List<String> paramLines = new ArrayList<>();

                while (it <= 11 && i < lines.size()){
                    String string = lines.get(i);
                    paramLines.add(string);

                    it++;
                    i++;
                }

                if (it == 12){
                    paramObject = ObjectUtils.readObjectFromList(paramLines);
                }

                i--;
            }

            if (CommandController.isValidCommand(str[0])){
                answer += CommandController.getCommandByName(str[0]).execute(paramObject, personId, ar) + "\n";
            }

        }

        return "Скрипт завершил своё выполнение:\n" + answer;
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
