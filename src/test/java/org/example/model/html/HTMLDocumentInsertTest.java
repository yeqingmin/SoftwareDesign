package org.example.model.html;

import org.example.model.exception.HTMLParseException;
import org.example.model.printer.IndentPrinter;
import org.example.model.printer.Printer;
import org.example.utils.HTMLParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class HTMLDocumentInsertTest {
    private HTMLDocument document;
    @BeforeEach
    public void setUp() throws HTMLParseException, IOException {
        String html= HTMLParser.readHTMLFile("src/test/resources/testfile/test_one.html");
        this.document= HTMLParser.parseHTML(html);
    }
    @Test
    public void insert_NormalCase_TokenTag() throws HTMLParseException, IOException {
        // Arrange
        String tagName = "br";
        String idValue = "newBreak";
        String insertLocationId = "content";
        String textContent = "";

        // Act
        document.insert(tagName, idValue, insertLocationId, textContent);

        // Assert
        HTMLTag insertedTag = document.findTagById(idValue);
        assertNotNull(insertedTag, "Tag should not be null");
        assertTrue(TokenTag.isTokenTag(insertedTag.getTagName()), "Inserted tag should be a TokenTag");
        System.out.println("Inserted tag: " + insertedTag.getTagName());
        assertEquals(tagName, insertedTag.getTagName());
    }

    @Test
    public void insert_NormalCase_TypeTag() throws HTMLParseException, IOException {
        // Arrange
        String tagName = "div";
        String idValue = "newDiv";
        String insertLocationId = "content";
        String textContent = "new paragraph";

        // Act
        document.insert(tagName, idValue, insertLocationId, textContent);

        // Assert
        HTMLTag insertedTag = document.findTagById(idValue);
        assertNotNull(insertedTag, "Tag should not be null");
        assertFalse(TokenTag.isTokenTag(insertedTag.getTagName()), "Inserted tag should be a TypeTag");
        assertEquals(tagName, insertedTag.getTagName());
        assertEquals(textContent, insertedTag.getText());
    }

    @Test
    public void insert_InsertLocationNotFound() {
        // Arrange
        String tagName = "div";
        String idValue = "newDiv";
        String insertLocationId = "nonExistentId";
        String textContent = "new paragraph";

        // Act
        System.out.println("Trying to insert with non-existent insert location ID...");
        document.insert(tagName, idValue, insertLocationId, textContent);

        // Assert
        HTMLTag insertedTag = document.findTagById(idValue);
        assertNull(insertedTag, "Tag should not be inserted as insert location ID does not exist");
    }

    @Test
    public void insert_DuplicateId() throws HTMLParseException, IOException {
        // Arrange
        String tagName = "div";
        String idValue = "content"; // Duplicate ID
        String insertLocationId = "body";
        String textContent = "new paragraph";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            document.insert(tagName, idValue, insertLocationId, textContent);
        });
        assertEquals("Duplicate id: " + idValue, exception.getMessage());
    }
}
