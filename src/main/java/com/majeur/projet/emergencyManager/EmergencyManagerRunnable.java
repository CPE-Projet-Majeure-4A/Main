package com.majeur.projet.emergencyManager;

import com.majeur.projet.apiCommunication.FacilityObject;
import com.majeur.projet.apiCommunication.FireObject;
import com.majeur.projet.apiCommunication.StaticGet;
import com.majeur.projet.apiCommunication.VehicleObject;
import com.majeur.projet.emergencyManager.vehicle.SendVehicle;
import com.majeur.projet.threading.MissionObject;
import com.majeur.projet.threading.ThreadEntity;
import com.majeur.projet.threading.ThreadRepository;
import com.majeur.projet.threading.VehicleState;

import java.util.Arrays;
import java.util.List;

public class EmergencyManagerRunnable implements Runnable {

    private final ThreadRepository hrepo;
    boolean isEnd = false;

    public EmergencyManagerRunnable(ThreadRepository hrepo) {
        this.hrepo = hrepo;
    }

    @Override
    public void run() {
        while (!this.isEnd) {
            try {
                Thread.sleep(10000);

                for (ThreadEntity h : this.hrepo.findAll()) {
                    System.out.println(h.toString());
                    // TODO fix problème plusieurs thread entity ??
                    if(h.getMissions() == null){
                        continue;
                    }
                    System.out.println("OK");
                    List<MissionObject> missions = h.getMissions();
                    FacilityObject facility = StaticGet.getTeamFacility();
                    List<FireObject> fires = Arrays.asList(StaticGet.getFires());
                    List<FireObject> firesInArea = SendVehicle.GetFireInArea(fires, facility);

                    synchronized(hrepo){
                        for(MissionObject mission : missions){
                            if(mission.getVehicleState().equals(VehicleState.AT_FACILITY) ||
                                mission.getVehicleState().equals(VehicleState.GOING_TO_FACILITY)){

                                VehicleObject vehicle = StaticGet.getVehicleById(String.valueOf(mission.getVehicleId()));
                                MissionObject newMission =
                                        SendVehicle.SelectVehicle(firesInArea, mission.getVehicleId(), facility.getId(), vehicle);

                                mission.setDestinationId(newMission.getDestinationId());
                                mission.setVehicleState(newMission.getVehicleState());
                            }

                        }
                    }

                }
                System.out.println("Thread affect loop");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Runnable DisplayRunnable ends.... ");
    }

    public void stop() {
        this.isEnd = true;
    }

}

