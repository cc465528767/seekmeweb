package com.example.seekmeweb.Bean;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_TRACE")
public class Trace implements Serializable{
    public Trace(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "userId", nullable = false )
    private String userId;

    @Column(name = "trace_starttime", nullable = false )
    private String startTime;

    @Column(name = "trace_realtime", nullable = false )
    private String realTime;


    @Column(name = "trace_date", nullable = false )
    private String date;

    @Column(name = "trace_pic", nullable = false )
    private String tracePic;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTracePic() {
        return tracePic;
    }

    public void setTracePic(String tracePic) {
        this.tracePic = tracePic;
    }
}
