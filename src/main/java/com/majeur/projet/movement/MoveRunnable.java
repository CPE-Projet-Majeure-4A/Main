package com.majeur.projet.movement;

import com.majeur.projet.apiCommunication.*;
import com.majeur.projet.threading.MissionEntity;
import com.majeur.projet.threading.ThreadEntity;
import com.majeur.projet.threading.ThreadRepository;
import com.majeur.projet.threading.VehicleState;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                Thread.sleep(3000);

                for (ThreadEntity h : this.hrepo.findAll()) {

                    synchronized(hrepo){
                        List<MissionEntity> missions = h.getMissions();
                        System.out.println("Move: "+ missions);
                        FacilityObject facility = StaticGet.getTeamFacility();
                        List<FireObject> listFires = Arrays.asList(StaticGet.getFires());

                        Map<Integer, FireObject> fireMap = listFires.stream()
                                .collect(Collectors.toMap(FireObject::getId, Function.identity()));

                        List<VehicleObject> listVehicles = List.of(StaticGet.getVehicles());
                        Map<Integer, VehicleObject> vehicleMap = listVehicles.stream()
                                .collect(Collectors.toMap(VehicleObject::getId, Function.identity()));

                        for(MissionEntity mission : missions){
                            VehicleObject vehicle = vehicleMap.get(mission.getVehicleId());

                            // On vérifie si le feu existe toujours
                            if(mission.getVehicleState().equals(VehicleState.AT_FIRE) ||
                                    mission.getVehicleState().equals(VehicleState.GOING_TO_FIRE)){
                                    if (!MoveFunctions.doesFireExist(mission, fireMap) || (vehicle.getLiquidQuantity() <= 5)){
                                        System.out.println("Vehicle " + vehicle.getId() +": Abort mission No Fire/Liquid");
                                        mission.setVehicleState(VehicleState.GOING_TO_FACILITY);
                                        mission.setDestinationId(facility.getId());
                                        mission.setSteps(5);
                                        continue;
                                    }
                            }

                            if(!MoveFunctions.isVehicleMoving(mission)){
                                continue;
                            }

                            // destination coords
                            double[] destCoords = MoveFunctions.getDestinationCoords(mission, facility, fireMap);

                            // coords to move vehicle to
                            // double[] coords = MoveFunctions.getDestination_Teleportation(destCoords);
                            double[] coords = MoveFunctions.getDestination_Linear(vehicle, mission, destCoords);

                            System.out.println("Vehicle " + vehicle.getId() + ": Old coords: " + vehicle.getLat() +", " + vehicle.getLon() + " New: " + coords[0] + ", " + coords[1]);

                            vehicle.setLat(coords[0]);
                            vehicle.setLon(coords[1]);

                            // send update to API
                            StaticVehicle.addVehicle(vehicle);

                            if(MoveFunctions.isVehicleAtDestination(destCoords, coords)){
                                mission.setVehicleState(MoveFunctions.setVehicleAtDestination(mission.getVehicleState()));
                                System.out.println("Vehicle " + vehicle.getId() + ": Reached destination");
                            }


                        }
                        h.setMissions(missions);
                        hrepo.save(h);
                    }

                }

                System.out.println("Thread move loop done");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Runnable DisplayRunnable ends.... ");
    }

    public void stop() {
        this.isEnd = true;
    }

}

