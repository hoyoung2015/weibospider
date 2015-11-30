package net.hoyoung.weibospider.test;

import net.hoyoung.weibospider.entity.DocInfo;
import net.hoyoung.weibospider.utils.ContentFilter;
import net.hoyoung.weibospider.utils.HtmlRegexpUtil;
import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.IOException;

/**
 * Created by hoyoung on 2015/11/30.
 */
public class HtmlParse {
    public static void main(String[] args) {
        try {
            String str = FileUtils.readFileToString(new File("data/topic.html"), "UTF-8");
            Html html = new Html(str);
            for (Selectable sele:html.xpath("//div[@class='WB_cardwrap WB_feed_type S_bg2']").nodes()){
//                String content = HtmlRegexpUtil.filterHtml(sele.xpath("//div[@class='WB_text W_f14']").get());
                String content = sele.xpath("//div[@class='WB_text W_f14']/text()").get();

                DocInfo docInfo = new DocInfo();
                docInfo.setContent(ContentFilter.filter(content));
                docInfo.setUserHome(sele.xpath("//a[@class='W_face_radius']/@href").get());
                docInfo.setMid(sele.xpath("/div/@mid").get());

                System.out.println(docInfo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
