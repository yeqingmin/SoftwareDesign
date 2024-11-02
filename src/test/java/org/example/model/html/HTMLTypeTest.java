package org.example.model.html;


import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HTMLTypeTest {

    @Test
    public void addChildTest() {
        // Arrange
        HTMLTypeTag parent=new HTMLTypeTag("div","div","");
        HTMLTypeTag child1=new HTMLTypeTag("div","p","ababa");
        HTMLTokenTag child2=new HTMLTokenTag("img","","dssd");
        parent.addChild(child1);
        parent.addChild(child2);

        // Assert
        List<HTMLTag> children = parent.getChildren();
        assertEquals(2, children.size());
        assertTrue(children.contains(child1));
        assertTrue(children.contains(child2));
    }

}
