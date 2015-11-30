import net.hoyoung.weibospider.vo.TopicPageInfo;
import org.aspectj.util.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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
}
