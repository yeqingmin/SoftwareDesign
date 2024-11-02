package org.example.model.printer;

import org.example.model.html.HTMLTag;
import org.example.model.html.HTMLTypeTag;


public class IndentPrinter implements Printer<HTMLTag>{
    private final int indentSpaces;

    public IndentPrinter(int indentSpaces) {
        this.indentSpaces = indentSpaces;
    }

    @Override
    public String format(HTMLTag root) {
        StringBuilder result = new StringBuilder();
        formatWithIndent(root, 0, result);
        return result.toString();
    }

    private void formatWithIndent(HTMLTag tag, int level, StringBuilder result) {
        StringBuilder indent = new StringBuilder();
        String tagName=tag.getTagName();
        for(int i=0;i<level*indentSpaces;i++){
            indent.append(" ");
        }
        result.append(indent).append("<")
                .append(tag.getTagName());
        if(!tag.getId().isEmpty()&!tagName.equals("html")&!tagName.equals("body")&!tagName.equals("head")&!tagName.equals("title")) {
            result.append(" id=\"").append(tag.getId()).append("\"");
        }
        result.append(">");
        if (!tag.getText().isEmpty()) {
            StringBuilder indentSpace=new StringBuilder();
            for(int i=0;i<indentSpaces;i++){indentSpace.append(" ");}
            if(level==0){
                result.append("\n").append(indentSpace).append(tag.getText());
            }else {
                result.append("\n").append(indent).append(indentSpace).append(tag.getText());
            }
        }
        result.append("\n");
        if(tag instanceof HTMLTypeTag){
            HTMLTypeTag typeTag=(HTMLTypeTag)tag;
            for (HTMLTag child : typeTag.getChildren()) {
                formatWithIndent(child, level + 1, result);
            }
        }
        result.append(indent).append("</").append(tag.getTagName()).append(">\n");
    }
}
