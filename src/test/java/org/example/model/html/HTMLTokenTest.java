package org.example.model.html;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HTMLTokenTest {
    @Test
    public void insertUpdateTest() {
        HTMLTypeTag parentTag = new HTMLTypeTag("body","parentId", "parentText");
        HTMLTokenTag insertLocationTag = new HTMLTokenTag("div","child1Id", "childText");
        insertLocationTag.setParent(parentTag);
        parentTag.addChild(insertLocationTag);
        // Arrange
        HTMLTokenTag newTag = new HTMLTokenTag("p","newId", "newText");
        // 执行insertUpdate方法，会实现新标签添加到父节点的子节点数组中，并添加观察者为当前tag的父节点
        insertLocationTag.insertUpdate(newTag);

        assertEquals(parentTag, newTag.getParent());
        assertEquals(newTag,parentTag.getChildren().get(parentTag.getChildren().size()-1));
    }
}

