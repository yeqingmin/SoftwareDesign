package org.example.model.command;

import org.example.model.command.edit.EditIOTypeCommand;
import org.example.model.command.io.InitCommand;
import org.example.model.command.io.ReadCommand;
import org.example.model.command.io.SaveCommand;
import org.example.model.html.HTMLDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandInvokerRedoTest {
    @Mock
    private HTMLDocument receiver;

    @InjectMocks
    private CommandInvoker commandInvoker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void redo_NoCommandCanBeRedone_ShouldPrintMessage() {
        // Given an empty redo stack
        commandInvoker.getRedoStack().clear();

        // When redo is called
        commandInvoker.redo();

        // Then "NO COMMAND CAN BE REDONE" should be printed
    }

    @Test
    void redo_EditIOTypeCommand_ShouldExecuteCommandAndPushToUndoStack() {
        // Given a mock EditIOTypeCommand
        EditIOTypeCommand mockCommand = mock(EditIOTypeCommand.class);

        // When mockCommand is pushed to redo stack
        commandInvoker.getRedoStack().push(mockCommand);

        // And redo is called
        commandInvoker.redo();

        // Then mockCommand should be executed and added to undo stack
        Assertions.assertEquals(mockCommand, commandInvoker.getUndoStack().pop());
    }

    @Test
    void redo_IOTypeCommand_ShouldNotSupportRedo() {
        // Given a mock IOTypeCommand
        ReadCommand mockCommand = mock(ReadCommand.class);
        InitCommand mockCommand2 = mock(InitCommand.class);
        SaveCommand mockCommand3 = mock(SaveCommand.class);

        //应该输出三个警告
        // When mockCommand is pushed to redo stack
        commandInvoker.getRedoStack().push(mockCommand);

        // And redo is called
        commandInvoker.redo();
        commandInvoker.getRedoStack().push(mockCommand2);
        commandInvoker.redo();
        commandInvoker.getRedoStack().push(mockCommand3);
        commandInvoker.redo();

        // Then "IO Command do not support redo!" should be printed
    }
}
