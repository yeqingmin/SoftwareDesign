package org.example.model.html;

import org.example.model.exception.HTMLParseException;
import org.example.model.printer.IndentPrinter;
import org.example.model.printer.Printer;
import org.example.utils.HTMLParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HTMLDocumentSaveTest {
    private HTMLDocument document;
    private String html;
    @BeforeEach
    public void setUp() throws HTMLParseException, IOException {
        this.html= HTMLParser.readHTMLFile("src/test/resources/testfile/test_one.html");
        document= HTMLParser.parseHTML(html);
    }
    @Test
    public void save_NullRoot_PrintsNULLHTMLModel() {
        document.setRoot(null);  // 设置根节点为null
        String directoryPath = "src/test/resources/output";
        //应该输出NULL HTML MODEL不保存
        document.save("E:\\Courses\\SoftwareDesign\\HTMLEditor\\src\\test\\resources\\saved");
    }

    @Test
    public void save_ValidRoot_SavesFile() throws HTMLParseException {
        String directoryPath = "E:\\Courses\\SoftwareDesign\\HTMLEditor\\src\\test\\resources\\saved";
        document= HTMLParser.parseHTML(html);
        document.save(directoryPath);
    }

    @Test
    public void invalidPath_SavesFile() throws HTMLParseException {
        String directoryPath = "M:\\jsadfhisdhfpa/\\89有n。\\...........";
        document= HTMLParser.parseHTML(html);
        //应该报错
        document.save(directoryPath);
    }
}
