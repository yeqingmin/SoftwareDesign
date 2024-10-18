package org.example.model.command.display;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
@Data
@AllArgsConstructor
public class PrintIndentCommand implements Command {
//    print-indent 按缩进格式显示
//    print-indent [indent]
    private int indent;
//    indent 为可选参数，表示每级缩进的空格数，默认为 2。当提供 indent 时，使用指定的空格数进行缩进显示。
    @Override
    public void execute() {

    }

    @Override
    public void undo() {
        //显示类指令undo不实现，函数体为空
    }
}
