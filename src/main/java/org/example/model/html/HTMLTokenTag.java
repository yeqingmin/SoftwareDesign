package org.example.model.html;

import lombok.Data;

import java.util.List;

/**
 * 当前类是不可包含子标签的HTMLTag：如text，img等，为了保持状态一致，就需要实现tag和observer接口
 */
@Data
public class HTMLTokenTag implements HTMLTag, HTMLTagObserver{
    /**
     * 当前标签的id，对于body这些默认id为body标签名本身
     */
    private int id;
    /**
     * 当前标签内存在的文本，含有其他子标签的，文本就在其他子标签前面
     */
    private String text;
    /**
     * 当前Tag的观察者，一般来说只有一个就是他的父标签
     */
    private List<HTMLTagObserver> observers;
    /**
     * 是否删除的标签值
     */
    private boolean isDeleted;
    @Override
    public void attachObserver(HTMLTagObserver observer) {

    }

    @Override
    public void detachObserver(HTMLTagObserver observer) {

    }

    @Override
    public void update() {

    }
}
