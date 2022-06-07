package com.majeur.projet.resourceManager;

import com.majeur.projet.apiCommunication.*;
import com.majeur.projet.movement.MoveFunctions;
import com.majeur.projet.threading.MissionEntity;
import com.majeur.projet.threading.ThreadEntity;
import com.majeur.projet.threading.ThreadRepository;
import com.majeur.projet.threading.VehicleState;

import java.util.Arrays;
import java.util.List;

public class ResourceManagerRunnable implements Runnable{

    private final ThreadRepository hrepo;
    boolean isEnd = false;

    public ResourceManagerRunnable(ThreadRepository hrepo) {
        this.hrepo = hrepo;
    }

    @Override
    public void run() {
        while (!this.isEnd) {
            try {
                Thread.sleep(2000);

                for (ThreadEntity h : this.hrepo.findAll()) {

                    synchronized(hrepo){
                        List<MissionEntity> missions = h.getMissions();
                        System.out.println("Move: "+ missions);
                        FacilityObject facility = StaticGet.getTeamFacility();
                        List<FireObject> fires = Arrays.asList(StaticGet.getFires());
                        //List<FireObject> firesInArea = SendVehicle.GetFireInArea(fires, facility);

                        for(MissionEntity mission : missions) {
                            //TODO
                        }
                        h.setMissions(missions);
                        hrepo.save(h);
                    }


                }

                System.out.println("Thread resource loop");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Runnable ResourceManager ends.... ");
    }

    public void stop() {
        this.isEnd = true;
    }

    public void update(){

    }
}


