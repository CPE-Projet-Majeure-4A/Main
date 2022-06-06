package com.majeur.projet.threading;

import javax.persistence.*;
import java.util.List;

@Entity
public class ThreadEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<MissionEntity> missions;

    private String name;

    public ThreadEntity() {
    }

    public ThreadEntity(int id, List<MissionEntity> missions, String name) {
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

    public List<MissionEntity> getMissions() {
        return missions;
    }

    public void setMissions(List<MissionEntity> missions) {
        this.missions = missions;
    }

    @Override
    public String toString() {
        return "THREAD ["+this.id+"]: missions:"+this.missions.toString();
    }

}
