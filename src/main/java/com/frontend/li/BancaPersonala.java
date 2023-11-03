package com.frontend.li;

import com.frontend.li.butoane.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BancaPersonala extends JPanel {
    private JLabel welcomeLabel;
    private JButton alimentareButton;
    private JButton extragereButton;
    private JButton balantaButton;
    private JButton messajeButton;
    private JButton tranzactiiButton;
    private JButton iesireButton;
    private static JFrame currentFrame;

    private static Integer idUser;
    private static String numeUser;

    public BancaPersonala(String nume, double balance) {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        welcomeLabel = new JLabel("Bine ai venit, " + nume);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        alimentareButton = createStyledButton("Alimentare");
        alimentareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFrame != null) {
                    currentFrame.dispose();
                }
                Alimentare.showNewPanel(numeUser,idUser);
            }
        });






        extragereButton = createStyledButton("Extragere");
        extragereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFrame != null) {
                    currentFrame.dispose();
                }
                Extragere.showNewPanel(numeUser,idUser);
            }
        });






        balantaButton = createStyledButton("Balanta");

        balantaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFrame != null) {
                    currentFrame.dispose();
                }
                Balanta.showNewPanel(numeUser,idUser);
            }
        });






        messajeButton = createStyledButton("Messaje");
        messajeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFrame != null) {
                    currentFrame.dispose();
                }
                Mesaje.showNewPanel(numeUser,idUser);
            }
        });






        tranzactiiButton = createStyledButton("Tranzactii");
        tranzactiiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFrame != null) {
                    currentFrame.dispose();
                }
                Tranzactii.showNewPanel(numeUser, idUser);
            }
        });








        iesireButton = createStyledButton("Iesire");
        iesireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });












        buttonPanel.add(alimentareButton);
        buttonPanel.add(messajeButton);
        buttonPanel.add(extragereButton);
        buttonPanel.add(tranzactiiButton);
        buttonPanel.add(balantaButton);
        buttonPanel.add(iesireButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 102, 204)); // Blue color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102), 2));
        button.setPreferredSize(new Dimension(180, 60));

        return button;
    }

    public static void showNewPanel(String nume, Integer userId) {
        idUser = userId;
        numeUser = nume;
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Moldindconbank ATM - Dezvoltat de Spinu Andrei IA2201");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            BancaPersonala bancaPersonala = new BancaPersonala(nume, userId);
            frame.getContentPane().add(bancaPersonala);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
            currentFrame = frame;

        });
    }
}
