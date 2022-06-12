package com.majeur.projet.emergencyManager;

import com.majeur.projet.apiCommunication.FacilityObject;
import com.majeur.projet.apiCommunication.FireObject;
import com.majeur.projet.apiCommunication.StaticGet;
import com.majeur.projet.apiCommunication.VehicleObject;
import com.majeur.projet.threading.MissionEntity;
import com.majeur.projet.threading.ThreadEntity;
import com.majeur.projet.threading.ThreadRepository;
import com.majeur.projet.threading.VehicleState;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                    synchronized(hrepo){

                        List<MissionEntity> missions = h.getMissions();

                        FacilityObject facility = StaticGet.getTeamFacility();
                        List<FireObject> listFires = Arrays.asList(StaticGet.getFires());

                        Map<Integer, FireObject> firesInArea = EmergencyManagerFunctions.GetFireInArea(listFires, facility);

                        List<VehicleObject> listVehicles = List.of(StaticGet.getVehicles());
                        Map<Integer, VehicleObject> vehicleMap = listVehicles.stream()
                                .collect(Collectors.toMap(VehicleObject::getId, Function.identity()));


                        for(MissionEntity mission : missions){
                            VehicleObject vehicle = vehicleMap.get(mission.getVehicleId());

                            // Sends vehicles only if they are available
                            if(mission.getVehicleState().equals(VehicleState.AT_FACILITY) || mission.getVehicleState().equals(VehicleState.GOING_TO_FACILITY)){

                                // Sends vehicles only if they have enough fuel & liquid
                                if((vehicle.getLiquidQuantity() > 5) && (vehicle.getFuel() > 5)) {
	                                MissionEntity newMission =
	                                        EmergencyManagerFunctions.SelectVehicle(firesInArea, mission.getVehicleId(), facility.getId(), vehicle);
                                    if(newMission.getVehicleState().equals(VehicleState.GOING_TO_FIRE)){
                                        // Avoid sending multiple vehicles to same fire
                                        firesInArea.remove(newMission.getDestinationId());
                                    }
                                    System.out.println("Vehicle " + vehicle.getId() + ": New mission " + newMission.getVehicleState() + " " + newMission.getDestinationId());
                                    double steps = EmergencyManagerFunctions.ComputeStep(vehicle, newMission.getDestinationId(), facility, newMission.getVehicleState());
                                    mission.setSteps(steps);
                                    mission.setDestinationId(newMission.getDestinationId());
	                                mission.setVehicleState(newMission.getVehicleState());
	                            }else{
                                    System.out.println("Vehicle " + vehicle.getId() + ": Not enough liquid/fuel to leave facility");
                                }
                        	}

                        }
                        System.out.println(missions.toString());
                        h.setMissions(missions);
                        hrepo.save(h);
                    }
                }
                System.out.println("Thread affect loop done");
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

