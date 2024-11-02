package org.example.model.html;

import org.example.model.exception.HTMLParseException;
import org.example.model.exception.HTMLTypeException;
import org.example.model.printer.IndentPrinter;
import org.example.model.printer.Printer;
import org.example.utils.HTMLParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertThrows;

public class HTMLDocumentAppendTest {
    private HTMLDocument document;
    private Printer<HTMLTag> printer;
    @BeforeEach
    public void setUp() throws HTMLParseException, IOException {
        String html= HTMLParser.readHTMLFile("src/test/resources/testfile/test_one.html");
        document= HTMLParser.parseHTML(html);
        printer= new IndentPrinter(4);
    }
    @Test
    public void findTagByIdTest(){
        HTMLTag htmlTag = document.findTagById("content");
        System.out.println(htmlTag.getTagName());
        System.out.println(htmlTag.getText());
    }

    @Test
    public void appendTest_ErrorCase(){
        assertThrows(IllegalArgumentException.class, () -> document.append("div", "head", "body", "new paragraph"));
    }
    @Test
    public void appendTest_NormalCase() throws HTMLTypeException {
        document.append("div", "ababab", "body", "new paragraph");
        System.out.println(printer.format(document.getRoot()));
    }

}
