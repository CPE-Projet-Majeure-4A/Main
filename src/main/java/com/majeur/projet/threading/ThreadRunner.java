package com.majeur.projet.threading;

import com.majeur.projet.threading.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ThreadRunner implements CommandLineRunner {

    @Autowired
    private final ThreadService threadService;

    public ThreadRunner(ThreadService threadService) {
        this.threadService = threadService;
    }

    @Override
    public void run(String... args) throws Exception {
        //threadService.init();
    }
}
