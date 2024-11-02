package org.example.model.command.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
import org.example.model.command.edit.EditIOTypeCommand;
import org.example.model.html.HTMLDocument;
@Data
@AllArgsConstructor
public class InitCommand implements EditIOTypeCommand {
    //管理的html文档
    private HTMLDocument htmlDocument;
    @Override
    public void undo() {
        System.out.println("IO COMMAND DONT SUPPORT <undo>");
    }

    @Override
    public void execute() {
        htmlDocument.init();
    }

}
