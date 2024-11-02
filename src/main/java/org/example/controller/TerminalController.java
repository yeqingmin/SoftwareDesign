package org.example.controller;

import org.example.model.command.CommandInvoker;
import org.example.utils.CommandParser;

import java.util.Scanner;

/**
 * 该类主要是为了实现整合几个内容，实现到App的接口，App直接调用他的方法就可以运行了
 */
public class TerminalController {
    public TerminalController(){
        CommandInvoker invoker=new CommandInvoker();
        //执行所有的内容，命令行编辑的入口
        System.out.println("Start HTML Editor!");
        while(true){
            System.out.print(">");
            Scanner input=new Scanner(System.in);
            String terminalCommand=input.nextLine();
            invoker.setCommand(terminalCommand);
            invoker.execute();
        }
    }
}
