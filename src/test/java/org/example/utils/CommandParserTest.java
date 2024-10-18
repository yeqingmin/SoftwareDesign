package org.example.utils;

import org.example.model.command.Command;
import org.example.model.command.display.PrintIndentCommand;
import org.example.model.command.display.PrintTreeCommand;
import org.example.model.command.display.SpellCheckCommand;
import org.example.model.command.edit.*;
import org.example.model.command.io.InitCommand;
import org.example.model.command.io.ReadCommand;
import org.example.model.command.io.SaveCommand;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.math.*;

import org.example.model.command.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest {

    @InjectMocks
    private CommandParser commandParser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void parse_InsertCommandWithOptionalArgument_ShouldReturnInsertCommand() {
        Command command = CommandParser.parse("insert 1 2 3 4");
        assertTrue(command instanceof InsertCommand);
    }

    @Test
    public void parse_InsertCommandWithoutOptionalArgument_ShouldReturnInsertCommand() {
        Command command = CommandParser.parse("insert 1 2 3");
        assertTrue(command instanceof InsertCommand);
    }

    @Test
    public void parse_AppendCommandWithOptionalArgument_ShouldReturnAppendCommand() {
        Command command = CommandParser.parse("append 1 2 3 4");
        assertTrue(command instanceof AppendCommand);
    }

    @Test
    public void parse_AppendCommandWithoutOptionalArgument_ShouldReturnAppendCommand() {
        Command command = CommandParser.parse("append 1 2 3");
        assertTrue(command instanceof AppendCommand);
    }

    @Test
    public void parse_EditIdCommand_ShouldReturnEditIdCommand() {
        Command command = CommandParser.parse("edit-id 1 2");
        assertTrue(command instanceof EditIdCommand);
    }

    @Test
    public void parse_EditTextCommandWithArgument_ShouldReturnEditTextCommand() {
        Command command = CommandParser.parse("edit-text 1 2");
        assertTrue(command instanceof EditTextCommand);
    }

    @Test
    public void parse_EditTextCommandWithoutArgument_ShouldReturnEditTextCommand() {
        Command command = CommandParser.parse("edit-text 1");
        assertTrue(command instanceof EditTextCommand);
    }

    @Test
    public void parse_DeleteCommand_ShouldReturnDeleteCommand() {
        Command command = CommandParser.parse("delete 1");
        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void parse_PrintIndentCommandWithArgument_ShouldReturnPrintIndentCommand() {
        Command command = CommandParser.parse("print-indent 4");
        assertTrue(command instanceof PrintIndentCommand);
    }

    @Test
    public void parse_PrintIndentCommandWithoutArgument_ShouldReturnPrintIndentCommand() {
        Command command = CommandParser.parse("print-indent");
        assertTrue(command instanceof PrintIndentCommand);
    }

    @Test
    public void parse_PrintTreeCommand_ShouldReturnPrintTreeCommand() {
        Command command = CommandParser.parse("print-tree");
        assertTrue(command instanceof PrintTreeCommand);
    }

    @Test
    public void parse_SpellCheckCommand_ShouldReturnSpellCheckCommand() {
        Command command = CommandParser.parse("spell-check");
        assertTrue(command instanceof SpellCheckCommand);
    }

    @Test
    public void parse_ReadCommand_ShouldReturnReadCommand() {
        Command command = CommandParser.parse("read file.txt");
        assertTrue(command instanceof ReadCommand);
    }

    @Test
    public void parse_SaveCommand_ShouldReturnSaveCommand() {
        Command command = CommandParser.parse("save file.txt");
        assertTrue(command instanceof SaveCommand);
    }

    @Test
    public void parse_InitCommand_ShouldReturnInitCommand() {
        Command command = CommandParser.parse("init");
        assertTrue(command instanceof InitCommand);
    }

    @Test
    public void parse_InvalidCommand_ShouldReturnNull() {
        Command command = CommandParser.parse("invalid");
        assertNull(command);
    }

    @Test
    public void parse_CommandWithIncorrectArguments_ShouldReturnNull() {
        Command command = CommandParser.parse("insert 1 2");
        assertNull(command);
    }
}
