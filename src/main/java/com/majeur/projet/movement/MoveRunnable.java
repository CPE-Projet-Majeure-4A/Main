package com.majeur.projet.movement;

import com.majeur.projet.threading.ThreadEntity;
import com.majeur.projet.threading.ThreadRepository;

//TODO Régler problèmes et adapter le code du prof à 2 threads différents
public class MoveRunnable implements Runnable{

    private ThreadRepository hrepo;
    boolean isEnd = false;

    public MoveRunnable(ThreadRepository hrepo) {
        this.hrepo = hrepo;
    }

    @Override
    public void run() {
        while (!this.isEnd) {
            try {
                Thread.sleep(2000);
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

    public void update(){

    }
}

