package org.example.model.html;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 当前类定义了可以接着存放子元素的HTML标签，如body，html，div，p这种
 */
@Data
public class HTMLTypeTag implements HTMLTag{
    /**
     * 标签名
     */
    private String tagName;
    /**
     * 当前标签的id，对于body这些默认id为body标签名本身
     */
    private String id;
    /**
     * 当前标签内存在的文本，含有其他子标签的，文本就在其他子标签前面
     */
    private String text;
    /**
     * 当前标签的观察者，用于通知当前标签的子标签，一般来说包含当前标签的父标签和所有子标签
     */
    private HTMLTag parent;
    /**
     * 包含的子标签，可以是token也可以是type
     */
    private List<HTMLTag> children=new ArrayList<>();
    /**
     * 是否删除的标签值
     */
    private boolean isDeleted;
    public HTMLTypeTag(){}
    public HTMLTypeTag(String tagName,String idValue, String textContent) {
        this.tagName=tagName;
        this.id=idValue;
        this.text=textContent;
    }

    @Override
    public void attachObserver(HTMLTag observer) {
        this.parent=observer;
    }

    @Override
    public void deleteUpdate() {
    //1. 如果是删除，则需要更新所有子节点，子节点全部通知为已删除，同时父节点应该删除当前这个节点
        for(HTMLTag child : children){
            child.setDeleted(true);
        }
        if(this.parent instanceof HTMLTypeTag){
            HTMLTypeTag parentTag=(HTMLTypeTag) this.parent;
            parentTag.removeChild(this);
        }
    }

    /**
     * 逻辑删除，恢复所有节点
     */
    @Override
    public void deleteRecoverUpdate() {
        for(HTMLTag child : children){
            child.setDeleted(true);
        }
        if(this.parent instanceof HTMLTypeTag){
            HTMLTypeTag parentTag=(HTMLTypeTag) this.parent;
            parentTag.addChild(this);
        }
    }

    @Override
    public void insertUpdate(HTMLTag newTag) {
    //2. 如果是在当前节点前插入了某个内容，那么父节点的子节点应该更新加入新子节点，同时新建的这个子节点应该新增观察者（父节点）
        if(this.parent instanceof HTMLTypeTag){
            HTMLTypeTag parentTag=(HTMLTypeTag) this.parent;
            parentTag.addChild(newTag, parentTag.getChildren().indexOf(this));
        }
        newTag.attachObserver(this.parent);
    }

    @Override
    public void setDeleted(boolean b) {
        this.isDeleted=b;
    }

    public void addChild(HTMLTag child){
        this.children.add(child);
    }
    //重载
    public void addChild(HTMLTag child,int index){
        this.children.add(index,child);
    }
    public void removeChild(HTMLTag child){
        this.children.remove(child);
    }

    @Override
    public String render(String indent,boolean isLastChild,boolean isRoot) {
        StringBuilder result = new StringBuilder();
        String prefix="";
        if(!isRoot&isLastChild){
            //如果是最后一个孩子
            prefix="└── ";
        }else if(!isRoot&!isLastChild){
            prefix="├── ";
        }
        result.append(indent).append(prefix).append(tagName);
        if (!id.isEmpty()&!tagName.equals("html")&!tagName.equals("body")&!tagName.equals("head")&!tagName.equals("title")) {
            result.append("#").append(id);
        }

        String indentToChildren="";
        //如果说是最后一个孩子，那么就不用加│了
        if(!isRoot){
            if (!isLastChild){
                indentToChildren=indent+"│   ";
            }else{
                indentToChildren=indent+"    ";
            }
        }else {
            indentToChildren=indent;
        }

        if (!text.isEmpty()) {
            //如果有孩子
            if (!children.isEmpty()) {
                result.append("\n").append(indentToChildren).append("├── ").append(text).append("\n");
            } else {
                result.append("\n").append(indentToChildren).append("└── ").append(text).append("\n");
            }
        }else{
            result.append("\n");
        }

        for (int i = 0; i < children.size(); i++) {
            HTMLTag child = children.get(i);
            if (i == children.size() - 1) {
                //如果是最后一个
                result.append(child.render(indentToChildren, true, false));
            } else {
                result.append(child.render(indentToChildren, false, false));
            }
        }

        return result.toString();
    }
}
