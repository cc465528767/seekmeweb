package com.example.seekmeweb.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TB_USER")
public class User implements Serializable{

    public User() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "userId",updatable = false, nullable = false)
    private String userId;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "phone1",nullable = false,unique = true)
    private String phone1;


    @Column(name = "phone2")
    private String phone2;

    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "mail")
    private String mail;
    @Column(name = "leaf",nullable = false)
    private int leaf;// 1 运维人员 2 运维组长 3 职能部门 4 其他人员

    @Column(name = "belong",nullable = false)
    private int belong;//部门号

    @Column(name = "jobnumber")
    private String jobnumber;
    @Column(name = "job")
    private String job;
    @Column(name = "photo")
    private String photo;
    @Column(name = "contact1")
    private String emergency_contact1;
    @Column(name = "contact2")
    private String emergency_contact2;
    @Column(name = "gender",nullable = false)
    private int gender;
    @Column(unique = true)
    private String token;
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "TB_user_notification",joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    //1、关系维护端，负责多对多关系的绑定和解除
    //2、@JoinTable注解的name属性指定关联表的名字，joinColumns指定外键的名字，关联到关系维护端(User)
    //3、inverseJoinColumns指定外键的名字，要关联的关系被维护端(Authority)
    //4、其实可以不使用@JoinTable注解，默认生成的关联表名称为主表表名+下划线+从表表名，
    //即表名为user_authority
    //关联到主表的外键名：主表名+下划线+主表中的主键列名,即user_id
    //关联到从表的外键名：主表中用于关联的属性名+下划线+从表的主键列名,即authority_id
    //主表就是关系维护端对应的表，从表就是关系被维护端对应的表
    private List<Notification> notificationList =new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getLeaf() {
        return leaf;
    }

    public void setLeaf(int leaf) {
        this.leaf = leaf;
    }

    public int getBelong() {
        return belong;
    }

    public void setBelong(int belong) {
        this.belong = belong;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmergency_contact1() {
        return emergency_contact1;
    }

    public void setEmergency_contact1(String emergency_contact1) {
        this.emergency_contact1 = emergency_contact1;
    }

    public String getEmergency_contact2() {
        return emergency_contact2;
    }

    public void setEmergency_contact2(String emergency_contact2) {
        this.emergency_contact2 = emergency_contact2;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }
}
