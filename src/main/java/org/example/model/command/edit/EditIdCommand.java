package org.example.model.command.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
import org.example.model.html.HTMLDocument;

@Data
@AllArgsConstructor
public class EditIdCommand implements Command , EditIOTypeCommand {
//    edit-id oldId newId
//    oldId 为现有元素的 id。
    private String oldId;
//    newId 为新 id，注意 id 不能与其他元素重复。
    private String newId;
    //管理的html文档
    private HTMLDocument htmlDocument;
    @Override
    public void execute() {
        htmlDocument.editId(oldId, newId);
    }

    @Override
    public void undo() {
        htmlDocument.editId(newId,oldId);
    }
}
