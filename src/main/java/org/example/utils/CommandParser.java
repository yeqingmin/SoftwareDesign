package org.example.utils;

import org.example.model.command.Command;
import org.example.model.command.display.PrintIndentCommand;
import org.example.model.command.display.PrintTreeCommand;
import org.example.model.command.display.SpellCheckCommand;
import org.example.model.command.edit.*;
import org.example.model.command.io.InitCommand;
import org.example.model.command.io.ReadCommand;
import org.example.model.command.io.SaveCommand;
import org.example.model.html.HTMLDocument;

public class CommandParser {
    /**
     *
     * @param commandStr 需要解析的命令行指令
     * @param receiver 所有指令要统一管理的接收者html文档对象，这里解析指令并进行receiver的依赖注入
     * @return 返回解析后的指令
     */
    public static Command parse(String commandStr, HTMLDocument receiver){
        Command command=null;
        String[] commandDetails =commandStr.split("\\s+");
        switch (commandDetails[0]){
            case "insert":
                if(commandDetails.length==5) {
                    command = new InsertCommand(commandDetails[1], commandDetails[2], commandDetails[3], commandDetails[4],receiver);
                }else if(commandDetails.length==4){
                    //如果可选选项为空，则默认为空字符串
                    command = new InsertCommand(commandDetails[1], commandDetails[2], commandDetails[3], "",receiver);
                }else{
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "append":
                if(commandDetails.length==5) {
                    command = new AppendCommand(commandDetails[1], commandDetails[2], commandDetails[3], commandDetails[4],receiver);
                }else if(commandDetails.length==4){
                    //如果可选选项为空，则默认为空字符串
                    command = new AppendCommand(commandDetails[1], commandDetails[2], commandDetails[3], "",receiver);
                }else{
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "edit-id":
                if(commandDetails.length==3) {
                    command = new EditIdCommand(commandDetails[1], commandDetails[2],receiver);
                }else{
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "edit-text":
                if(commandDetails.length==3) {
                    command = new EditTextCommand(commandDetails[1], commandDetails[2],receiver);
                }else if(commandDetails.length==2){
                   command = new EditTextCommand(commandDetails[1], "",receiver);
                } else {
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "delete":
                if(commandDetails.length==2) {
                    command = new DeleteCommand(commandDetails[1],receiver);
                }else {
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "print-indent":
                if(commandDetails.length==2){
                    command=new PrintIndentCommand(Integer.parseInt(commandDetails[1]),receiver);
                }else if(commandDetails.length==1){
                    command=new PrintIndentCommand(Constant.DEFAULT_INDENT,receiver);
                }
                break;
            case "print-tree":
                command=new PrintTreeCommand(receiver);
                break;
            case "spell-check":
                command=new SpellCheckCommand(receiver);
                break;
            case "read":
                if(commandDetails.length==2) {
                    command = new ReadCommand(commandDetails[1],receiver);
                }else {
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "save":
                if(commandDetails.length==2) {
                    command = new SaveCommand(commandDetails[1],receiver);
                }else{
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "init":
                command=new InitCommand(receiver);
                break;
            case "undo":
            case "redo":
                //放在invoker处理
                break;
            default:
                System.out.println("[ERROR]command format error");
                break;
        }
        return command;
    }
}
