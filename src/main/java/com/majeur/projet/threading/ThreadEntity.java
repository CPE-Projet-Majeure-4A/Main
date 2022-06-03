package com.majeur.projet.threading;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ThreadEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    public ThreadEntity() {
    }

    public ThreadEntity(int id, String name, int superPowerValue) {
        super();
        this.id=id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HERO ["+this.id+"]: name:"+this.name;
    }
}
