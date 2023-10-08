package com.example.li;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiApplication {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MoldincombankUI moldincombankUI = new MoldincombankUI();
            }
        });

        SpringApplication.run(LiApplication.class, args);

    }
}
