package com.love233niang.weblog.markdown;

import com.love233niang.weblog.markdown.renderer.ImageNodeRenderer;
import com.love233niang.weblog.markdown.renderer.LinkNodeRenderer;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;

public class MarkdownHelper {

    /**
     * Markdown 解析器
     */
    private final static Parser PARSER;

    /**
     * HTML 渲染器
     */
    private final static HtmlRenderer HTML_RENDERER;

    static {
        List<Extension> extensions = Arrays.asList(
                TablesExtension.create(), // 添加表格支持
                HeadingAnchorExtension.create(), // 添加标题锚点支持
                ImageAttributesExtension.create(), // 添加图片属性支持
                TaskListItemsExtension.create() // 添加任务列表项支持
        );

        PARSER = Parser.builder().extensions(extensions).build();
        HTML_RENDERER = HtmlRenderer.builder()
                .extensions(extensions)
                .nodeRendererFactory(context -> new ImageNodeRenderer(context)) // 自定义图片解析
                .nodeRendererFactory(context -> new LinkNodeRenderer(context)) // 自定义超链接解析
                .build();
    }

    /**
     * 将 Markdown 转换为 HTML
     *
     * @param markdown
     * @return
     */
    public static String convertMarkdown2Html(String markdown) {
        Node document = PARSER.parse(markdown);
        return HTML_RENDERER.render(document);
    }

    public static void main(String[] args) {
        String markdown = "![图 1-1 技术栈](https://img.love233niang.com/love233niang/169560181378937 \"图 1-1 技术栈\"){width=100 height=100}";
        System.out.println(MarkdownHelper.convertMarkdown2Html(markdown));

    }


}
