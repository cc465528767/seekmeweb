package com.example.seekmeweb.Bean;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "TB_ArticleLike")
public class ArticleLike implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "supportid",updatable = false, nullable = false)
    private Integer supportid;

    @Column(name="parentsid", nullable = false)
    private Integer parentsid;

    @Column(name="supporterid", nullable = false)
    private Integer supporterid;


    public Integer getSupportid() {
        return supportid;
    }

    public void setSupportid(Integer supportid) {
        this.supportid = supportid;
    }

    public Integer getParentsid() {
        return parentsid;
    }

    public void setParentsid(Integer parentsid) {
        this.parentsid = parentsid;
    }

    public Integer getSupporterid() {
        return supporterid;
    }

    public void setSupporterid(Integer supporterid) {
        this.supporterid = supporterid;
    }
}
