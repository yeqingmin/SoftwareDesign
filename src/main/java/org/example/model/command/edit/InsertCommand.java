package org.example.model.command.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
@Data
@AllArgsConstructor
public class InsertCommand implements Command {
//    命令格式：insert tagName idValue insertLocation [textContent]
//    tagName 为新元素的元素标签。
    private String tagName;
//    idValue 为新元素的 id，注意 id 不能与其他元素重复。
    private String idValue;
//    insertLocation 为插入位置元素的 id，新元素将插入到该元素之前。
    private String insertLocationId;
//    textContent 为可选参数，表示新元素中的文本内容。
    private String textContent;
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
