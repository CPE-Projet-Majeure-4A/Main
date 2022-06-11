package com.majeur.projet.resourceManager;

import com.majeur.projet.apiCommunication.*;
import com.majeur.projet.movement.MoveFunctions;
import com.majeur.projet.threading.MissionEntity;
import com.majeur.projet.threading.ThreadEntity;
import com.majeur.projet.threading.ThreadRepository;
import com.majeur.projet.threading.VehicleState;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

                        List<VehicleObject> listVehicles = List.of(StaticGet.getVehicles());
                        Map<Integer, VehicleObject> vehicleMap = listVehicles.stream()
                                .collect(Collectors.toMap(VehicleObject::getId, Function.identity()));

                        for(MissionEntity mission : missions) {
                            VehicleObject vehicle = vehicleMap.get(mission.getVehicleId());
                            if(mission.getVehicleState().equals(VehicleState.AT_FIRE)){
                                ResourceManagerFunctions.decreaseLiquid(vehicle);
                            }else if(mission.getVehicleState().equals(VehicleState.AT_FACILITY)) {
                                ResourceManagerFunctions.increaseFuel(vehicle);
                                ResourceManagerFunctions.increaseLiquid(vehicle);
                            }else{
                                ResourceManagerFunctions.decreaseFuel(vehicle);
                            }
                            StaticVehicle.addVehicle(vehicle);

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


