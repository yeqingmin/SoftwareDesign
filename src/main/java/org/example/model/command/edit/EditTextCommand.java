package org.example.model.command.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
import org.example.model.html.HTMLDocument;

@Data
@AllArgsConstructor
public class EditTextCommand implements Command, EditIOTypeCommand {
//    edit-text element [newTextContent]
//    element 为要编辑元素的 id。
    private String elementId;
//    newTextContent 为新的文本内容，可以为空，表示清空该元素的文本内容。
    private String newTextContent;
    //管理的html文档
    private HTMLDocument htmlDocument;
    //返回的旧文本内容
    private String oldText=null;

    public EditTextCommand(String elementId, String newTextContent, HTMLDocument htmlDocument) {
        this.elementId = elementId;
        this.newTextContent = newTextContent;
        this.htmlDocument = htmlDocument;
    }

    @Override
    public void execute() {
        this.oldText= htmlDocument.editText(elementId,newTextContent);
    }

    @Override
    public void undo() {
        this.oldText= htmlDocument.editText(elementId,oldText);
    }
}
