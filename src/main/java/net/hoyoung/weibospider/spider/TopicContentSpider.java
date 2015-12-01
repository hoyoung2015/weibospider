package net.hoyoung.weibospider.spider;

import net.hoyoung.weibospider.entity.DocInfo;
import net.hoyoung.weibospider.utils.ContentFilter;
import net.hoyoung.weibospider.vo.TopicPageInfo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2015/11/30.
 */
public class TopicContentSpider extends BaseSpider{
    private static String firstPageUrl = "http://weibo.com/p/<page_id>?pids=Pl_Third_App__9&feed_sort=timeline&feed_filter=timeline#Pl_Third_App__9";
    //page,current_page
    private static String targetAjaxUrlTpl = "http://weibo.com/p/aj/v6/mblog/mbloglist?ajwvr=6&domain=100808&feed_sort=timeline&feed_filter=timeline&pre_page=<page>&page=<page>&max_id=&end_id=3915204376910076&pagebar=<pagebar>&filtered_min_id=&pl_name=Pl_Third_App__9&id=<page_id>&script_uri=/p/<page_id>&feed_type=1&";
    private TopicPageInfo topicPageInfo;

    public TopicContentSpider(TopicPageInfo topicPageInfo) {
        this.topicPageInfo = topicPageInfo;
    }
//3915182604511858
    /**
     * 首页url
     * http://weibo.com/p/100808e03b5c8e200b73525215d47c44652703?pids=Pl_Third_App__9&feed_sort=timeline&feed_filter=timeline#Pl_Third_App__9
     * http://weibo.com/p/<page_id>?pids=Pl_Third_App__9&feed_sort=timeline&feed_filter=timeline#Pl_Third_App__9
     *
     * 下一页
     * http://weibo.com/p/<page_id>?pids=Pl_Third_App__9&feed_filter=timeline&feed_sort=timeline&current_page=3&since_id=%7B%22last_since_id%22%3A3915183917408129%2C%22res_type%22%3A1%2C%22next_since_id%22%3A3915152157803865%7D&page=2#Pl_Third_App__9
     */
    public void start(){
        Spider.create(new ContentProcessor())
                .addUrl(firstPageUrl.replace("<page_id>", this.topicPageInfo.getPageId()))//从第一页开始
                .thread(1)
                .run();
    }

    class ContentProcessor implements PageProcessor{
        protected Logger logger = LoggerFactory.getLogger(getClass());
        private AtomicInteger curPageAddsOn = new AtomicInteger(0);
        @Override
        public void process(Page page) {
            Html html = null;
            if(page.getUrl().regex("mblog/mbloglist").match()){//ajax
                html = new Html(page.getJson().jsonPath("$.data").get());
                logger.info("ajax请求");
            }else {//普通翻页
                try {
                    FileUtils.writeStringToFile(new File("data/jsonTemp.json"), page.getRawText());
                    String jsonStr = FileUtils.readFileToString(new File("data/jsonTemp.json"));
                    jsonStr = new Html(jsonStr).xpath("//script[35]").replace("<script>FM\\.view\\(|\\)</script>", "").get();
                    jsonStr = new JsonPathSelector("$.html").select(jsonStr);
                    html = new Html(jsonStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info("普通翻页");
            }
            for (Selectable sele:html.xpath("//div[@class='WB_cardwrap WB_feed_type S_bg2']").nodes()){
//                String content = HtmlRegexpUtil.filterHtml(sele.xpath("//div[@class='WB_text W_f14']").get());
                String content = sele.xpath("//div[@class='WB_text W_f14']/text()").get();

                DocInfo docInfo = new DocInfo();
                docInfo.setContent(ContentFilter.filter(content));
                docInfo.setUserHome(sele.xpath("//a[@class='W_face_radius']/@href").get());
                docInfo.setMid(sele.xpath("/div/@mid").get());
                String tmp = sele.xpath("//div[@class='WB_from S_txt2']/a[@class='S_txt2']/@date").get();
                docInfo.setPubTime(new Date(Long.valueOf(tmp)));
                logger.info(docInfo.toString());
            }
            Selectable pagerDiv = html.xpath("//div[@class='W_pages']");

            int pageNum = curPageAddsOn.addAndGet(1)/3 + 1;//计算页码

            if(pagerDiv.get()!=null){//有分页
                //找下一页url
                Selectable nextPage = pagerDiv.xpath("//a[@class='page next S_txt1 S_line1']");
                if(nextPage.get()!=null){//有下一页
                    page.addTargetRequest("http://weibo.com"+nextPage.xpath("/a/@href").get());
                    logger.info("进入下一页:http://weibo.com" + nextPage.xpath("/a/@href").get());
                }else {
                    logger.info("全部结束");
                }
            }else {//继续ajax
                logger.info("继续ajax");
                String tmp = html.xpath("//div[@class='WB_empty']/@action-data").get()+"&domain_op=100808&__rnd="+System.currentTimeMillis();
                String newUrl = targetAjaxUrlTpl.replace("<pagebar>",curPageAddsOn.get()%3==2?"1":"0")
                        .replace("<page_id>", TopicContentSpider.this.topicPageInfo.getPageId())
                        .replace("<page>",pageNum+"")+tmp;

                logger.info(newUrl);
                page.addTargetRequest(newUrl);//下一页ajax请求或者页面请求
            }
            site.setSleepTime(15000+new Random().nextInt(10000));
            logger.info("等待"+site.getSleepTime()/1000+"秒");
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
                    .setSleepTime(10000)
                    .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36")
//                    .addHeader("Host","huati.weibo.com")
                    .addHeader("Cookie", TopicContentSpider.this.cookieStr)
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("Accept", "text/html, application/xhtml+xml, */*");
        }
    }
}
