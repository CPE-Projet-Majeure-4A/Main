package com.majeur.projet.emergencyManager;

import com.majeur.projet.threading.ThreadEntity;
import com.majeur.projet.threading.ThreadRepository;

public class EmergencyManagerRunnable implements Runnable {

    private ThreadRepository hrepo;
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
                }
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

