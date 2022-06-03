package com.majeur.projet.threading;

import java.util.ArrayList;
import java.util.Optional;

import com.majeur.projet.emergencyManager.EmergencyManagerRunnable;
import com.majeur.projet.movement.MoveRunnable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {


    private ThreadRepository threadRepository;
    EmergencyManagerRunnable emergencyManagerRunnable;
    MoveRunnable moveRunnable;
    private Thread emergencyManagerThread;
    private Thread moveRunnableThread;

    public ThreadService(ThreadRepository threadRepository) {
        //Replace the @Autowire annotation....
        this.threadRepository = threadRepository;
        //TODO Prendre en charge génération de 2 threads différents à partir de Movement et EmergencyManager
        //Create a Runnable is charge of executing cyclic actions
        this.emergencyManagerRunnable =new EmergencyManagerRunnable(this.threadRepository);
        this.moveRunnable =new MoveRunnable(this.threadRepository);

        // A Runnable is held by a Thread which manage lifecycle of the Runnable
        emergencyManagerThread =new Thread(emergencyManagerRunnable);
        moveRunnableThread = new Thread(moveRunnable);
        // The Thread is started and the method run() of the associated DisplayRunnable is launch
        emergencyManagerThread.start();

    }


    public void addThread(ThreadEntity h) {
        ThreadEntity createdThreadEntity = threadRepository.save(h);
        System.out.println(createdThreadEntity);
    }

    public ThreadEntity getThread(int id) {
        Optional<ThreadEntity> hOpt = threadRepository.findById(id);
        if (hOpt.isPresent()) {
            return hOpt.get();
        }else {
            return null;
        }
    }

    public void stopThread() {
        //Call the user defined stop method of the runnable
        this.emergencyManagerRunnable.stop();
        try {
            //force the thread to stop
            this.emergencyManagerThread.join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Bean(initMethod="init")
    public void init() {
        ArrayList<MissionObject> missions = new ArrayList<MissionObject>(); //TODO Utiliser avec mot clef synchronized
        ThreadEntity h1=new ThreadEntity(5, "name", 55);
        ThreadEntity h2=new ThreadEntity(5, "name1", 55);
        threadRepository.save(h1);
        threadRepository.save(h2);
    }

}
