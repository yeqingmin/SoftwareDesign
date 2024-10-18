package org.example.utils;

import org.example.model.command.Command;
import org.example.model.command.display.PrintIndentCommand;
import org.example.model.command.display.PrintTreeCommand;
import org.example.model.command.display.SpellCheckCommand;
import org.example.model.command.edit.*;
import org.example.model.command.io.InitCommand;
import org.example.model.command.io.ReadCommand;
import org.example.model.command.io.SaveCommand;

public class CommandParser {
    public static Command parse(String commandStr){
        Command command=null;
        String[] commandDetails =commandStr.split("\\s+");
        switch (commandDetails[0]){
            case "insert":
                if(commandDetails.length==5) {
                    command = new InsertCommand(commandDetails[1], commandDetails[2], commandDetails[3], commandDetails[4]);
                }else if(commandDetails.length==4){
                    //如果可选选项为空，则默认为空字符串
                    command = new InsertCommand(commandDetails[1], commandDetails[2], commandDetails[3], "");
                }else{
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "append":
                if(commandDetails.length==5) {
                    command = new AppendCommand(commandDetails[1], commandDetails[2], commandDetails[3], commandDetails[4]);
                }else if(commandDetails.length==4){
                    //如果可选选项为空，则默认为空字符串
                    command = new AppendCommand(commandDetails[1], commandDetails[2], commandDetails[3], "");
                }else{
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "edit-id":
                if(commandDetails.length==3) {
                    command = new EditIdCommand(commandDetails[1], commandDetails[2]);
                }else{
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "edit-text":
                if(commandDetails.length==3) {
                    command = new EditTextCommand(commandDetails[1], commandDetails[2]);
                }else if(commandDetails.length==2){
                   command = new EditTextCommand(commandDetails[1], "");
                } else {
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "delete":
                if(commandDetails.length==2) {
                    command = new DeleteCommand(commandDetails[1]);
                }else {
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "print-indent":
                if(commandDetails.length==2){
                    command=new PrintIndentCommand(Integer.parseInt(commandDetails[1]));
                }else if(commandDetails.length==1){
                    command=new PrintIndentCommand(Constant.DEFAULT_INDENT);
                }
                break;
            case "print-tree":
                command=new PrintTreeCommand();
                break;
            case "spell-check":
                command=new SpellCheckCommand();
                break;
            case "read":
                if(commandDetails.length==2) {
                    command = new ReadCommand(commandDetails[1]);
                }else {
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "save":
                if(commandDetails.length==2) {
                    command = new SaveCommand(commandDetails[1]);
                }else{
                    System.out.println("[ERROR]command format error");
                }
                break;
            case "init":
                command=new InitCommand();
                break;
            case "undo":
            case "redo":
                //这两个指令交给invoker处理，这里不处理
                break;
            default:
                System.out.println("[ERROR]command format error");
                break;
        }
        return command;
    }
}
