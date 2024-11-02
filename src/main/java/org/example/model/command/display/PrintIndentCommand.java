package org.example.model.command.display;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
import org.example.model.html.HTMLDocument;

@Data
@AllArgsConstructor
public class PrintIndentCommand implements Command {
//    print-indent 按缩进格式显示
//    print-indent [indent]
    private int indent;
    private HTMLDocument htmlDocument;
//    indent 为可选参数，表示每级缩进的空格数，默认为 2。当提供 indent 时，使用指定的空格数进行缩进显示。
    @Override
    public void execute() {
        htmlDocument.printIndent(indent);
    }

}
