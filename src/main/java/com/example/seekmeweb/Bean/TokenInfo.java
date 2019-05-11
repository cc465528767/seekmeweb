package com.example.seekmeweb.Bean;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "TB_TOKENINOF",schema = "jwt")
public class TokenInfo implements Serializable
{
    @Id
    @GeneratedValue
    @Column(name = "ati_id")
    private Long id;
    @Column(name = "phone1")
    private  String phone1;
    @Column(name = "ati_token")
    private byte[] token;
    @Column(name = "ati_build_time")
    private String buildTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }
}
