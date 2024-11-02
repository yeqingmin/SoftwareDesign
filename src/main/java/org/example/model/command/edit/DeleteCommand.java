package org.example.model.command.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
import org.example.model.html.HTMLDocument;
import org.example.model.html.HTMLTag;

@Data
@AllArgsConstructor
public class DeleteCommand implements Command , EditIOTypeCommand {
//    delete element
//    element 为要删除元素的 id。
    private String elementId;
    //管理的html文档
    private HTMLDocument htmlDocument;
    private HTMLTag deletedTag;

    public DeleteCommand(String elementId, HTMLDocument htmlDocument){
        this.elementId=elementId;
        this.htmlDocument=htmlDocument;
    }
    @Override
    public void execute() {
        this.deletedTag=htmlDocument.delete(elementId);
    }

    @Override
    public void undo() {
        //delete重做比较复杂需要判断到底是append还是insert
        //tagName, idValue, parentElementId, textContent
        try {
            htmlDocument.append(deletedTag.getTagName(),deletedTag.getId(),deletedTag.getParent().getId(),deletedTag.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
