package net.hoyoung.weibospider;

import net.hoyoung.weibospider.spider.TopicContentSpider;
import net.hoyoung.weibospider.spider.TopicMetaSpider;
import net.hoyoung.weibospider.vo.TopicPageInfo;
import org.aspectj.util.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2015/11/30.
 */
public class Test {
    public static void main(String[] args) throws IOException {

        String cookieStr = FileUtil.readAsString(new File("weibo_cookie.txt"));

        TopicMetaSpider topicMetaSpider = new TopicMetaSpider("");
        topicMetaSpider.setCookieStr(cookieStr);
        TopicPageInfo topicPageInfo = topicMetaSpider.getTopicPageInfo();
        System.out.println(topicPageInfo);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TopicContentSpider topicContentSpider = new TopicContentSpider(topicPageInfo);
        topicContentSpider.setCookieStr(cookieStr);
        topicContentSpider.start();
    }
}
