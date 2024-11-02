package org.example.model.html;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TokenTagTest {
    @Test
    public void testIsTokenTagWithValidTags() {
        assertFalse(TokenTag.isTokenTag("body"));
        assertFalse(TokenTag.isTokenTag("A")); // 检查大小写不敏感
        assertTrue(TokenTag.isTokenTag("br"));
        assertTrue(TokenTag.isTokenTag("IMG"));
        assertTrue(TokenTag.isTokenTag("area"));
        assertTrue(TokenTag.isTokenTag("textarea"));
    }

    @Test
    public void testIsTokenTagWithInvalidTags() {
        assertFalse(TokenTag.isTokenTag("unknown"));
        assertFalse(TokenTag.isTokenTag("nonexistent"));
        assertFalse(TokenTag.isTokenTag("invalid"));
    }

    @Test
    public void testIsTokenTagWithNullAndEmptyStrings() {
        assertFalse(TokenTag.isTokenTag(null));
        assertFalse(TokenTag.isTokenTag(""));
        assertFalse(TokenTag.isTokenTag(" "));
    }
}
