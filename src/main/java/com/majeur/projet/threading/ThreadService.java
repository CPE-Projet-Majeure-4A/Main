package com.majeur.projet.threading;

import java.util.ArrayList;
import java.util.Optional;

import com.majeur.projet.emergencyManager.EmergencyManagerRunnable;
import com.majeur.projet.movement.MoveRunnable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {


    private final ThreadRepository threadRepository;
    EmergencyManagerRunnable emergencyManagerRunnable;
    MoveRunnable moveRunnable;
    private final Thread emergencyManagerThread;
    private final Thread moveThread;

    public ThreadService(ThreadRepository threadRepository) {
        //Replace the @Autowire annotation....
        this.threadRepository = threadRepository;
        //TODO Prendre en charge génération de 2 threads différents à partir de Movement et EmergencyManager
        //Create a Runnable is charge of executing cyclic actions
        this.emergencyManagerRunnable =new EmergencyManagerRunnable(this.threadRepository);
        this.moveRunnable =new MoveRunnable(this.threadRepository);

        // A Runnable is held by a Thread which manage lifecycle of the Runnable
        emergencyManagerThread =new Thread(emergencyManagerRunnable);
        moveThread = new Thread(moveRunnable);
        // The Thread is started and the method run() of the associated DisplayRunnable is launch
        emergencyManagerThread.start();
        moveThread.start();
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
        try {
            //force the thread to stop
            this.emergencyManagerThread.join(100);
            this.moveThread.join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Bean()
    //@Bean(initMethod = "init")
    public void init() {
        ArrayList<MissionObject> missions = new ArrayList<>(); //TODO Utiliser avec mot clef synchronized
        ThreadEntity h1=new ThreadEntity(1, "default");
        threadRepository.save(h1);
    }

}
