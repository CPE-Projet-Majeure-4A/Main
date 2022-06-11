package com.majeur.projet.threading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.majeur.projet.apiCommunication.StaticGet;
import com.majeur.projet.apiCommunication.VehicleObject;
import com.majeur.projet.emergencyManager.EmergencyManagerRunnable;
import com.majeur.projet.movement.MoveRunnable;
import com.majeur.projet.resourceManager.ResourceManagerRunnable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {


    private final ThreadRepository threadRepository;
    EmergencyManagerRunnable emergencyManagerRunnable;
    MoveRunnable moveRunnable;
    ResourceManagerRunnable resourceManagerRunnable;
    private final Thread emergencyManagerThread;
    private final Thread moveThread;
    private final Thread resourceManagerThread;

    public ThreadService(ThreadRepository threadRepository) {
        //Replace the @Autowire annotation....
        this.threadRepository = threadRepository;
        //Create a Runnable is charge of executing cyclic actions
        this.emergencyManagerRunnable =new EmergencyManagerRunnable(this.threadRepository);
        this.moveRunnable =new MoveRunnable(this.threadRepository);
        this.resourceManagerRunnable = new ResourceManagerRunnable(this.threadRepository);

        // A Runnable is held by a Thread which manage lifecycle of the Runnable
        emergencyManagerThread = new Thread(emergencyManagerRunnable);
        moveThread = new Thread(moveRunnable);
        resourceManagerThread = new Thread(resourceManagerRunnable);
        // The Thread is started and the method run() of the associated DisplayRunnable is launch
        emergencyManagerThread.start();
        moveThread.start();
        resourceManagerThread.start();
    }


    public void addThread(ThreadEntity h) {
        ThreadEntity createdThreadEntity = threadRepository.save(h);
        System.out.println(createdThreadEntity);
    }

    public ThreadEntity getThread(int id) {
        Optional<ThreadEntity> hOpt = threadRepository.findById(id);
        return hOpt.orElse(null);
    }

    public void stopThread() {
        //Call the user defined stop method of the runnable
        this.emergencyManagerRunnable.stop();
        this.moveRunnable.stop();
        this.resourceManagerRunnable.stop();
        try {
            //force the thread to stop
            this.emergencyManagerThread.join(100);
            this.moveThread.join(100);
            this.resourceManagerThread.join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Bean()
    //@Bean(initMethod = "init")
    public void init() {
        ArrayList<MissionEntity> missions = new ArrayList<>(); //Utiliser avec mot clef synchronized
        String name = "default";
        List<VehicleObject> vehicles = Arrays.asList(StaticGet.getVehicles());
        int facilityId = StaticGet.getTeamFacility().getId();
        for(VehicleObject vehicle : vehicles){
            if(vehicle.getFacilityRefID() == facilityId){
                MissionEntity mission = new MissionEntity(vehicle.getId(), facilityId, VehicleState.GOING_TO_FACILITY, 5);
                //Initialisation des missions
                missions.add(mission);
            }
        }
        ThreadEntity h1 = new ThreadEntity(1, missions, name);
        threadRepository.save(h1);
        System.out.println(threadRepository.findById(1).get().getMissions());
        System.out.println("Thread initalized");
    }

}
