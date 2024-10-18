package org.example.model.command.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.command.Command;
@Data
@AllArgsConstructor
public class SaveCommand implements Command {
//    save filepath

//    filepath 为写入文件的路径名。
    private String filePath;
//    进行必要的异常检测，例如提供的路径名无法写入文件。
    @Override
    public void execute() {

    }

    @Override
    public void undo() {
        System.out.println("Command <save> is not allowed to undo!");
    }
}
