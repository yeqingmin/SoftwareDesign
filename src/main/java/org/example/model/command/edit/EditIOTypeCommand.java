package org.example.model.command.edit;

import org.example.model.command.Command;
import org.example.model.html.HTMLDocument;

public interface EditIOTypeCommand extends Command {
    void undo();

    //所有的操作都操作同一个树，所以要把htmlDocument传进去
    void execute();
}
