package com.majeur.projet.movement;

import com.majeur.projet.apiCommunication.*;
import com.majeur.projet.threading.MissionEntity;
import com.majeur.projet.threading.ThreadEntity;
import com.majeur.projet.threading.ThreadRepository;
import com.majeur.projet.threading.VehicleState;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;
import java.util.List;

//TODO Régler problèmes et adapter le code du prof à 2 threads différents
public class MoveRunnable implements Runnable{

    private final ThreadRepository hrepo;
    boolean isEnd = false;

    public MoveRunnable(ThreadRepository hrepo) {
        this.hrepo = hrepo;
    }

    @Override
    public void run() {
        while (!this.isEnd) {
            try {
                Thread.sleep(5000);

                for (ThreadEntity h : this.hrepo.findAll()) {
                    List<MissionEntity> missions = h.getMissions();;
                    FacilityObject facility = StaticGet.getTeamFacility();
                    List<FireObject> fires = Arrays.asList(StaticGet.getFires());
                    //List<FireObject> firesInArea = SendVehicle.GetFireInArea(fires, facility);

                    synchronized(hrepo){
                        for(MissionEntity mission : missions){
                            VehicleObject vehicle = StaticGet.getVehicleById(String.valueOf(mission.getVehicleId()));

                            // On vérifie si le feu existe toujours
                            if(mission.getVehicleState().equals(VehicleState.AT_FIRE) ||
                                    mission.getVehicleState().equals(VehicleState.GOING_TO_FIRE)){
                                    if (!MoveFunctions.doesFireExist(mission)){
                                        mission.setVehicleState(VehicleState.GOING_TO_FACILITY);
                                        continue;
                                    }
                            }

                            if(!MoveFunctions.isVehicleMoving(mission)){
                                continue;
                            }

                            double[] destCoords = MoveFunctions.getDestinationCoords(mission, facility);

                            double[] coords = MoveFunctions.getDestination_Teleportation(destCoords);
                            //double[] coords = MoveFunctions.getDestination_Linear(mission, destCoords);

                            vehicle.setLat(coords[0]);
                            vehicle.setLon(coords[1]);
                            StaticVehicle.addVehicle(vehicle);
                        }
                        h.setMissions(missions);
                    }

                }

                System.out.println("Thread move loop");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Runnable DisplayRunnable ends.... ");
    }

    public void stop() {
        this.isEnd = true;
    }

    public void update(){

    }
}

