package org.example.command;

public interface Executable {
    String execute(Object object, int personId, String... args);
}
