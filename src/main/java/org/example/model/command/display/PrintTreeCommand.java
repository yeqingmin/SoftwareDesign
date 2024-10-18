package org.example.model.command.display;

import org.example.model.command.Command;

public class PrintTreeCommand implements Command {
    @Override
    public void execute() {

    }

    @Override
    public void undo() {
        //显示类指令undo不实现，函数体为空
    }
}
