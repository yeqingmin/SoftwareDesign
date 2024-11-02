package org.example.model.html;

import java.util.*;
import java.math.*;

import org.example.model.exception.HTMLParseException;
import org.example.model.printer.IndentPrinter;
import org.example.model.printer.Printer;
import org.example.utils.HTMLParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class HTMLDocumentInitTest {

    private HTMLDocument document;
    private Printer<HTMLTag> printer;

    @BeforeEach
    public void setUp() throws IOException, HTMLParseException {
        String html= HTMLParser.readHTMLFile("src/test/resources/testfile/test_one.html");
        document= HTMLParser.parseHTML(html);
        printer= new IndentPrinter(4);
    }

    @Test
    public void initTest() {
        document.init();
        System.out.println(printer.format(document.getRoot()));
    }

    @Test
    public void initTest_NormalCase() {
        document.init();
        // Check if root is not null after init
        assertNotNull(document.getRoot(), "Root should not be null after init");
        // Check if id2Tag map is not null and has entries after init
        Map<String, HTMLTag> id2TagMap = document.getId2Tag();
        assertNotNull(id2TagMap, "id2Tag map should not be null after init");
        assertTrue(id2TagMap.size() > 0, "id2Tag map should have entries after init");
    }
}
