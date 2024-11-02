package org.example.model.printer;

import org.example.model.html.HTMLTokenTag;
import org.example.model.html.HTMLTypeTag;
import org.junit.jupiter.api.Test;
public class IndentPrinterTest {
    @Test
    public void format_SingleTag_CorrectlyFormatted() {
        IndentPrinter printer = new IndentPrinter(4);
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

}
