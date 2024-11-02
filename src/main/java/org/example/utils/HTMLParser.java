package org.example.utils;

import org.example.model.exception.HTMLParseException;
import org.example.model.html.HTMLDocument;
import org.example.model.html.HTMLTag;
import org.example.model.html.HTMLTokenTag;
import org.example.model.html.HTMLTypeTag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

/**
 * 当前类使用静态方法，将HTML文本解析为一个HTMLTag的根节点，返回这个根节点给HTMLDocument，所有的HTMLDocument中的操作都是针对这个根节点来访问所有的HTML标签
 */
public class HTMLParser {
    public static String readHTMLFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
    public static HTMLDocument parseHTML(String html) throws HTMLParseException {
        //字典需要创建存放创建的dom树根节点以及以及tag和id的哈希映射表
        HTMLDocument document = new HTMLDocument();
        Stack<HTMLTag> tagStack = new Stack<>();
        int i = 0;
        HTMLTag root = null;

        while (i < html.length()) {
            if (html.charAt(i) == '<') {
                // 处理标签
                int tagEnd = findTagEnd(html, i);
                String tagContent = getTagContent(html, i, tagEnd);
                i = processTagContent(tagContent, tagStack, html, i, tagEnd,document);
                if (root == null && !tagStack.isEmpty()) {
                    root = tagStack.firstElement(); // 设置根标签
                }
            } else {
                // 处理文本
                i = processTextContent(html, tagStack, i);
            }
        }
        document.setRoot(root);
        return document;
    }

    // 获取结束标签的位置
    public static int findTagEnd(String html, int i) throws HTMLParseException {
        int tagEnd = html.indexOf('>', i);
        if (tagEnd == -1) {
            throw new HTMLParseException("Invalid HTML: tag not closed properly.");
        }
        return tagEnd;
    }

    // 获取标签内容，去除尖括号和空白
    public static String getTagContent(String html, int tagStart, int tagEnd) {
        return html.substring(tagStart + 1, tagEnd).trim();
    }

    // 处理标签内容
    public static int processTagContent(String tagContent, Stack<HTMLTag> tagStack, String html, int i, int tagEnd,HTMLDocument document) {
        if (isEndTag(tagContent)) {
            tagStack.pop(); // 处理结束标签
        } else {
            HTMLTag newTag = createTagFromContent(tagContent, html, tagEnd,document);
            if (!tagStack.isEmpty()) {
                attachChildToParent(newTag, tagStack.peek());
            }
            if (!isSelfClosingTag(tagContent)) {
                tagStack.push(newTag); // 非自闭合标签压栈
            }
        }
        return tagEnd + 1; // 更新索引
    }

    // 处理文本内容
    public static int processTextContent(String html, Stack<HTMLTag> tagStack, int i) {
        int textEnd = html.indexOf('<', i);
        if (textEnd == -1) textEnd = html.length();
        String text = html.substring(i, textEnd).trim();
        if (!text.isEmpty() && !tagStack.isEmpty()) {
            tagStack.peek().setText(text); // 设置当前标签的文本
        }
        return textEnd; // 更新索引
    }

    // 判断是否结束标签
    public static boolean isEndTag(String tagContent) {
        return tagContent.startsWith("/");
    }

    // 判断是否自闭合标签
    public static boolean isSelfClosingTag(String tagContent) {
        return tagContent.endsWith("/");
    }

    // 创建标签
    public static HTMLTag createTagFromContent(String tagContent, String html, int tagEnd,HTMLDocument htmlDocument) {
        boolean isSelfClosing = isSelfClosingTag(tagContent);
        String tagName = extractTagName(tagContent);
        String id;
        switch (tagName) {
            case "html":
                id="html";
                break;
            case "body":
                id="body";
                break;
            case "head":
                id="head";
                break;
            case "title":
                id="title";
                break;
            default:
                id=extractIdAttribute(tagContent);
                break;
        }
        if (isSelfClosing || !html.substring(tagEnd + 1).contains("<")) {
            HTMLTag tokenTag = new HTMLTokenTag(tagName, id, "");
            htmlDocument.addTag(tokenTag);
            return tokenTag;
        } else {
            HTMLTag typeTag = new HTMLTypeTag(tagName, id, "");
            htmlDocument.addTag(typeTag);
            return typeTag;
        }
    }

    // 从标签内容中提取标签名
    public static String extractTagName(String tagContent) {
        return tagContent.split("\\s+")[0]; // 第一个空白符前的字符串为标签名
    }

    // 提取 id 属性
    public static String extractIdAttribute(String tagContent) {
        int idIndex = tagContent.indexOf("id=\"");
        if (idIndex != -1) {
            int idEnd = tagContent.indexOf("\"", idIndex + 4);
            return tagContent.substring(idIndex + 4, idEnd);
        }
        return "";
    }

    // 将子标签附加到父标签
    public static void attachChildToParent(HTMLTag child, HTMLTag parent) {
        if (parent instanceof HTMLTypeTag) {
            ((HTMLTypeTag) parent).addChild(child);
            child.attachObserver(parent);
        }
    }
}
