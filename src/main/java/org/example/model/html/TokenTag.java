package org.example.model.html;

public enum TokenTag {
    IMG("img"),
    BR("br"),
    HR("hr"),
    INPUT("input"),
    META("meta"),
    LINK("link"),
    AREA("area"),
    BASE("base"),
    COL("col"),
    EMBED("embed"),
    SOURCE("source"),
    TRACK("track"),
    WBR("wbr"),
    PARAM("param");
    private final String tagName;

    TokenTag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    // 判断给定的标签名是否是空标签
    public static boolean isTokenTag(String tagName) {
        for (TokenTag voidTag : TokenTag.values()) {
            if (voidTag.getTagName().equalsIgnoreCase(tagName)) {
                return true;
            }
        }
        return false;
    }
}
