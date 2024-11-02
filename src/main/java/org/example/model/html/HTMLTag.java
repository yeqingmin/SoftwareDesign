package org.example.model.html;

import org.example.model.printer.TreeNodeRender;

public interface HTMLTag extends TreeNodeRender {
    /**
     * 添加观察者
     * @param observer 观察者
     */
    public void attachObserver(HTMLTag observer);

    String getId();

    //结合了观察者模式，当有更新时，通知观察者（这里就是父节点和子节点）
    void deleteUpdate();

    void deleteRecoverUpdate();

    void insertUpdate(HTMLTag newTag);

    void setDeleted(boolean b);

    void setId(String newId);

    void setText(String newText);

    String getTagName();

    String getText();

    HTMLTag getParent();

    boolean isDeleted();
}
