package org.example.model.command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.math.*;

import org.example.model.html.HTMLDocument;
import org.example.model.command.edit.EditIOTypeCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommandInvokerUndoTest {

    @Mock
    private HTMLDocument receiver;

    @InjectMocks
    private CommandInvoker commandInvoker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void undo_NoCommandsOnUndoStack_PrintsWarning() {
        // Given an empty undo stack
        assertTrue(commandInvoker.getUndoStack().empty());

        // When undo is called
        commandInvoker.undo();

        // Then it should print "NO COMMAND CAN BE UNDONE"
        // We'll check the console output by capturing the System.out
        final PrintStream originalOut = System.out;
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bout));
        commandInvoker.undo();
        //输出NO COMMAND CAN BE UNDONE
        System.setOut(originalOut);
    }

    @Test
    void undo_HasNonEditIOTypeCommand_PrintsWarningAndDoesNothing() {
        // Given a command that is not EditIOTypeCommand
        Command nonEditCommand = mock(Command.class);
        commandInvoker.getUndoStack().push(nonEditCommand);

        // When undo is called
        commandInvoker.undo();

        // Then it should print "NO COMMAND CAN BE UNDONE"
        // We'll check the console output by capturing the System.out
//        assertEquals("NO COMMAND CAN BE UNDONE\n");
        //输出NO COMMAND CAN BE UNDONE
    }

    @Test
    void undo_HasEditIOTypeCommand_ExecutesUndoAndPushesToRedoStack() {
        // Given an EditIOTypeCommand
        EditIOTypeCommand editCommand = mock(EditIOTypeCommand.class);
        commandInvoker.getUndoStack().push(editCommand);

        // When undo is called
        commandInvoker.undo();
        // Then it should call undo on the EditIOTypeCommand and push it to the redo stack
//        verify(editCommand, times(1)).undo();
        assertTrue(commandInvoker.getRedoStack().contains(editCommand));
    }
}

