package com.shebao.test.test;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;

@Component
public class testOne {
    // 非阻塞队列
    @Test
    public void one(){
        // 先进后出
        // 双端队列
        Deque<String> strs = new LinkedList<>();
        // 压入元素(添加)
//        strs.add()
//        strs.offer();
        strs.addLast("1");
        strs.addFirst("2");
        strs.addFirst("3");
//        strs.addLast("4");
//        System.out.println(strs);
        // 弹出元素(删除)：remove()、poll()
//        String remove = strs.remove();// 抛异常
        String poll = strs.poll(); // 不抛异常
        System.out.println(strs.element()); //获取第一个值，没有值抛异常
        System.out.println(strs.peek()); // 获取第一个值，没有值不抛异常，返回null
        System.out.println(strs);
    }
    // 阻塞队列
    @Test
    public void two(){
        //阻塞队列接口有BlockingQueue和BlockingDeque两个，其中后者是双向队列。
        Queue<String> strs = new LinkedBlockingQueue<>(2);
        strs.add("1");
        strs.add("1");
        strs.add("2");
    }

    @Test
    public void test3(){
        AtomicInteger atomicInteger = new AtomicInteger(5);
        atomicInteger.getAndUpdate((p)-> p = p+2);
        System.out.println(atomicInteger);
    }
    @Test
    public void test4(){
        LongAdder longAdder = new LongAdder();
        longAdder.increment(); // 多个计算单元一起计算，效率高
    }

    // CAS版本号
    static AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("A",0);



    @Scheduled(cron = "0/35 * * * * ?")
    public void test6(){
        WebDriver driver = null;

        try {
            Thread.sleep(1000);

            System.setProperty("webdriver.chrome.driver", "D:\\application\\chromedriver_win32 (2)\\chromedriver.exe");// chromedriver服务地址
            ChromeOptions chromeOptions = new ChromeOptions();
            //        chromeOptions.addArguments("--headless"); //开启无头浏览器
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--window-size=1920,1080");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(chromeOptions); // 新建一个WebDriver 的对象，但是new 的是谷歌的驱动
            WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            driver.get("https://v.douyin.com/rQyV83P/");
            //
            webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"login-pannel\"]/div[2]"))));
            driver.findElement(By.xpath("//*[@id=\"login-pannel\"]/div[2]")).click();


//            ((JavascriptExecutor) driver).executeScript("document.getElementsByName(\"qsny\")[0].value ='" + previousMonth + "';");
            webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div/div[1]/div[2]/div/xg-controls/xg-inner-controls/xg-left-grid/xg-icon[1]/div[1]"))));

            driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div/div[1]/div[2]/div/xg-controls/xg-inner-controls/xg-left-grid/xg-icon[1]/div[1]")).click();
            //                          //*[@id="root"]/div[1]/div[2]/div/div/div[1]/div[2]/div/xg-video-container/xg-start/xg-start-inner/svg[1]

            Thread.sleep(23000);
            driver.quit();
        } catch (Exception e) {
            System.out.println("出错了");
            if(driver != null){
                driver.quit();
            }
        }
    }
    @Test
    public void test5() {

        int success = 0;
        int fail = 0;
        while (true) {
            WebDriver driver = null;

            try {
                Thread.sleep(1000);

                System.setProperty("webdriver.chrome.driver", "D:\\application\\chromedriver_win32 (2)\\chromedriver.exe");// chromedriver服务地址
                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.addArguments("--headless"); //开启无头浏览器
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                driver = new ChromeDriver(chromeOptions); // 新建一个WebDriver 的对象，但是new 的是谷歌的驱动
                WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                driver.get("https://v.douyin.com/rQyV83P/");
                //
                webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"login-pannel\"]/div[2]"))));
                driver.findElement(By.xpath("//*[@id=\"login-pannel\"]/div[2]")).click();


//            ((JavascriptExecutor) driver).executeScript("document.getElementsByName(\"qsny\")[0].value ='" + previousMonth + "';");
                webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div/div[1]/div[2]/div/xg-controls/xg-inner-controls/xg-left-grid/xg-icon[1]/div[1]"))));

                driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div/div[1]/div[2]/div/xg-controls/xg-inner-controls/xg-left-grid/xg-icon[1]/div[1]")).click();
                //                          //*[@id="root"]/div[1]/div[2]/div/div/div[1]/div[2]/div/xg-video-container/xg-start/xg-start-inner/svg[1]

                Thread.sleep(23000);
                driver.quit();
                success++;
                System.out.println("成功运行了--------》:"+success);
            } catch (Exception e) {
                fail++;
                System.out.println("出错了---------->:"+fail);
                if(driver != null){
                    driver.quit();
                }
            }
        }
    }

}
