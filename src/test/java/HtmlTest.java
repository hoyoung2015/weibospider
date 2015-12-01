import net.hoyoung.weibospider.vo.TopicPageInfo;
import org.aspectj.util.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/30.
 */
public class HtmlTest {
    @Test
    public void test(){
        try {
            String html = FileUtil.readAsString(new File("data/a.html"));
            System.out.println(TopicPageInfo.parse(html));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void test2(){
        long time = 1448858665000l;
        new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:m:s");
        System.out.println(sdf.format(new Date(time)));
    }
}
