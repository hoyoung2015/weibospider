package net.hoyoung.weibospider.entity;

import java.util.Date;

/**
 * Created by Administrator on 2015/12/1.
 */
public class Topic {
    private int topicId;
    private String title;
    private Date createTime;

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
