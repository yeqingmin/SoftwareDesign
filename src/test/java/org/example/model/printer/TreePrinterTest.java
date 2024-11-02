package org.example.model.printer;

import org.example.model.exception.HTMLParseException;
import org.example.model.html.HTMLTag;
import org.example.model.html.HTMLTokenTag;
import org.example.model.html.HTMLTypeTag;
import org.example.utils.HTMLParser;
import org.junit.jupiter.api.Test;

public class TreePrinterTest {
    @Test
    public void test(){
        TreePrinter printer = new TreePrinter();
        HTMLTokenTag tokenTag=new HTMLTokenTag("div", "child", "child-1-Text");
        HTMLTokenTag tokenTag2=new HTMLTokenTag("p", "paragraph", "child-2-text");
        HTMLTypeTag root = new HTMLTypeTag("div", "main", "content");
        root.addChild(tokenTag);
        root.addChild(tokenTag2);
        tokenTag.setParent(root);
        tokenTag2.setParent(root);

        String formatted = printer.format(root);
        System.out.println(formatted);
    }

    @Test
    public void test2() throws HTMLParseException {
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
        TreePrinter treePrinter=new TreePrinter();
        System.out.println(treePrinter.format(root));
    }
}
