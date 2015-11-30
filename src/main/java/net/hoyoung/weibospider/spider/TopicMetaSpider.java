package net.hoyoung.weibospider.spider;

import net.hoyoung.weibospider.spider.BaseSpider;
import net.hoyoung.weibospider.vo.TopicPageInfo;
import org.aspectj.util.FileUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/30.
 */
public class TopicMetaSpider extends BaseSpider{

    public TopicMetaSpider(String keyword) {
        this.setKeyword(keyword);
    }

    private TopicPageInfo topicPageInfo;
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public TopicPageInfo getTopicPageInfo() {
        if(topicPageInfo==null){
            Spider spider = Spider.create(new TopicMetaProcessor())
                    .addUrl("http://huati.weibo.com/k/" + keyword + "?from=501")
                    .thread(1);
            spider.run();
            spider.close();
        }
        return topicPageInfo;
    }

    public static void main(String[] args) {
//        Spider spider = Spider.create(new TopicMetaProcessor())
//                .addUrl("http://huati.weibo.com/k/" + "夏洛特烦恼" + "?from=501")
//                .thread(1);
//        spider.run();
//        spider.close();
    }

    public void setTopicPageInfo(TopicPageInfo topicPageInfo) {
        this.topicPageInfo = topicPageInfo;
    }
    public class TopicMetaProcessor implements PageProcessor{

        private Site site;
        public TopicMetaProcessor() {
            Map<String,Object> header = new HashMap<>();
            header.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");
            header.put("User-Agent","");
            site = Site.me()
                    .setRetryTimes(5)
                    .setSleepTime(1000)
                    .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36")
//                    .addHeader("Host","huati.weibo.com")
                    .addHeader("Cookie", TopicMetaSpider.this.cookieStr)
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("Accept", "text/html, application/xhtml+xml, */*");
        }

        @Override
        public void process(Page page) {
            TopicMetaSpider.this.topicPageInfo = new TopicPageInfo(page.getHtml().regex("CONFIG\\['page_id'\\]='([0-9a-z]+)';",1).get(),page.getHtml().regex("CONFIG\\['oid'\\]='([0-9a-z]+)';",1).get());
        }
        @Override
        public Site getSite() {
            return site;
        }
    }
}
