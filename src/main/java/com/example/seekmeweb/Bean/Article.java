package com.example.seekmeweb.Bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_Article")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "paperid",updatable = false, nullable = false)
    private Integer paperid;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="publisher", nullable = false)
    private String publisher;

    @Column(name="type", nullable = false)
    private int type;

    @Column(name="likenumber", nullable = false)
    private int likenumber;

    public Integer getPaperid() {
        return paperid;
    }

    public void setPaperid(Integer paperid) {
        this.paperid = paperid;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLikenumber() {
        return likenumber;
    }

    public void setLikenumber(int likenumber) {
        this.likenumber = likenumber;
    }
}
