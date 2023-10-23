package com.backend.li;

import com.frontend.li.text.MainPanel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.frontend.li.Moldindconbank;

import javax.swing.*;

@SpringBootApplication
public class LiApplication {
    public static void main(String[] args) {

       /* SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainPanel();
            }
        });*/

        SpringApplication.run(LiApplication.class, args);

    }
}
