package org.example.model.command.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
import org.example.model.exception.HTMLTypeException;
import org.example.model.html.HTMLDocument;

@Data
@AllArgsConstructor
public class AppendCommand implements Command , EditIOTypeCommand {
//    append tagName idValue parentElement [textContent]
//    tagName 为新元素的元素标签。
    private String tagName;
//    idValue 为新元素的 id，注意 id 不能与其他元素重复。
    private String idValue;
//    parentElement 为目标父元素的 id，新元素将被插入到该元素内部，并且成为该元素的最后一个子元素。
    private String parentElementId;
//    textContent 为可选参数，表示新元素中的文本内容。
    private String textContent;
    //管理的html文档
    private HTMLDocument htmlDocument;
    @Override
    public void execute() {
        try {
            htmlDocument.append(tagName, idValue, parentElementId, textContent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {
        //添加的元素，undo就是删除
        htmlDocument.delete(idValue);
    }
}
