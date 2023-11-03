package com.frontend.li;

import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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
    private Integer validNumeid;
    private Image backgroundImage;


    public Inregistrare() {

        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\Spinu Andrei\\Desktop\\Java\\LI\\src\\main\\java\\panel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setFocusable(true);
        requestFocusInWindow();


        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;

        numeLabel = new JLabel("Nume:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(numeLabel, gbc);

        nrCardLabel = new JLabel("Numar Card:");
        gbc.gridy = 1;
        add(nrCardLabel, gbc);

        pinLabel = new JLabel("PIN:");
        gbc.gridy = 2;
        add(pinLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        numeField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(numeField, gbc);

        nrCardField = new JTextField(16);
        gbc.gridy = 1;
        add(nrCardField, gbc);

        pinField = new JPasswordField(4);
        gbc.gridy = 2;
        add(pinField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        registerButton = new JButton("Inregistreaza");
        add(registerButton, gbc);

        numeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nrCardLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        registerButton.setBackground(Color.BLUE);
        registerButton.setForeground(Color.WHITE);
        numeLabel.setForeground(Color.WHITE);
        nrCardLabel.setForeground(Color.WHITE);
        pinLabel.setForeground(Color.WHITE);

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
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }



    private boolean postDataToDatabase(String nume, String nrCard, String pin) {
        try {
            String apiUrl = "http://localhost:8080/api/users";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"nume\": \"" + nume + "\", \"nr_card\": \"" + nrCard + "\", \"pin\": \"" + pin + "\", \"balance\": 0}";

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write(jsonInputString);
            osw.flush();
            osw.close();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                int userId = getUserIdFromResponse(connection);
                postWelcomeMessages(userId, nume);
                cautaId(nume);
            }

            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getUserIdFromResponse(HttpURLConnection connection) {
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                try {
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    return jsonResponse.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.err.println("Invalid JSON response: " + response.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }


    private void postWelcomeMessages(int userId, String nume) {
        try {
            if (cautaId(nume)) {
                String welcomeMessage = "{\"id_utilizator\": " + validNumeid + ", \"mesaj\": \"Bine ati venit la MICB - " + nume + "\"}";
                String message2 = "{\"id_utilizator\": " + validNumeid + ", \"mesaj\": \"lalalalalalalaaaaaaaaaaaa\"}";
                String message3 = "{\"id_utilizator\": " + validNumeid + ", \"mesaj\": \"Spaaaaaaaaaam!\"}";

                postMessageToDatabase(welcomeMessage);
                postMessageToDatabase(message2);
                postMessageToDatabase(message3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void postMessageToDatabase(String message) {
        try {
            String messageApiUrl = "http://localhost:8080/api/messages";

            URL messageUrl = new URL(messageApiUrl);
            HttpURLConnection messageConnection = (HttpURLConnection) messageUrl.openConnection();
            messageConnection.setRequestMethod("POST");
            messageConnection.setRequestProperty("Content-Type", "application/json");
            messageConnection.setDoOutput(true);

            OutputStream os = messageConnection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write(message);
            osw.flush();
            osw.close();

            int responseCode = messageConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        numeField.setText("");
        nrCardField.setText("");
        pinField.setText("");
    }

    private void closePanelAndOpenConectare() {
        Window parentWindow = SwingUtilities.windowForComponent(this);
        if (parentWindow != null) {
            parentWindow.dispose();
        }

        Conectare.showNewPanel();
    }

    public static void showNewPanel() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Inregistrare");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Inregistrare inregistrare = new Inregistrare();
            frame.getContentPane().add(inregistrare);
            frame.setResizable(false);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }




    private boolean cautaId(String nume) {
        for (int i = 1; i <= 100; i++) {
            try {
                String apiUrl = "http://localhost:8080/api/users/" + i;

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

                    JSONObject userData = new JSONObject(response.toString());

                    String validNume = userData.getString("nume");

                    if (validNume.equals(nume)) {
                        validNumeid = userData.getInt("id");
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}
