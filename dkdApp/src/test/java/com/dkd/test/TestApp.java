package com.dkd.test;

import com.dkd.framework.service.SpiderService;
import com.dkd.framework.service.SpiderUtils;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-context.xml")
public class TestApp {
    @Value("${reqTimeOut}")
    private int reqTimeOut;
    @Value("${PHPSESSID}")
    private String PHPSESSID;
    @Resource
    private SpiderService spiderService;
    @Resource
    private SpiderUtils spiderUtils;

    //@Test
    public void test() {
        Document document = spiderUtils.getKaoPuNiaoHtml("https://kaopuniao.com/invest/detail/JBWIS20160000703633A");
        String signImg = "";
        for (Element img : document.select(".pic_befor > div").select("img")) {
            signImg += img.attr("src") + ";";
        }
        System.err.println(signImg);

    }

    @Test
    public void testSynData() {

        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semp = new Semaphore(20);

        for (int index = 0; index < 20; index++) {

            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        semp.acquire();
                        spiderService.getSynNonoBankData(NO, NO * 1000, 10);
                        semp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
        exec.shutdown();

        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSynDetail() {
        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semp = new Semaphore(20);

        for (int index = 0; index < 20; index++) {

            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        semp.acquire();
                        spiderService.updateNonoBankDetail(NO, NO * 1000, 10);
                        semp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
        exec.shutdown();

        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void testCookieLogin() throws IOException {
        Connection con = Jsoup.connect("https://www.nonobank.com/Lend/View/" + "24759");
        //设置访问形式（电脑访问，手机访问）：直接百度都参数设置
        con.header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
        //把登录信息的cookies保存如map对象里面
        con = con.cookie("PHPSESSID", PHPSESSID);
        Document objectDoc = con.timeout(10000).get();
        //System.err.println(objectDoc);
        System.err.println("身份：" + objectDoc.select("#tabs-1 > div > div.basic_box > div.box_left > p").text());
        System.err.println("学历：" + objectDoc.select("#tabs-1 > div > div.basic_box > div.box_right > div:eq(7)").text());
        System.err.println("婚姻：" + objectDoc.select("#tabs-1 > div > div.basic_box > div.box_right > div:eq(5)").text());

    }

    //@Test
    public void testSynUser() {
        spiderService.synUser();
    }


    // @Test
    public void testBirdDetail() {
        spiderService.getBirdList();
        spiderService.synBirdDetail();
    }

    //@Test
    public void testBirdApi() {
        spiderService.getBirdListByApi();
        spiderService.synBirdDetail();
    }

    //@Test
    public void testThread() {
        ExecutorService exec = Executors.newCachedThreadPool();
        // 20个线程可以同时访问
        final Semaphore semp = new Semaphore(20);
        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        semp.acquire();
                        semp.release();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            };
            exec.execute(run);
        }
        exec.shutdown();
    }

}
