package com.example.seekmeweb.Bean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_SOS")
public class Sos implements Serializable {
    public Sos(){
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "sos_Id",updatable = false, nullable = false,unique = true)
    private String sosId;


    @Column(name = "sos_date",updatable = false, nullable = false)
    private String date;


    @Column(name = "sos_createtime",updatable = false, nullable = false)
    private String createTime;


    @Column(name = "seekerId",updatable = false, nullable = false)
    private String seekerId;

    @Column(name = "helperId")
    private String helperId;

    @Column(name = "sos_pic",updatable = false, nullable = false)
    private String sosPic;

    @Column(name = "seekerName",updatable = false, nullable = false)
    private String seekerName;

    @Column(name = "helperName")
    private String helperName;

    @Column(name = "sosLat",updatable = false, nullable = false)
    private String sosLatitude;

    @Column(name = "sosLng",updatable = false, nullable = false)
    private String sosLongitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(String seekerId) {
        this.seekerId = seekerId;
    }

    public String getHelperId() {
        return helperId;
    }

    public void setHelperId(String helperId) {
        this.helperId = helperId;
    }

    public String getSosPic() {
        return sosPic;
    }

    public void setSosPic(String sosPic) {
        this.sosPic = sosPic;
    }

    public String getSeekerName() {
        return seekerName;
    }

    public void setSeekerName(String seekerName) {
        this.seekerName = seekerName;
    }

    public String getHelperName() {
        return helperName;
    }

    public void setHelperName(String helperName) {
        this.helperName = helperName;
    }

    public String getSosLatitude() {
        return sosLatitude;
    }

    public void setSosLatitude(String sosLatitude) {
        this.sosLatitude = sosLatitude;
    }

    public String getSosId() {
        return sosId;
    }

    public void setSosId(String sosId) {
        this.sosId = sosId;
    }

    public String getSosLongitude() {
        return sosLongitude;
    }

    public void setSosLongitude(String sosLongitude) {
        this.sosLongitude = sosLongitude;
    }
}
