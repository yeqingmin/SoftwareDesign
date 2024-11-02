package org.example.model.command;

import org.junit.jupiter.api.Test;

public class CommandInvokerTest {
    @Test
    public void setCommandTest(){
        CommandInvoker commandInvoker = new CommandInvoker();
        commandInvoker.setCommand("redo");
        System.out.println(commandInvoker.getCommand());
        commandInvoker.setCommandStr("undo");
        System.out.println(commandInvoker.getCommand());
        commandInvoker.setCommand("append id parent hello");
        System.out.println(commandInvoker.getCommand());
        commandInvoker.setCommand("print-indent 4");
        System.out.println(commandInvoker.getCommand());

    }
}
