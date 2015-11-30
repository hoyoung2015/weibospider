package net.hoyoung.weibospider.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hoyoung on 2015/11/30.
 */
@Entity
@Table(name = "doc_info", schema = "", catalog = "weibospider")
public class DocInfo {
    private int id;
    private Date pubTime;
    private String content;
    private Date createTime;
    private String mid;
    private String userHome;

    public DocInfo() {
        createTime = new Date();
    }

    @Override
    public String toString() {
        return "DocInfo{" +
                "id=" + id +
                ", pubTime=" + pubTime +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", mid='" + mid + '\'' +
                ", userHome='" + userHome + '\'' +
                '}';
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pub_time")
    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "mid")
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    @Basic
    @Column(name = "user_home")
    public String getUserHome() {
        return userHome;
    }

    public void setUserHome(String userHome) {
        this.userHome = userHome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocInfo docInfo = (DocInfo) o;

        if (id != docInfo.id) return false;
        if (pubTime != null ? !pubTime.equals(docInfo.pubTime) : docInfo.pubTime != null) return false;
        if (content != null ? !content.equals(docInfo.content) : docInfo.content != null) return false;
        if (createTime != null ? !createTime.equals(docInfo.createTime) : docInfo.createTime != null) return false;
        if (mid != null ? !mid.equals(docInfo.mid) : docInfo.mid != null) return false;
        if (userHome != null ? !userHome.equals(docInfo.userHome) : docInfo.userHome != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pubTime != null ? pubTime.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (mid != null ? mid.hashCode() : 0);
        result = 31 * result + (userHome != null ? userHome.hashCode() : 0);
        return result;
    }
}
