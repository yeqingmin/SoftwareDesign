package org.example.utils;

import org.example.model.exception.HTMLParseException;
import org.example.model.html.HTMLTag;
import org.example.model.printer.IndentPrinter;
import org.example.model.printer.TreePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class HTMLParserTest {
    private String tagWithId;
    private String tagWithoutId;
    private String tagWithMultipleAttributes;
    private String tagWithIdAndQuotes;

    private String validHtml;
    private String invalidHtml;
    private int testPosition;

    @BeforeEach
    public void setUp() {
        validHtml = "<div class=\"container\">"; // 有效的HTML标签
        invalidHtml = "<div class=\"container\""; // 无效的HTML标签（未闭合）
        testPosition = 0; // 测试开始位置
        tagWithId = "div id=\"testId\"";
        tagWithoutId = "div class=\"testClass\"";
        tagWithMultipleAttributes = "div id=\"testId\" class='testClass'";
        tagWithIdAndQuotes = "div id=\"testId\"";
    }

    @Test
    public void findTagEnd_ValidHtml_ReturnsCorrectIndex() throws HTMLParseException {
        // 测试有效的HTML，期望方法返回正确的结束标签位置
        int expectedIndex = validHtml.indexOf('>');
        assertEquals(expectedIndex, HTMLParser.findTagEnd(validHtml, testPosition));
    }

    @Test
    public void findTagEnd_InvalidHtml_ThrowsHTMLParseException() {
        // 测试无效的HTML，期望方法抛出HTMLParseException
        assertThrows(HTMLParseException.class, () -> HTMLParser.findTagEnd(invalidHtml, testPosition));
    }

    @Test
    public void findTagEnd_HtmlWithoutClosingTag_ThrowsHTMLParseException() {
        // 测试没有结束标签的HTML，期望方法抛出HTMLParseException
        String html = "<div";
        assertThrows(HTMLParseException.class, () -> HTMLParser.findTagEnd(html, testPosition));
    }

    @Test
    public void findTagEnd_EmptyHtml_ThrowsHTMLParseException() {
        // 测试空的HTML，期望方法抛出HTMLParseException
        String html = "";
        assertThrows(HTMLParseException.class, () -> HTMLParser.findTagEnd(html, testPosition));
    }

    @Test
    public void findTagEnd_HtmlWithNestedTags_ReturnsCorrectIndex() throws HTMLParseException {
        // 测试嵌套标签的HTML，期望方法返回正确的结束标签位置
        String html = "<div><span></span></div>";
        int expectedIndex = html.indexOf('>');
        assertEquals(expectedIndex, HTMLParser.findTagEnd(html, testPosition));
    }
    @Test
    public void wholeRightTest() throws HTMLParseException {
        HTMLTag root = HTMLParser.parseHTML("<html id=\"html\">\n" +
                "<head id=\"head\">\n" +
                "    <title id=\"title\">Test-01</title>\n" +
                "</head>\n" +
                "<body id=\"body\">\n" +
                "    <div id=\"container\">\n" +
                "        <h1 id=\"header\">happy</h1>\n" +
                "        <div id=\"c-body\">\n" +
                "            birthday\n" +
                "            <ul id=\"content\">\n" +
                "                <li id=\"one\">to</li>\n" +
                "                <li id=\"two\">you</li>\n" +
                "            </ul>\n" +
                "            <p id=\"para\">dudulu</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>").getRoot();
        IndentPrinter indentPrinter=new IndentPrinter(4);
        TreePrinter treePrinter=new TreePrinter();
        System.out.println(indentPrinter.format(root));
//        System.out.println(treePrinter.format(root));
    }
    @Test
    public void wholeDuplicateIdRTest() throws HTMLParseException {
        HTMLTag root = HTMLParser.parseHTML("<html id=\"html\">\n" +
                "<head id=\"head\">\n" +
                "    <title id=\"title\">Test-01</title>\n" +
                "</head>\n" +
                "<body id=\"body\">\n" +
                "    <div id=\"container\">\n" +
                "        <h1 id=\"head\">happy</h1>\n" +
                "        <div id=\"c-body\">\n" +
                "            birthday\n" +
                "            <ul id=\"content\">\n" +
                "                <li id=\"one\">to</li>\n" +
                "                <li id=\"two\">you</li>\n" +
                "            </ul>\n" +
                "            <p id=\"para\">dudulu</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>").getRoot();
        assertThrows(IllegalArgumentException.class,()->{});
    }
    @Test
    public void extractIdAttribute_TagWithId_ReturnsId() {
        // 测试包含 id 属性的标签，期望方法返回正确的 id 值
        assertEquals("testId", HTMLParser.extractIdAttribute(tagWithId));
    }

    @Test
    public void extractIdAttribute_TagWithoutId_ReturnsEmptyString() {
        // 测试不包含 id 属性的标签，期望方法返回空字符串
        assertEquals("", HTMLParser.extractIdAttribute(tagWithoutId));
    }

    @Test
    public void extractIdAttribute_TagWithMultipleAttributes_ReturnsId() {
        // 测试包含多个属性的标签，期望方法返回正确的 id 值
        assertEquals("testId", HTMLParser.extractIdAttribute(tagWithMultipleAttributes));
    }

    @Test
    public void extractIdAttribute_TagWithIdAndDoubleQuotes_ReturnsId() {
        // 测试 id 属性使用双引号的标签，期望方法返回正确的 id 值
        assertEquals("testId", HTMLParser.extractIdAttribute(tagWithIdAndQuotes));
    }

    @Test
    public void extractIdAttribute_TagWithNoClosingQuote_ReturnsEmptyString() {
        // 测试没有闭合引号的标签，期望方法返回空字符串
        String tag = "div id='testId";
        assertEquals("", HTMLParser.extractIdAttribute(tag));
    }

    @Test
    public void extractIdAttribute_EmptyString_ReturnsEmptyString() {
        // 测试空字符串，期望方法返回空字符串
        assertEquals("", HTMLParser.extractIdAttribute(""));
    }

    @Test
    public void extractIdAttribute_NullInput_ReturnsEmptyString() {
        // 测试 null 输入，期望方法返回空字符串
        assertEquals("", HTMLParser.extractIdAttribute(null));
    }

    @Test
    public void readHTMLFileTest() throws IOException {
        String filePath = "src/test/resources/testfile/test_one.html";
        String htmlContent = HTMLParser.readHTMLFile(filePath);
        System.out.println(htmlContent);
    }
}

