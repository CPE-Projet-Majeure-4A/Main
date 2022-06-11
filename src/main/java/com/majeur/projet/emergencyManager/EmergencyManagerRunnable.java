package com.majeur.projet.emergencyManager;

import com.majeur.projet.apiCommunication.FacilityObject;
import com.majeur.projet.apiCommunication.FireObject;
import com.majeur.projet.apiCommunication.StaticGet;
import com.majeur.projet.apiCommunication.VehicleObject;
import com.majeur.projet.threading.MissionEntity;
import com.majeur.projet.threading.ThreadEntity;
import com.majeur.projet.threading.ThreadRepository;
import com.majeur.projet.threading.VehicleState;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
                        //System.out.println("EM: "+missions);

                        FacilityObject facility = StaticGet.getTeamFacility();
                        List<FireObject> listFires = Arrays.asList(StaticGet.getFires());

                        List<FireObject> firesInArea = EmergencyManagerFunctions.GetFireInArea(listFires, facility);

                        List<VehicleObject> listVehicles = List.of(StaticGet.getVehicles());
                        Map<Integer, VehicleObject> vehicleMap = listVehicles.stream()
                                .collect(Collectors.toMap(VehicleObject::getId, Function.identity()));

                        for(MissionEntity mission : missions){
                            VehicleObject vehicle = vehicleMap.get(mission.getVehicleId());
                        	if(vehicle.getLiquidQuantity() != 0) {
	                            if(mission.getVehicleState().equals(VehicleState.AT_FACILITY) ||
	                                mission.getVehicleState().equals(VehicleState.GOING_TO_FACILITY)){
                                    //System.out.println("Looking for mission");
	                                MissionEntity newMission =
	                                        EmergencyManagerFunctions.SelectVehicle(firesInArea, mission.getVehicleId(), facility.getId(), vehicle);
	                                // Calculer pas (steps) et ajouter à mission
                                    double steps = EmergencyManagerFunctions.ComputeStep(vehicle, newMission.getDestinationId(), facility, newMission.getVehicleState());
                                    mission.setSteps(steps);
                                    mission.setDestinationId(newMission.getDestinationId());
	                                mission.setVehicleState(newMission.getVehicleState());
	                            }
                        	}else{
                                System.out.println("No liquid");
                            }

                        }
                        System.out.println(missions.toString());
                        h.setMissions(missions);
                        hrepo.save(h);
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

