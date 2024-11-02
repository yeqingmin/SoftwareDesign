package org.example.model.printer;

import org.example.model.html.HTMLTag;
import org.example.model.html.HTMLTypeTag;

import java.util.List;
//待优化，这个类写得嵌套很多，不够好看
public class TreePrinter implements Printer<HTMLTag> {
    @Override
    public String format(HTMLTag root) {
        return root.render("", true,true);
    }
}