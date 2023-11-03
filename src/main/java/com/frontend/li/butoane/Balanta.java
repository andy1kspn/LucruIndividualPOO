package com.frontend.li.butoane;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontend.li.BancaPersonala;
public class Balanta extends JPanel {
    public static Integer userId;
    public static String userNume;
    private JLabel numeLabel;
    private JLabel balanceLabel;

    public static void showNewPanel(String userNume,Integer userId) {
        Balanta.userId = userId;
        Balanta.userNume = userNume;
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Moldindconbank ATM - Dezvoltat de Spinu Andrei IA2201");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Balanta balance = new Balanta();
            frame.getContentPane().add(balance);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
            balance.fetchUserData();
        });
    }

    public Balanta() {
        setLayout(new BorderLayout());

        JPanel userDataPanel = new JPanel();
        userDataPanel.setLayout(new BoxLayout(userDataPanel, BoxLayout.Y_AXIS));

        numeLabel = new JLabel("Nume: ");
        balanceLabel = new JLabel("Balance: ");

        userDataPanel.add(numeLabel);
        userDataPanel.add(balanceLabel);

        add(userDataPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            closePanel();
            openBancaPersonala();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        numeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
    }

    public void fetchUserData() {
        try {
            String apiUrl = "http://localhost:8080/api/users/" + userId;
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                ObjectMapper objectMapper = new ObjectMapper();
                UserData userData = objectMapper.readValue(response.toString(), UserData.class);

                numeLabel.setText("Nume: " + userData.getNume());
                balanceLabel.setText("Balance: " + userData.getBalance());

            } else {
                System.out.println("Failed to retrieve user data. HTTP response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class UserData {
        private int id;
        private String nume;
        private String pin;
        private String nr_card;
        private double balance;

        // Getter and Setter for "id"
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        // Getter and Setter for "nume"
        public String getNume() {
            return nume;
        }

        public void setNume(String nume) {
            this.nume = nume;
        }

        // Getter and Setter for "pin"
        public String getPin() {
            return pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }

        // Getter and Setter for "nr_card"
        public String getNr_card() {
            return nr_card;
        }

        public void setNr_card(String nr_card) {
            this.nr_card = nr_card;
        }

        // Getter and Setter for "balance"
        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
    }
    private void closePanel() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
    }

    private void openBancaPersonala() {
        BancaPersonala.showNewPanel(userNume, userId);
    }

}
