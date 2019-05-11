package com.example.seekmeweb.Bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TB_MESSAGE")
public class Message implements Serializable {
    public static final int RECEIVER_TYPE_NONE = 1;
    // 发送给群的
    public static final int RECEIVER_TYPE_GROUP = 2;

    public static final int TYPE_STR = 1; // 字符串类型
    public static final int TYPE_PIC = 2; // 图片类型
    public static final int TYPE_FILE = 3; // 文件类型
    public static final int TYPE_AUDIO = 4; // 语音类型
    @Id
    @PrimaryKeyJoinColumn
    // 主键生成存储的类型为UUID
    // 这里不自动生成UUID，Id由代码写入，由客户端负责生成
    // 避免复杂的服务器和客户端的映射关系
    //@GeneratedValue(generator = "uuid")
    // 把uuid的生成器定义为uuid2，uuid2是常规的UUID toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    // 不允许更改，不允许为null
    @Column(updatable = false, nullable = false)
    private String id;
    @Column(name = "sentid",updatable = false, nullable = false)
    private String sentid;
    @Column(name = "receiveid",updatable = false, nullable = false)
    private String receiveid;



    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 附件
    @Column
    private String attach;

    // 消息类型
    @Column(nullable = false)
    private int type;

    @Column(name="CREATE_TIME",nullable = false)
    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    @Column(name="updatetime",nullable = false)
    @LastModifiedDate
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date updatetime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getSentid() {
        return sentid;
    }

    public void setSentid(String sentid) {
        this.sentid = sentid;
    }

    public String getReceiveid() {
        return receiveid;
    }

    public void setReceiveid(String receiveid) {
        this.receiveid = receiveid;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



}
