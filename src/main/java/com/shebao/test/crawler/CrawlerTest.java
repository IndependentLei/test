package com.shebao.test.crawler;

import com.sun.javafx.fxml.builder.URLBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CrawlerTest {
    @Test
    public void test1() throws IOException {
        // 1、打开浏览器    创建一个HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 2、输入网址，发起get请求
        HttpGet httpGet = new HttpGet("https://www.vip.com");
        // 3、按回车,发起请求
        CloseableHttpResponse response = httpclient.execute(httpGet);
        // 4、解析请求，获取数据 ,判断状态码
        if(response.getStatusLine().getStatusCode() == 200){
            // 拿到静态数据
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            log.debug("抓取的数据为:{}",s);
        }
    }

    /**
     * get请求
     * @throws URISyntaxException
     */
    @Test
    public void test2() throws URISyntaxException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 带参数的get请求
        URIBuilder urlBuilder = new URIBuilder("https://yun.itheima.com/search");
        urlBuilder.setParameter("keys","Java");
        HttpGet httpGet = new HttpGet(urlBuilder.build());
        CloseableHttpResponse res = null;
        try{
            res = httpclient.execute(httpGet);
            // 判断状态码
            if (res.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = res.getEntity();
                String s = EntityUtils.toString(entity);
                log.debug("抓取的数据的长度为:{}",s.length());
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(res != null) {
                try {
                    // 关闭资源

                    res.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * post请求
     */
    @Test
    public void test3(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("Https://www.baidu.com");
        CloseableHttpResponse res = null;
        try {
            res = httpClient.execute(httpPost);
            if(res.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = res.getEntity();
                String s = EntityUtils.toString(entity);
                log.debug("数据为:{}",s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (res != null) {
                try {
                    res.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 表单post请求
     */
    @Test
    public void test4() throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("Https://www.baidu.com");
        // 封装表单中的参数
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("keys","Java"));

        // 创建表单的Entity对象   第一个参数为表单数据，第二个是编码
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params,"utf-8");
        httpPost.setEntity(formEntity);

        CloseableHttpResponse res = null;
        try {
            res = httpClient.execute(httpPost);
            if(res.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = res.getEntity();
                String s = EntityUtils.toString(entity);
                log.debug("数据为:{}",s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (res != null) {
                try {
                    res.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test5(){
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // 设置最大连接数
        cm.setMaxTotal(100);
        // 设置每个主机的最大连接数
        cm.setDefaultMaxPerRoute(10);
        CrawlerTest.doGet(cm,"Https://www.baidu.com");
    }

    public static void doGet(PoolingHttpClientConnectionManager cm,String website){
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        HttpGet httpGet = new HttpGet(website);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if ( response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity,"utf-8");
                log.debug("数据为:{}",s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null ){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // httpclient不能关闭，因为使用的HttpClient连接池
        }
    }


    @Test
    public void test6(){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://yun.itheima.com/search");
        // 配置请求信息
        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000)// 创建连接的最大时长，单位是毫秒
                .setConnectionRequestTimeout(500)// 设置获取连接的最长时间，单位是毫秒
                .setSocketTimeout(10*1000).build();// 设置数据传输的最长时间,单位是毫秒

        httpGet.setConfig(config);

        CloseableHttpResponse res = null;
        try{
            res = httpclient.execute(httpGet);
            // 判断状态码
            if (res.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = res.getEntity();
                String s = EntityUtils.toString(entity,"utf-8");
                log.debug("抓取的数据的长度为:{}",s.length());
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(res != null) {
                try {
                    // 关闭资源

                    res.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
