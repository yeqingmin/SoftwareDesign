package org.example.model.command;

public class CommandInvoker {
    //undo和redo应该是实现在这里，使用两个栈来实现
    private String commandStr;
    public CommandInvoker(){}
    private Command command;

    /**
     * 构造方法
     * @param command 接收到的命令行参数
     */
    public CommandInvoker(String command){
        this.commandStr=command;
    }
    private void setCommand(){
        String[] commandDetails=commandStr.split("\\s+");
//        if()
    }
}
