package com.frontend.li.butoane;

import com.frontend.li.BancaPersonala;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tranzactii extends JPanel {
    private JButton backButton;
    private JTextArea messageTextArea;
    private static Integer userId;
    private static String userNume;

    public Tranzactii(Integer userId) {
        backButton = new JButton("Back");
        messageTextArea = new JTextArea(20, 40);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closePanel();
                openBancaPersonala();
            }
        });

        add(backButton);
        add(new JScrollPane(messageTextArea));

        fetchAndDisplayMessages(userId);
    }

    private void fetchAndDisplayMessages(Integer userId) {
        try {
            for (int variable = 0; variable < 100; variable++) {
                URL url = new URL("http://localhost:8080/api/tranzactii/" + variable);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    String transactionData = response.toString();
                    try {
                        JSONObject transactionJson = new JSONObject(transactionData);
                        JSONObject userJson = transactionJson.getJSONObject("user");
                        int id_utilizator = userJson.getInt("id");

                        if (id_utilizator == userId) {
                            // Display the information from the transaction
                            String data = transactionJson.getString("data");
                            String tip = transactionJson.getString("tip");
                            double suma = transactionJson.getDouble("suma");

                            // Append the information to the messageTextArea
                            messageTextArea.append("Data: " + data + "\n");
                            messageTextArea.append("Tip: " + tip + "\n");
                            messageTextArea.append("Suma: " + suma + "\n");
                            messageTextArea.append("\n");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                connection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public static void showNewPanel(String userNume, Integer userId) {
        final String userNumeFinal = userNume;
        final Integer userIdFinal = userId;
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Moldindconbank ATM - Dezvoltat de Spinu Andrei IA2201");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Tranzactii tranzactii = new Tranzactii(userIdFinal);
            Tranzactii.userNume = userNumeFinal;
            Tranzactii.userId = userIdFinal;
            frame.getContentPane().add(tranzactii);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void closePanel() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
    }

    private void openBancaPersonala() {
        BancaPersonala.showNewPanel(userNume, userId); // You need to implement this method in your BancaPersonala class
    }

}

