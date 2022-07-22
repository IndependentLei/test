package com.shebao.test.crawler;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class JsoupFourTest {

    /**
     * 解析url
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        // 解析url地址
        Document document = Jsoup.parse(new URL("Https://www.baidu.com"), 1000);
        String title = document.getElementsByTag("title").first().text();
        log.debug("title:{}",title);  //  百度一下，你就知道
    }

    /**
     * 解析字符串
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        // 读取字符串
        String context = FileUtils.readFileToString(new File("C:\\Users\\EDY\\Desktop\\jsoup.html"), "utf-8");
//        System.out.println(context);
        Document d = Jsoup.parse(context);
        Elements title = d.getElementsByTag("title");
        System.out.println("title.text() = " + title.text());
    }

    /**
     * 解析文件
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        // 读取字符串
        Document d = Jsoup.parse(new File("C:\\Users\\EDY\\Desktop\\jsoup.html"), "utf-8");
        Elements title = d.getElementsByTag("title");
        System.out.println("title.text() = " + title.text());
    }


    /**
     * 根据id查询元素      getElementById
     * 根据标签获取元素     getElementsByTag
     * 根据class获取元素   getElementsByClass
     * 根据属性获取元素     getElementsByAttribute
     */

    /**
     * 根据id查询元素      getElementById
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        Document d = Jsoup.parse(new File("C:\\Users\\EDY\\Desktop\\jsoup.html"), "utf-8");
        Element element = d.getElementById("qiye-section-h5");
        Element child = element.child(0).child(0);
        System.out.println("child.text() = " + child.text()); // 优秀企业的信赖之选，让高效研发触手可及
    }

    @Test
    public void test4() throws IOException {
        /**
         *      从元素中获取id
         *      从元素中获取className
         *      从元素中获取属性的值attr
         *      从元素中获取所有属性的attributes
         *      从元素中获取文本内容text
         */
        Document document = Jsoup.parse(new File("C:\\Users\\EDY\\Desktop\\jsoup.html"), "utf-8");
        Element element = document.getElementById("qiye-section-h5");
        System.out.println("element.id() = " + element.id());
        System.out.println("element.className() = " + element.className());
        System.out.println("element.attr(\"id\") = " + element.attr("id")); // 根据属性的名字获取属性的值
        System.out.println("element.attributes() = " + element.attributes());
        System.out.println("element.text() = " + element.text());
    }

    @Test
    public void test5() throws IOException {
        /**
         *      Selector选择器
         *
         *      tagname:通过标签查找元素，比如 <span></span>
         *      #id :通过ID查找元素，比如： #city_id
         *      .class :通过class名称查找元素：比如： .class_a
         *      [attribute]:通过属性查找元素，比如：[abc]
         *      [attr=value]: 利用属性值来查找元素，比如：[class=s_name]
         */
        Document document = Jsoup.parse(new File("C:\\Users\\EDY\\Desktop\\jsoup.html"), "utf-8");
        // 标签
        Elements ele = document.select("span");
        for(Element element : ele){
            System.out.println(element.text());
        }
        System.out.println("------------------------------");
        // id    ---->   #qiye-section-h5
        Elements ids = document.select("#qiye-section-h5");
        for(Element element : ids){
            System.out.println(element.text());
        }
        System.out.println("------------------------------");
        /*
            .class
         */
        Elements clazz = document.select(".title");
        for(Element element : clazz){
            System.out.println(element.text());
        }
        System.out.println("------------------------------");
        /*
            根据属性
         */
        Elements attrs = document.select("[href]");
        for(Element element : attrs){
            System.out.println("href.first().text() = " + element.text());
        }
        System.out.println("------------------------------");

        /*
            根据属性和属性值获取元素
         */
        Elements select = document.select("[href=https://e.coding.net/register]");
        for (Element element : select){
            System.out.println("element.text() = " + element.text());
        }
    }

    /**
     * 组合选择器的使用
     */
    @Test
    public void test7() throws IOException {
        Document document = Jsoup.parse(new File("C:\\Users\\EDY\\Desktop\\go.html"), "utf-8");
        // 标签+id
        System.out.println(document.select("h1#gogo").text());
        System.out.println("-------------------------------------");
        // 标签 + class
        Element select = document.select("p.you").first();
        System.out.println(select.text());
        System.out.println("-------------------------------------");

        // 标签+class+属性
        Element select1 = document.select("p.you[gogo]").first();
        System.out.println(select1.text());
        System.out.println("-------------------------------------");
        // 标签+class+属性和属性值
        Element select2 = document.select("p.you[gogo=thankyou]").first();
        System.out.println(select2.text());
    }
}
