package com.example.seekmeweb.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "TB_NOTIFICATION")
public class Notification implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "sendername", updatable = false, nullable = false)
    private String sendername;

    @Column(name = "notitime", nullable = false)
    private String notitime;

    @Column(name = "notiday", nullable = false)
    private String notiday;

    @Column(name = "sendtime", nullable = false)
    private String sendtime;

    @Column(name = "sendday", nullable = false)
    private String sendday;

    @Column(name = "title", nullable = false)
    private String title;


    @Column(name = "content", nullable = false)
    private String content;
/*
    @ManyToMany(mappedBy = "notificationList", fetch = FetchType.LAZY)
    private Set<User> userList = new HashSet<>();*/

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getNotitime() {
        return notitime;
    }

    public void setNotitime(String notitime) {
        this.notitime = notitime;
    }

    public String getNotiday() {
        return notiday;
    }

    public void setNotiday(String notiday) {
        this.notiday = notiday;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getSendday() {
        return sendday;
    }

    public void setSendday(String sendday) {
        this.sendday = sendday;
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

   /* public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }*/
}
