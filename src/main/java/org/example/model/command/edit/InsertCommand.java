package org.example.model.command.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
import org.example.model.html.HTMLDocument;
import org.example.model.html.HTMLTag;

@Data
@AllArgsConstructor
public class InsertCommand implements EditIOTypeCommand {
//    命令格式：insert tagName idValue insertLocation [textContent]
//    tagName 为新元素的元素标签。
    private String tagName;
//    idValue 为新元素的 id，注意 id 不能与其他元素重复。
    private String idValue;
//    insertLocation 为插入位置元素的 id，新元素将插入到该元素之前。
    private String insertLocationId;
//    textContent 为可选参数，表示新元素中的文本内容。
    private String textContent;
    //管理的html文档
    private HTMLDocument htmlDocument;
    @Override
    public void execute() {
        //先找到insertLocationId对应的tag
        htmlDocument.insert(tagName, idValue, insertLocationId, textContent);
    }

    @Override
    public void undo() {
        htmlDocument.delete(idValue);
    }
}
