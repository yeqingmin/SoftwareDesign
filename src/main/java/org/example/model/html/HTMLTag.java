package org.example.model.html;

public interface HTMLTag {
    /**
     * 添加观察者
     * @param observer 观察者
     */
    public void attachObserver(HTMLTagObserver observer);

    /**
     * 移除观察者
     * @param observer 观察者
     */
    public void detachObserver(HTMLTagObserver observer);

}
