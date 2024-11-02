package org.example.model.html;

import lombok.Data;
import org.example.model.exception.HTMLTypeException;
import org.example.model.printer.IndentPrinter;
import org.example.model.printer.Printer;
import org.example.model.printer.TreePrinter;
import org.example.utils.HTMLParser;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.language.BritishEnglish;
import org.languagetool.markup.AnnotatedText;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.RuleMatch;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 管理所有html节点（用id的散列表管理），同时作为command的receiver，所有指令的具体实现都在这个类里面
 */
@Data
public class HTMLDocument {
    //当前类实现所有指令
    // 使用 HashMap 来存储 id 到 HTMLTag 的映射
    private Map<String, HTMLTag> id2Tag = new HashMap<>();
    private HTMLTag root;   //根节点html标签
    private Printer<HTMLTag> printer;
    /**
     * 插入新tag元素的具体实现
     * @param tagName
     * @param idValue
     * @param insertLocationId
     * @param textContent
     */
    public void insert(String tagName, String idValue, String insertLocationId, String textContent){
        //2. 找到插入位置的tag
        HTMLTag insertLocationTag = findTagById(insertLocationId);
        if(insertLocationTag!=null) {
            //1. 先新建一个节点
            HTMLTag newHtmlTag = null;
            if (TokenTag.isTokenTag(tagName)) {
                //新建为TokenTag
                newHtmlTag = new HTMLTokenTag(tagName, idValue, textContent);
            } else {
                newHtmlTag = new HTMLTypeTag(tagName, idValue, textContent);
            }
            //3. 更新状态
            insertLocationTag.insertUpdate(newHtmlTag);
            addTag(newHtmlTag);
        }else {
            System.out.println("NO SUCH INSERT LOCATION ID");
        }
    }

    /**
     * 在标签中插入新的子元素的具体实现
     * @param tagName
     * @param idValue
     * @param parentElementId
     * @param textContent
     * @throws HTMLTypeException
     */
    public void append(String tagName, String idValue, String parentElementId, String textContent) throws HTMLTypeException,IllegalArgumentException {
        //2. 找到插入位置的tag
        HTMLTag parentElementTag = findTagById(parentElementId);
        if(parentElementTag!=null) {
            //1. 先新建一个节点
            HTMLTag newHtmlTag = null;
            if (TokenTag.isTokenTag(tagName)) {
                //新建为TokenTag
                newHtmlTag = new HTMLTokenTag(tagName, idValue, textContent);
            } else {
                newHtmlTag = new HTMLTypeTag(tagName, idValue, textContent);
            }
            addTag(newHtmlTag);
            //这里要检查是否不是typetag，如果要向tokentag中加入子元素会抛出错误
            if (parentElementTag instanceof HTMLTypeTag) {
                HTMLTypeTag parentTypeTag = (HTMLTypeTag) parentElementTag;
                parentTypeTag.addChild(newHtmlTag);
                newHtmlTag.attachObserver(parentElementTag);
            } else {
                throw new HTMLTypeException(tagName);
            }
        }else {
            System.out.println("NO SUCH PARENT ELEMENT");
        }
    }

    /**
     * 修改id的具体实现
     * @param oldId
     * @param newId
     */
    public void editId(String oldId, String newId){
        //1. 根据oldId找到对应的tag
        HTMLTag targetTag = findTagById(oldId);
        //2. 判断新id是否会重复
        if(id2Tag.containsKey(newId)){
//            throw new IllegalArgumentException("Duplicate id: " + newId);
            System.out.println("Duplicate id: " + newId);
        }else{
            if(targetTag!=null) {
                targetTag.setId(newId);
                //还需要修改id2Tag哈希表的内容
                removeTag(oldId);
                addTag(targetTag);
            }else {
                System.out.println("NO SUCH ELEMENT ID");
            }
        }
    }

    /**
     * 修改标签文本
     * @param elementId
     * @param newText
     */
    public String editText(String elementId, String newText){
        HTMLTag targetTag = findTagById(elementId);
        if(targetTag!=null) {
            String oldText = targetTag.getText();
            targetTag.setText(newText);
            return oldText;
        }else{
            System.out.println("NO SUCH ELEMENT ID");
            return null;
        }
    }

    /**
     * 删除元素的具体实现
     * @param elementId
     */
    public HTMLTag delete(String elementId){
        HTMLTag targetTag = findTagById(elementId);
        if(targetTag!=null) {
            removeTag(elementId);
            targetTag.setDeleted(true);
            //更新状态，通知树中其他节点
            targetTag.deleteUpdate();
            return targetTag;
        }else {
            System.out.println("NO SUCH ELEMENT ID");
            return null;
        }
    }
    public void printIndent(int indent){
        //根据this.root进行打印
        printer=new IndentPrinter(indent);
        if(root==null){
            System.out.println("EMPTY HTML,PLEASE READ OR INIT");
        }else{
            System.out.println(printer.format(root));
        }
    }
    public void printTree(){
        //根据this.root打印
        printer=new TreePrinter();
        if(root==null){
            System.out.println("EMPTY HTML,PLEASE READ OR INIT");
        }else{
            System.out.println(printer.format(root));
        }
    }
    public void spellCheck(){
        //根据this.root进行拼写检查
        //调用api
        //获取当前的html字符串
        printer=new IndentPrinter(2);
        if(this.root!=null) {
            String htmlContent = printer.format(this.root);
            JLanguageTool langTool = new JLanguageTool(new BritishEnglish());

            // 使用 AnnotatedTextBuilder 解析 HTML 文本
            AnnotatedTextBuilder builder = new AnnotatedTextBuilder();

            boolean inTag = false;
            StringBuilder textContent = new StringBuilder();

            for (char c : htmlContent.toCharArray()) {
                if (c == '<') {
                    // 添加去掉多余空格后的文本
                    addNonEmptyText(builder, textContent.toString());
                    textContent.setLength(0); // 清空文本缓冲
                    inTag = true;
                }
                if (!inTag) {
                    textContent.append(c); // 累加标签外的文本
                }
                if (c == '>') {
                    inTag = false;
                }
            }

            // 处理剩余文本
            addNonEmptyText(builder, textContent.toString());

            // 创建 AnnotatedText 并进行拼写检查
            AnnotatedText annotatedText = builder.build();
            try {
                for (RuleMatch match : langTool.check(annotatedText)) {
                    System.out.printf("Potential error at line %d, column %d: %s%n",
                            match.getLine(), match.getColumn(), match.getMessage());
                    System.out.printf("Suggested correction(s): %s%n%n", match.getSuggestedReplacements());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("EMPTY HTML MODEL,PLEASE READ OR INIT");
        }
    }
    private void addNonEmptyText(AnnotatedTextBuilder builder, String text) {
        // 去掉多余空白字符，如果有非空内容才添加
        String trimmedText = text.trim();
        if (!trimmedText.isEmpty()) {
            builder.addText(trimmedText + " ");
        }
    }
    public void save(String directoryPath){
        // 创建一个 UUID 作为文件名
        String fileName = UUID.randomUUID().toString() + ".html";
        // 拼接完整的文件路径
        String filePath = Paths.get(directoryPath, fileName).toString();

        printer = new IndentPrinter(4);
        String content = "";

        // 格式化内容
        try {
            if(this.root==null){
                System.out.println("NULL HTML MODEL");
            }else{
                content = printer.format(this.root);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(this.root!=null) {
            // 写入文件
            try {
                // 确保目录存在
                Path path = Paths.get(directoryPath);
                if (!Files.exists(path)) {
                    Files.createDirectories(path); // 创建目录
                }

                // 使用 BufferedWriter 写入文件
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                    bw.write(content);
                }

                System.out.println("文件已保存至: " + filePath); // 输出保存的文件路径
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void read(String filePath) {
        String html="";
        try{
            html= HTMLParser.readHTMLFile(filePath);
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            HTMLDocument parseredDoc=HTMLParser.parseHTML(html);
            this.root= parseredDoc.getRoot();
            this.id2Tag=parseredDoc.getId2Tag();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void init(){
        String html = "";
        try {
            // 使用类加载器读取资源文件
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("template/HTMLTemplate.html");

            if (inputStream == null) {
                throw new FileNotFoundException("HTMLTemplate.html not found in resources");
            }
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            html= stringBuilder.toString();
            inputStream.close();
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            HTMLDocument parsedDoc = HTMLParser.parseHTML(html);
            this.root = parsedDoc.getRoot();
            this.id2Tag = parsedDoc.getId2Tag();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 添加标签并检查 id 是否重复
    public void addTag(HTMLTag tag) throws IllegalArgumentException {
        if (id2Tag.containsKey(tag.getId())) {
            throw new IllegalArgumentException("Duplicate id: " + tag.getId());
        }
        id2Tag.put(tag.getId(), tag);  // 将标签添加到 map 中
    }
    // 删除标签
    private void removeTag(String id) {
        id2Tag.remove(id);  // 将标签从 map 中移除
    }

    // 根据id查找标签
    public HTMLTag findTagById(String id) {
        return id2Tag.get(id);
    }
}
