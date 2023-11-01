package com.frontend.li;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Inregistrare extends JPanel {
    private JLabel numeLabel;
    private JTextField numeField;
    private JLabel nrCardLabel;
    private JTextField nrCardField;
    private JLabel pinLabel;
    private JPasswordField pinField;
    private JButton registerButton;

    public Inregistrare() {
        setLayout(new GridLayout(6, 2));

        numeLabel = new JLabel("Nume:");
        numeField = new JTextField();
        nrCardLabel = new JLabel("Numar Card:");
        nrCardField = new JTextField();
        pinLabel = new JLabel("PIN:");
        pinField = new JPasswordField();
        registerButton = new JButton("Inregistreaza");

        add(numeLabel);
        add(numeField);
        add(nrCardLabel);
        add(nrCardField);
        add(pinLabel);
        add(pinField);
        add(new JLabel());
        add(registerButton);

        // Add styling
        numeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nrCardLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        registerButton.setBackground(Color.BLUE);
        registerButton.setForeground(Color.WHITE);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nume = numeField.getText();
                String nrCard = nrCardField.getText();
                String pin = new String(pinField.getPassword());

                boolean success = postDataToDatabase(nume, nrCard, pin);

                if (success) {
                    JOptionPane.showMessageDialog(Inregistrare.this, "Inregistrare reusita!");
                    clearFields();
                    closePanelAndOpenConectare();
                } else {
                    JOptionPane.showMessageDialog(Inregistrare.this, "Inregistrare esuata. Va rugam sa incercati din nou.");
                }
            }
        });
    }

    private boolean postDataToDatabase(String nume, String nrCard, String pin) {
        try {
            String apiUrl = "http://localhost:8080/api/users";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"id\": 2, \"nume\": \"" + nume + "\", \"nr_card\": \"" + nrCard + "\", \"pin\": \"" + pin + "\", \"balance\": 0}";

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write(jsonInputString);
            osw.flush();
            osw.close();

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void clearFields() {
        numeField.setText("");
        nrCardField.setText("");
        pinField.setText("");
    }

    private void closePanelAndOpenConectare() {
        // Close the current panel
        Window parentWindow = SwingUtilities.windowForComponent(this);
        if (parentWindow != null) {
            parentWindow.dispose();
        }

        // Open the Conectare panel
        Conectare.showNewPanel();
    }

    public static void showNewPanel() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Inregistrare");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Inregistrare inregistrare = new Inregistrare();
            frame.getContentPane().add(inregistrare);
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
