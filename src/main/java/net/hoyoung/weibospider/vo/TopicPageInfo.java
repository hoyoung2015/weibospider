package net.hoyoung.weibospider.vo;

import us.codecraft.webmagic.selector.RegexSelector;

/**
 * Created by Administrator on 2015/11/30.
 */
public class TopicPageInfo {
    private String pageId;
    private String oid;
    private static RegexSelector pageIdSelector = new RegexSelector("CONFIG\\['page_id'\\]='([0-9a-z]+)';",1);
    private static RegexSelector oidSelector = new RegexSelector("CONFIG\\['oid'\\]='([0-9a-z]+)';",1);
    public static TopicPageInfo parse(String html){
        TopicPageInfo topicPageInfo = new TopicPageInfo();
        topicPageInfo.setPageId(pageIdSelector.select(html));
        topicPageInfo.setOid(oidSelector.select(html));
        return topicPageInfo;
    }
    private TopicPageInfo(){

    }

    public TopicPageInfo(String pageId, String oid) {
        this.pageId = pageId;
        this.oid = oid;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Override
    public String toString() {
        return "TopicPageInfo{" +
                "pageId='" + pageId + '\'' +
                ", oid='" + oid + '\'' +
                '}';
    }
}
