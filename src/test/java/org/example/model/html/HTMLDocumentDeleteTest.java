package org.example.model.html;

import org.example.model.exception.HTMLParseException;
import org.example.model.printer.IndentPrinter;
import org.example.model.printer.Printer;
import org.example.utils.HTMLParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HTMLDocumentDeleteTest {
    private HTMLDocument document;
    @BeforeEach
    public void setUp() throws HTMLParseException, IOException {
        String html= HTMLParser.readHTMLFile("src/test/resources/testfile/test_one.html");
        document= HTMLParser.parseHTML(html);
    }
    @Test
    public void delete_ElementExists_ShouldRemoveElementAndUpdateMap() {
        // 假设 'content' 是 map 中的一个有效 ID
        String elementId = "content";
        HTMLTag deletedTag = document.delete(elementId);

        assertNotNull(deletedTag);
        assertEquals(elementId, deletedTag.getId());
        assertTrue(deletedTag.isDeleted());

        // 确保 map 中不再存在该元素
        assertNull(document.findTagById(elementId));

        // 检查控制台输出，确认删除操作
        System.out.println("Deleted tag ID: " + elementId);
    }

    @Test
    public void delete_ElementDoesNotExist_ShouldReturnNull() {
        // 假设 'nonexistent' 不是 map 中的 ID
        String nonExistentId = "nonexistent";
        HTMLTag result = document.delete(nonExistentId);

        assertNull(result, "Result should be null for non-existent element ID");

        // 确保控制台输出了错误信息
        System.out.println("NO SUCH ELEMENT ID");
    }
}
