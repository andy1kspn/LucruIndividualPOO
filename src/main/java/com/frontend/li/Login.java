package com.frontend.li;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JPanel {
    private Moldindconbank parent;

    public Login(Moldindconbank parent) {
        this.parent = parent;
        initialize();
    }

    private void initialize() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel pinLabel = new JLabel("PIN: ");
        JTextField pinTextField = new JTextField(10);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredPin = pinTextField.getText();
                boolean isPinCorrect = parent.checkIfPinIsCorrect(enteredPin);

                parent.getInteractionPanel().removeAll();
                parent.getInteractionPanel().setLayout(null);

                JLabel resultLabel = new JLabel();
                resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
                resultLabel.setBounds(50, 50, 200, 50);

                if (isPinCorrect) {
                    resultLabel.setText("Success");
                } else {
                    resultLabel.setText("Pin gresit!");
                }

                parent.getInteractionPanel().add(resultLabel);
                parent.getInteractionPanel().repaint();
            }
        });

        add(pinLabel);
        add(pinTextField);
        add(loginButton);
    }
}
