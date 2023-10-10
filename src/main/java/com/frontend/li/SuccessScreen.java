package com.frontend.li;

import javax.swing.*;
import java.awt.*;

public class SuccessScreen {
    public static void showSuccessPanel(Moldindconbank moldincombankUI) {
        JFrame successFrame = new JFrame("Success!");
        successFrame.setSize(300, 150);
        successFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        successFrame.setLocationRelativeTo(moldincombankUI);  // Position relative to the MoldincombankUI

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Transaction Successful", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(label, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> successFrame.dispose());
        panel.add(closeButton, BorderLayout.SOUTH);

        successFrame.add(panel);
        successFrame.setVisible(true);
    }
}
