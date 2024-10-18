package org.example.model.command.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
@Data
@AllArgsConstructor
public class EditTextCommand implements Command {
//    edit-text element [newTextContent]
//    element 为要编辑元素的 id。
    private String elementId;
//    newTextContent 为新的文本内容，可以为空，表示清空该元素的文本内容。
    private String newTextContent;
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
