package com.frontend.li.butoane;

import com.frontend.li.BancaPersonala;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.swing.JOptionPane.showMessageDialog;

public class Extragere extends JPanel {
    public static Integer userId;
    public static String userNume;
    private static int transactionIdCounter = 1;

    public static void showNewPanel(String useNume, Integer useId) {
        userId = useId;
        userNume = useNume;
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Moldindconbank ATM - Dezvoltat de Spinu Andrei IA2201");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Extragere extragere = new Extragere();
            frame.getContentPane().add(extragere);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public Extragere() {
        setLayout(new BorderLayout());

        JTextField sumaField = new JTextField(10);
        JButton extrageButton = new JButton("Extrage");

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.insets = new Insets(5, 5, 5, 5);
        labelConstraints.anchor = GridBagConstraints.WEST;

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.insets = new Insets(5, 5, 5, 5);
        fieldConstraints.anchor = GridBagConstraints.WEST;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;

        JLabel sumaLabel = new JLabel("Suma:");
        sumaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        inputPanel.add(sumaLabel, labelConstraints);

        sumaField.setFont(new Font("Arial", Font.PLAIN, 24));
        inputPanel.add(sumaField, fieldConstraints);
        inputPanel.add(extrageButton);

        add(inputPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            closePanel();
            openBancaPersonala();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        styleButton(extrageButton);
        styleButton(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        extrageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String suma = sumaField.getText();
                    int transactionId = transactionIdCounter++;
                    String apiUrl = "http://localhost:8080/api/users/" + userId;

                    HttpURLConnection userConnection = (HttpURLConnection) new URL(apiUrl).openConnection();
                    userConnection.setRequestMethod("GET");

                    int userResponseCode = userConnection.getResponseCode();
                    if (userResponseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(userConnection.getInputStream()));
                        StringBuilder userResponse = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            userResponse.append(line);
                        }
                        reader.close();

                        String userJson = userResponse.toString();

                        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
                        String transactionData = "{\"id\":" + transactionId + ",\"user\":" + userJson + ",\"data\":\"" + currentDateTime + "\",\"tip\":\"withdraw\",\"suma\":" + suma + "}";

                        String postApiUrl = "http://localhost:8080/api/tranzactii";
                        HttpURLConnection transactionConnection = (HttpURLConnection) new URL(postApiUrl).openConnection();
                        transactionConnection.setRequestMethod("POST");
                        transactionConnection.setRequestProperty("Content-Type", "application/json");
                        transactionConnection.setDoOutput(true);

                        try (OutputStream os = transactionConnection.getOutputStream()) {
                            byte[] input = transactionData.getBytes("UTF-8");
                            os.write(input, 0, input.length);
                        }

                        int responseCode = transactionConnection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            System.out.println("Transaction successful.");
                            showMessageDialog(null, "Extragere reusita!");
                        } else {
                            System.out.println("Failed to make a POST request. HTTP response code: " + responseCode);
                        }
                    } else {
                        System.out.println("Failed to retrieve user data. HTTP response code: " + userResponseCode);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(51, 153, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
    }

    private void closePanel() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
    }

    private void openBancaPersonala() {
        BancaPersonala.showNewPanel(userNume, userId);
    }
}
