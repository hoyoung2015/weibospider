package net.hoyoung.weibospider.spider;

import net.hoyoung.weibospider.vo.TopicPageInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/30.
 */
public class TopicContentSpider extends BaseSpider{
    //pageId,page
    private static String pageUrl = "http://weibo.com/p/%s?feed_filter=timeline&feed_sort=timeline&current_page=3&page=%s#Pl_Third_App__9";
    //page,current_page
    private static String pageUrlAjax = "http://weibo.com/p/aj/v6/mblog/mbloglist?ajwvr=6&domain=100808&feed_sort=timeline&feed_filter=timeline&pre_page=1&page=[page]&max_id=&end_id=3914914906859901&pagebar=0&filtered_min_id=&pl_name=Pl_Third_App__9&id=100808e03b5c8e200b73525215d47c44652703&script_uri=/p/100808e03b5c8e200b73525215d47c44652703&feed_type=1&tab=home&current_page=[current_page]&domain_op=100808&__rnd=1448872503591";
    private TopicPageInfo topicPageInfo;

    public TopicContentSpider(TopicPageInfo topicPageInfo) {
        this.topicPageInfo = topicPageInfo;
    }
    public void start(){
        Spider.create(new ContentProcessor())
//                .addUrl(String.format(pageUrl,topicPageInfo.getPageId(),1))
                .addUrl(pageUrlAjax.replace("[current_page]",1+"").replace("[page]","1"))
                .thread(1)
                .run();
    }

    class ContentProcessor implements PageProcessor{

        @Override
        public void process(Page page) {
            System.out.println(page.getRawText());
            if(page.getUrl().regex("mblog/mbloglist").match()){//ajax
                System.out.println(page.getJson().jsonPath("$.data"));
                page.getJson().jsonPath("$.data");
                System.out.println("ajax翻页");
            }else {//普通翻页
                System.out.println("普通翻页");
            }

        }

        private Site site;
        @Override
        public Site getSite() {
            return site;
        }
        public ContentProcessor() {
            Map<String,Object> header = new HashMap<>();
            header.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");
            header.put("User-Agent","");
            site = Site.me()
                    .setRetryTimes(5)
                    .setSleepTime(1000)
                    .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36")
//                    .addHeader("Host","huati.weibo.com")
                    .addHeader("Cookie", TopicContentSpider.this.cookieStr)
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("Accept", "text/html, application/xhtml+xml, */*");
        }
    }
}
