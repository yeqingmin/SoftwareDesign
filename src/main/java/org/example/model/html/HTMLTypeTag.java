package org.example.model.html;

import lombok.Data;

import java.util.List;

/**
 * 当前类定义了可以接着存放子元素的HTML标签，如body，html，div，p这种
 */
@Data
public class HTMLTypeTag implements HTMLTag, HTMLTagObserver{
    /**
     * 当前标签的id，对于body这些默认id为body标签名本身
     */
    private int id;
    /**
     * 当前标签内存在的文本，含有其他子标签的，文本就在其他子标签前面
     */
    private String text;
    /**
     * 当前标签的观察者，用于通知当前标签的子标签，一般来说包含当前标签的父标签和所有子标签
     */
    private List<HTMLTagObserver> observers;
    /**
     * 包含的子标签，可以是token也可以是type
     */
    private List<HTMLTag> children;
    /**
     * 是否删除的标签值
     */
    private boolean isDeleted;
    @Override
    public void attachObserver(HTMLTagObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void detachObserver(HTMLTagObserver observer) {
        if(this.observers != null) {
            this.observers.remove(observer);
        }
    }

    @Override
    public void update() {
        //更新观察者状态很简单，对于Type类型的，就是更新子节点的状态
    }
}
