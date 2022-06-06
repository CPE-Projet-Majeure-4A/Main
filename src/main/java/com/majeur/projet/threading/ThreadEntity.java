package com.majeur.projet.threading;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class ThreadEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Transient
    private List<MissionObject> missions;

    private String name;

    public ThreadEntity() {
    }

    public ThreadEntity(int id, List<MissionObject> missions, String name) {
        super();
        this.id=id;
        this.missions = missions;
        this.name = name;
    }

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

    public List<MissionObject> getMissions() {
        return missions;
    }

    public void setMissions(List<MissionObject> missions) {
        this.missions = missions;
    }

    @Override
    public synchronized String toString() {
        return "THREAD ["+this.id+"]: missions:"+this.missions.toString();
    }

}
