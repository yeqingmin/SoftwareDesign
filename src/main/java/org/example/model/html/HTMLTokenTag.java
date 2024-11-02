package org.example.model.html;

import lombok.Data;

/**
 * 当前类是不可包含子标签的HTMLTag：如text，img等，为了保持状态一致，就需要实现tag和observer接口
 */
@Data
public class HTMLTokenTag implements HTMLTag{
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
     * 当前Tag的观察者，就是当前节点的父节点
     */
    private HTMLTag parent;
    /**
     * 是否删除的标签值
     */
    private boolean isDeleted;

    public HTMLTokenTag(String tagName,String idValue, String textContent) {
        this.tagName=tagName;
        this.id=idValue;
        this.text=textContent;
    }

    @Override
    public void attachObserver(HTMLTag observer) {
    //添加父节点
        this.parent=observer;
    }

    public void deleteUpdate() {
        //1. 如果是删除，则需要通知父节点应该删除当前这个节点
        if(this.parent instanceof HTMLTypeTag){
            HTMLTypeTag parentTag=(HTMLTypeTag) this.parent;
            parentTag.removeChild(this);
        }
    }

    /**
     * 恢复状态
     */
    @Override
    public void deleteRecoverUpdate() {
        if(this.parent instanceof HTMLTypeTag){
            HTMLTypeTag parentTag=(HTMLTypeTag) this.parent;
            parentTag.addChild(this);
        }
    }

    public void insertUpdate(HTMLTag newTag) {
        //2. 如果是在当前节点前插入了某个内容，那么父节点（observers）的子节点应该更新加入新子节点,且在这个元素之前，同时新建的这个子节点应该新增观察者（父节点）
        if(this.parent instanceof HTMLTypeTag){
            HTMLTypeTag parentTag=(HTMLTypeTag) this.parent;
            parentTag.addChild(newTag, parentTag.getChildren().indexOf(this));
        }
        newTag.attachObserver(this.parent);
    }

    /**
     * 实现当前节点输出树节点的渲染
     */
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
        if (!text.isEmpty()) {
            if(!isRoot) {
                if (!isLastChild) {
                    result.append("\n").append(indent).append("│   └── ").append(text).append("\n");
                } else {
                    result.append("\n").append(indent).append("    └── ").append(text).append("\n");
                }
            }
        } else {
            result.append("\n");
        }

        return result.toString();
    }
}
