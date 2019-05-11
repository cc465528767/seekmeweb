package com.example.seekmeweb.Bean;

import javax.persistence.*;

@Entity
@Table(name = "TB_GTsave")
public class SaveMe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "seekeruserid",updatable = false, nullable = false)
    private String userid;

    @Column(name = "createtime",updatable = false, nullable = false)
    private String createTime;

    @Column(name = "title",updatable = false, nullable = false)
    private String title;

    @Column(name = "content",updatable = false, nullable = false)
    private String content;

    @Column(name = "cid",updatable = false, nullable = false)
    private String cid;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
