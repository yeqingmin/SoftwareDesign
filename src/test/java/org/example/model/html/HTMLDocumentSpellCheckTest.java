package org.example.model.html;

import org.example.model.printer.IndentPrinter;
import org.example.model.printer.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

public class HTMLDocumentSpellCheckTest {
    private HTMLDocument document;
    private Printer<HTMLTag> printer;
    @BeforeEach
    public void setUp() {
        document = new HTMLDocument();
        printer = new IndentPrinter(2);
    }

    @Test
    public void spellCheck_EmptyHTMLModel_ShouldPrintMessage() {
        document.spellCheck();
        // Assuming the output is captured and tested
        assertTrue(System.out.toString().contains("EMPTY HTML MODEL,PLEASE READ OR INIT"));
    }
}
