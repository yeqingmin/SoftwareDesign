package org.example.model.command.io;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
@Data
@AllArgsConstructor
public class ReadCommand implements Command {
//    read filepath

//    filepath 为读取文件的路径名。
    private String filePath;
//    进行必要的异常检测，例如读取的文件不存在。
    @Override
    public void execute() {

    }

    @Override
    public void undo() {
        System.out.println("Command <read> is not allowed to undo!");
    }
}
