package com.backend.li;

import com.frontend.li.LoadingScreen;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class LiApplication {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LoadingScreen form = new LoadingScreen();
            form.setVisible(true);
        });




        SpringApplication.run(LiApplication.class, args);

    }
}
