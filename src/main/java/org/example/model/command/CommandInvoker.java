package org.example.model.command;

import lombok.Data;
import org.example.model.command.edit.EditIOTypeCommand;
import org.example.model.command.io.InitCommand;
import org.example.model.command.io.ReadCommand;
import org.example.model.command.io.SaveCommand;
import org.example.model.html.HTMLDocument;
import org.example.utils.CommandParser;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;
@Data
public class CommandInvoker {
    //undo和redo应该是实现在这里，使用两个栈来实现
    private String commandStr;
    /**
     * 注意，只有编辑类指令支持undo，redo操作，后面redo和undo方法的错误判断也根据是否是instance of EditTypeCommand来进行错误判断
     */
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
    //Receiver：也就是命令施展的对象
    private HTMLDocument receiver=new HTMLDocument();
    public CommandInvoker(){}
    private Command command = null;

    public void setCommand(@NotNull String commandStr){
        this.commandStr=commandStr;
        if(!commandStr.equals("undo")&!commandStr.equals("redo")){
            this.command= CommandParser.parse(commandStr,receiver);
        }
    }

    /**
     * 执行代码
     */
    public void execute(){
        //因为undo和redo需要对其他的command进行统一管理，所以不适合自令为一个类，就在execute处做if-else嵌套,错误处理什么不在这里做，在undo和redo方法里做
        if(commandStr.equals("redo")){
            this.redo();
        }else if(commandStr.equals("undo")){
            this.undo();
        }else if(command!=null){
            command.execute();
            //执行后加入undo栈
            undoStack.push(command);
            //一旦有了新的edit类型的指令就不支持redo了
            if(command instanceof EditIOTypeCommand){
                redoStack.clear();
            }
        }else{
            System.out.println("COMMAND FAULT ERROR");
        }
    }
    public void undo(){
        if(undoStack.empty()){
            System.out.println("NO COMMAND CAN BE UNDONE");
        }else{
            //不断pop，直到pop出了editIOTypeCommand
            Command command = undoStack.pop();
            while(!(command instanceof EditIOTypeCommand)&&!undoStack.empty()){
                command=undoStack.pop();
            }
            // 只有editTypeCommand才支持undo，否则跳过,(虽然只有edit类型支持undo，
            // 但是为了保证执行IO类型指令的时候不会跳过它导致在他前面的edit类指令被压入压出栈导致问题，而且可以打印警告，
            // 所以这两类型都有undo方法
            if(command instanceof EditIOTypeCommand){
                EditIOTypeCommand editTypeCommand = (EditIOTypeCommand) command;
                editTypeCommand.undo();
                redoStack.push(command);
            }else {
                System.out.println("NO COMMAND CAN BE UNDONE");
            }
        }
    }
    public void redo(){
        if(redoStack.empty()){
            System.out.println("NO COMMAND CAN BE REDONE");
        }else{
            Command command = redoStack.pop();
            //redo栈里面只可能有编辑类指令，所以不用不停地pop
            if(command instanceof EditIOTypeCommand){
                EditIOTypeCommand editTypeCommand=(EditIOTypeCommand) command;
                if(command instanceof ReadCommand||command instanceof InitCommand||command instanceof SaveCommand){
                    System.out.println("IO Command do not support redo!");
                }else{
                    editTypeCommand.execute();
                    undoStack.push(command);
                }
            }
        }
    }
}
