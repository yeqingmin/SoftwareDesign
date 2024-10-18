package org.example.model.command.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
@Data
@AllArgsConstructor
public class EditIdCommand implements Command {
//    edit-id oldId newId
//    oldId 为现有元素的 id。
    private String oldId;
//    newId 为新 id，注意 id 不能与其他元素重复。
    private String newId;
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
