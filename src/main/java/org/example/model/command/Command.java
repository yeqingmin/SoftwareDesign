package org.example.model.command;

public interface Command {
    void execute();
    void undo();
}
