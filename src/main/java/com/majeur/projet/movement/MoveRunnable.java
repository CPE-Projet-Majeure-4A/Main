package com.majeur.projet.movement;

import com.majeur.projet.apiCommunication.*;
import com.majeur.projet.emergencyManager.vehicle.SendVehicle;
import com.majeur.projet.threading.MissionObject;
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
                    //System.out.println(h.toString());
                    //TODO fix problème plusieurs thread entity ??
                    if(h.getMissions() == null){
                        continue;
                    }
                    System.out.println("OK");
                    List<MissionObject> missions = h.getMissions();;
                    FacilityObject facility = StaticGet.getTeamFacility();
                    List<FireObject> fires = Arrays.asList(StaticGet.getFires());
                    //List<FireObject> firesInArea = SendVehicle.GetFireInArea(fires, facility);

                    synchronized(hrepo){
                        for(MissionObject mission : missions){
                            double lat;
                            double lon;
                            VehicleObject vehicle = StaticGet.getVehicleById(String.valueOf(mission.getVehicleId()));
                            if(mission.getVehicleState().equals(VehicleState.AT_FIRE) ||
                                    mission.getVehicleState().equals(VehicleState.GOING_TO_FIRE)){
                                //TODO Faire sans spammer l'api
                                try{
                                    FireObject fire = StaticGet.getFire(String.valueOf(mission.getDestinationId()));
                                    // Le feu existe toujours
                                }catch(HttpServerErrorException e){
                                    // Le feu n'existe plus
                                    mission.setVehicleState(VehicleState.GOING_TO_FACILITY);
                                    continue;
                                }

                            }
                            if(mission.getVehicleState().equals(VehicleState.GOING_TO_FACILITY)){

                                lat = facility.getLat();
                                lon = facility.getLon();
                                mission.setVehicleState(VehicleState.AT_FIRE);

                            } else if (mission.getVehicleState().equals(VehicleState.GOING_TO_FIRE)) {
                                //TODO Faire propre sans spammer l'api
                                FireObject fire = StaticGet.getFire(String.valueOf(mission.getDestinationId()));
                                lat = fire.getLat();
                                lon = fire.getLon();
                                mission.setVehicleState(VehicleState.AT_FIRE);
                            }else{
                                continue;
                            }
                            vehicle.setLat(lat);
                            vehicle.setLon(lon);
                            StaticVehicle.addVehicle(vehicle);
                        }
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

