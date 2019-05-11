package com.example.seekmeweb.Bean;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_Client")
public class ClientCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "userId",updatable = false, nullable = false,unique = true)
    private String userId;

    @Column(name = "clientID")
    private String clientId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
