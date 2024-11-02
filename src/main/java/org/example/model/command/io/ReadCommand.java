package org.example.model.command.io;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
import org.example.model.command.edit.EditIOTypeCommand;
import org.example.model.html.HTMLDocument;

@Data
@AllArgsConstructor
public class ReadCommand implements EditIOTypeCommand {
//    read filepath

//    filepath 为读取文件的路径名。
    private String filePath;
    //管理的html文档
    private HTMLDocument htmlDocument;


    @Override
    public void undo() {
        System.out.println("IO COMMAND DONT SUPPORT <undo>");
    }

    //进行必要的异常检测，例如读取的文件不存在。
    @Override
    public void execute() {
        htmlDocument.read(filePath);
    }
}
