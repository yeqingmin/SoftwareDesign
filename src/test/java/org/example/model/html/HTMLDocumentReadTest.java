package org.example.model.html;

import org.example.model.exception.HTMLParseException;
import org.example.model.printer.IndentPrinter;
import org.example.model.printer.Printer;
import org.example.utils.HTMLParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

public class HTMLDocumentReadTest {
    private HTMLDocument document;
    private Printer<HTMLTag> printer;
    @BeforeEach
    public void setUp() throws HTMLParseException, IOException {
        String html= HTMLParser.readHTMLFile("src/test/resources/testfile/test_one.html");
        document= HTMLParser.parseHTML(html);
        printer= new IndentPrinter(4);
    }
    @Test
    void read_ValidFilePath_ShouldParseHTMLCorrectly() throws IOException {
        String testFilePath = "src/test/resources/testfile/test_one.html";
        HTMLDocument document = new HTMLDocument();
        document.read(testFilePath);

        assertNotNull(document.getRoot(), "Root element should not be null");
        assertFalse(document.getId2Tag().isEmpty(), "ID to Tag map should not be empty");
    }

    @Test
    void read_InvalidFilePath_ShouldHandleIOException() {
        String invalidFilePath = "src/test/resources/testfile/non_existent_file.html";
        HTMLDocument document = new HTMLDocument();
        //应该输出报错
        document.read(invalidFilePath);
    }
}
