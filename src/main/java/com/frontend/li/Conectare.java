package com.frontend.li;

import com.frontend.li.InsertCard;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Conectare extends JPanel {

    private JPanel rectanglePanel;
    private JPanel interactionPanel;
    private BufferedImage backgroundImage;
    private JLabel customLabel;
    private JLabel numarCardLabel;
    private JTextField numarCardInput;
    private JLabel pinLabel;
    private JPasswordField pinInput;
    private JButton verifyButton;
    private JLabel inregistrativaLabel;
    private JLabel errorLabel;
    private String userName;
    private Integer userId;
    public Conectare() {
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\Spinu Andrei\\Desktop\\Java\\LI\\src\\main\\java\\panel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setFocusable(true);
        requestFocusInWindow();

        setLayout(null);

        customLabel = new JLabel("Conectare:");
        customLabel.setForeground(Color.white);
        customLabel.setBounds(300, -5, 500, 250);
        customLabel.setFont(new Font("Arial", Font.BOLD, 15));
        add(customLabel);

        numarCardLabel = new JLabel("Numarul cardului:");
        numarCardLabel.setForeground(Color.white);
        numarCardLabel.setBounds(200, 150, 150, 30);
        add(numarCardLabel);

        numarCardInput = new JTextField();
        numarCardInput.setBounds(350, 150, 150, 30);
        add(numarCardInput);

        pinLabel = new JLabel("PIN:");
        pinLabel.setForeground(Color.white);
        pinLabel.setBounds(200, 200, 150, 30);
        add(pinLabel);

        pinInput = new JPasswordField();
        pinInput.setBounds(350, 200, 150, 30);
        add(pinInput);

        verifyButton = new JButton("CONECTARE");
        verifyButton.setBounds(250, 250, 200, 30);
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numarCard = numarCardInput.getText();
                String pin = new String(pinInput.getPassword());


                boolean verificationSuccessful = verifyCardAndPin(numarCard, pin);

                if (verificationSuccessful) {
                    closePanelAndOpenNewOne();
                } else {
                    errorLabel.setText("Eroare: Numar card sau PIN incorect!");
                }
            }
        });
        add(verifyButton);

        inregistrativaLabel = new JLabel("Inregistrativa!");
        inregistrativaLabel.setForeground(Color.blue);
        inregistrativaLabel.setBounds(300, 300, 150, 30);
        inregistrativaLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        inregistrativaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openInregistrareForm();
            }
        });
        add(inregistrativaLabel);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.red);
        errorLabel.setBounds(245, 320, 300, 30);
        add(errorLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void closePanelAndOpenNewOne() {
        Window parentWindow = SwingUtilities.windowForComponent(this);
        if (parentWindow != null) {
            parentWindow.dispose();
        }

        BancaPersonala.showNewPanel(userName,userId);
    }

    private boolean verifyCardAndPin(String numarCard, String pin) {
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

                    String validNumarCard = userData.getString("nr_card");
                    String validPin = userData.getString("pin");

                    if (numarCard.equals(validNumarCard) && pin.equals(validPin)) {
                        userName = userData.getString("nume");
                        userId = userData.getInt("id");
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private void openInregistrareForm() {
        Window parentWindow = SwingUtilities.windowForComponent(this);
        if (parentWindow != null) {
            parentWindow.dispose();
        }

        Inregistrare.showNewPanel();
    }
    public static void showNewPanel() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Moldindconbank ATM - Dezvoltat de Spinu Andrei IA2201");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Conectare conectare = new Conectare();
            frame.getContentPane().add(conectare);
            frame.setResizable(false);
            frame.setSize(707, 485);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
