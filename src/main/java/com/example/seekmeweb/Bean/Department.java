package com.example.seekmeweb.Bean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_DEPARTMENT")
public class Department implements Serializable{
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            updatable = false,
            nullable = false
    )
    private Integer id;
    @Column(
            name="name",
            nullable = false
    )
    private String name;
    @Column(
            name="superior",
            nullable = false
    )
    private int superior;
    @Column(
            name="level",
            nullable = false
    )
    private int level;

    public Department(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSuperior() {
        return superior;
    }

    public void setSuperior(int superior) {
        this.superior = superior;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
