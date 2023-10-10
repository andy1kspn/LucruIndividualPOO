package com.frontend.li;

import javax.swing.*;
import java.awt.*;

public class Register extends JPanel {
    public Register() {
        initialize();
    }

    private void initialize() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add components for registration as needed
        // For example: JLabels, JTextFields, JButtons, etc.
        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameTextField = new JTextField(10);
        JButton registerButton = new JButton("Register");

        // Add components to the panel
        add(nameLabel);
        add(nameTextField);
        add(registerButton);
    }
}
