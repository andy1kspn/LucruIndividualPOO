package com.frontend.li.butoane;

import com.frontend.li.BancaPersonala;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class Mesaje extends JPanel {
    private JButton deleteAllButton;
    private JButton backButton;
    private JTextArea messageTextArea;
    private static Integer userId;
    private static String userNume;
    private List<Integer> messagesToBeDeleted = new ArrayList<>();

    public Mesaje(Integer userId) {
        setLayout(new BorderLayout());

        deleteAllButton = new JButton("Delete All");
        messageTextArea = new JTextArea(20, 40);

        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendDeleteRequest(userId);
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            closePanel();
            openBancaPersonala();
        });

        styleButton(deleteAllButton);
        styleButton(backButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(deleteAllButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(messageTextArea), BorderLayout.CENTER);

        fetchAndDisplayMessages(userId);
    }

    private void fetchAndDisplayMessages(Integer userId) {
        for (int variable = 0; variable < 100; variable++) {
            try {
                URL url = new URL("http://localhost:8080/api/messages/" + variable);
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

                    String messageData = response.toString();
                    try {
                        JSONObject messageJson = new JSONObject(messageData);
                        int messageUserId = messageJson.getInt("id_utilizator");

                        if (messageUserId == userId) {
                            messageTextArea.append(messageJson.getString("mesaj") + "\n");
                            messagesToBeDeleted.add(variable);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendDeleteRequest(Integer userId) {
        for (int i = 0; i < messagesToBeDeleted.size(); i++) {
            try {
                URL url = new URL("http://localhost:8080/api/messages/" + messagesToBeDeleted.get(i));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    messageTextArea.setText("");
                } else {
                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        showMessageDialog(null, "Toate mesajele dvs au fost sterse!");
    }

    public static void showNewPanel(String userNume, Integer userId) {
        final String userNumeFinal = userNume;
        final Integer userIdFinal = userId;

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Moldindconbank ATM - Dezvoltat de Spinu Andrei IA2201");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Mesaje mesaje = new Mesaje(userIdFinal);
            mesaje.userNume = userNumeFinal;
            mesaje.userId = userIdFinal;
            frame.getContentPane().add(mesaje);
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
        BancaPersonala.showNewPanel(userNume, userId);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(51, 153, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
    }
}
