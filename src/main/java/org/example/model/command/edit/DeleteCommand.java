package org.example.model.command.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
@Data
@AllArgsConstructor
public class DeleteCommand implements Command {
//    delete element
//    element 为要删除元素的 id。
    private String elementId;
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
