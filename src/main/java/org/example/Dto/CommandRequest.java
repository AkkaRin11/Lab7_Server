package org.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CommandRequest implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private final String commandName;
    private final String[] commandStringArgument;
    private final Serializable commandObjectArgument;
    private final String userName;
    private String userPass;

    @Override
    public String toString() {
        return commandName + " "
                + commandStringArgument + " "
                + commandObjectArgument;
    }
}