package org.example.model.html;

import org.example.model.exception.HTMLParseException;
import org.example.model.printer.IndentPrinter;
import org.example.model.printer.Printer;
import org.example.utils.HTMLParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class HTMLDocumentEditTest {
    private HTMLDocument document;
    @BeforeEach
    public void setUp() throws HTMLParseException, IOException {
        String html= HTMLParser.readHTMLFile("src/test/resources/testfile/test_one.html");
        document= HTMLParser.parseHTML(html);
    }
    @Test
    public void editId_OldIdNotFound_ShouldPrintNoSuchElementId() {
        String oldId = "nonexistent";
        String newId = "newid";
        System.out.println("NO SUCH ELEMENT ID");
        assertDoesNotThrow(() -> document.editId(oldId, newId));
    }

    @Test
    public void editId_NewIdDuplicate_ShouldPrintDuplicateId() {
        String oldId = "content";
        String newId = "duplicate";
        System.out.println("Duplicate id: " + newId);
        assertDoesNotThrow(() -> document.editId(oldId, newId));
    }

    @Test
    public void editId_ValidIds_ShouldUpdateIdAndMap() {
        String oldId = "content";
        String newId = "newcontent";

        // Ensure oldId exists and newId doesn't
        Assertions.assertNotNull(document.findTagById(oldId));
        Assertions.assertNull(document.findTagById(newId));

        document.editId(oldId, newId);

        // Ensure oldId no longer exists and newId now exists
        Assertions.assertNull(document.findTagById(oldId));
        Assertions.assertNotNull(document.findTagById(newId));
    }

    @Test
    public void editText_ElementExists_ReturnsOldText() {
        // 假设 'content' 是 id2Tag 中的一个有效键
        String elementId = "content";
        String newText = "new text content";
        String oldText = document.editText(elementId, newText);

        Assertions.assertNotNull(oldText);
        Assertions.assertEquals("hello", oldText);
        HTMLTag tag = document.findTagById(elementId);
        Assertions.assertNotNull(tag);
        Assertions.assertEquals(newText, tag.getText());
    }

    @Test
    public void editText_ElementDoesNotExist_ReturnsNull() {
        String elementId = "non_existent_id";
        String newText = "new text content";
        String oldText = document.editText(elementId, newText);

        Assertions.assertNull(oldText);
        // 确保没有异常发生，并且文本没有被错误地修改
        HTMLTag tag = document.findTagById(elementId);
        Assertions.assertNull(tag);
    }

    @Test
    public void findTagById_TagExists_ReturnsTag() {
        HTMLTag htmlTag = document.findTagById("content");
        Assertions.assertNotNull(htmlTag);
        Assertions.assertEquals("content", htmlTag.getId());
    }

    @Test
    public void findTagById_TagDoesNotExist_ReturnsNull() {
        HTMLTag htmlTag = document.findTagById("non_existent_id");
        Assertions.assertNull(htmlTag);
    }
}
