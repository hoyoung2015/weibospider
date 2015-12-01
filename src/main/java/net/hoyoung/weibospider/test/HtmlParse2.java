package net.hoyoung.weibospider.test;

import net.hoyoung.weibospider.entity.DocInfo;
import net.hoyoung.weibospider.utils.ContentFilter;
import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.IOException;

/**
 * Created by hoyoung on 2015/11/30.
 */
public class HtmlParse2 {
    public static void main(String[] args) {
        try {
            String str = FileUtils.readFileToString(new File("data/firstPage.html"), "UTF-8");
            Html html = new Html(str);
            /*for (int i = 1; i <= html.xpath("script").nodes().size(); i++) {
                System.out.println(">>"+"//script["+i+"]");
                System.out.println(html.xpath("//script["+i+"]"));
            }*/
            String jsonStr = html.xpath("//script[35]").replace("<script>FM\\.view\\(|\\)</script>","").get();

            System.out.println(new JsonPathSelector("$.html").select(jsonStr));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
