package com.frontend.li;


import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
class BackgroundPanel extends JPanel {
    private Color backgroundColor;

    public BackgroundPanel(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

public class Moldindconbank extends JFrame {
    private JPanel rectanglePanel;
    private JPanel interactionPanel;

    private Login loginPanel;
    private Register registerPanel;

    public Moldindconbank() {
        setTitle("Aplicatie dezvoltata de Spînu Andrei IA2201");
        setSize(400, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = Color.BLUE;
        BackgroundPanel mainPanel = new BackgroundPanel(backgroundColor);
        mainPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Moldindconbank", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(label, BorderLayout.NORTH);

        interactionPanel = createInteractionPanel();
        mainPanel.add(interactionPanel, BorderLayout.CENTER);

        JPanel numpadPanel = createNumpadPanel();

        rectanglePanel = createRectanglePanel();

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(rectanglePanel, BorderLayout.CENTER);

        loginPanel = new Login(this);
        registerPanel = new Register();


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(numpadPanel, BorderLayout.WEST);
        bottomPanel.add(eastPanel, BorderLayout.CENTER);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        interactionPanel.setFocusable(true);
        interactionPanel.requestFocusInWindow();
        interactionPanel.addKeyListener(new KeyListener() {
            JLabel successLabel;  // Declare the label here to make it accessible within the key listener

            @Override
            public void keyTyped(KeyEvent e) {
                // Not used
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    interactionPanel.removeAll();
                    interactionPanel.setLayout(null);  // Set layout to null

                    // Send a GET request and check if id 1 is present
                    // For simplicity, assuming a boolean response
                    boolean isId1Present = checkIfId1IsPresent();

                    JLabel resultLabel = new JLabel();
                    resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
                    resultLabel.setBounds(50, 50, 200, 50);  // Set position and size

                    if (isId1Present) {
                        switchToLoginPanel();

                    } else {
                        //Register thing
                    }

                    interactionPanel.add(resultLabel);
                    resultLabel.setVisible(true);

                    // Repaint the panel
                    interactionPanel.repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used
            }
        });


    }

    private JPanel createInteractionPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panel.add(new JLabel("Apasati ENTER pentru a introduce cardul", SwingConstants.CENTER));
        return panel;
    }

    private JPanel createRectanglePanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                g.setColor(new Color(0, 0, 0, 0));
                g.fillRect(0, 0, panelWidth, panelHeight);

                int smallRectWidth = 90;
                int smallRectHeight = 20;
                int smallRectX = (panelWidth - smallRectWidth) / 2;
                int smallRectY = (panelHeight - smallRectHeight - 130);

                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(smallRectX, smallRectY, smallRectWidth, smallRectHeight);

                int circleDiameter = 20;
                int circleX = (panelWidth - circleDiameter) / 2;
                int circleY = smallRectY + smallRectHeight + 10;

                g.setColor(Color.darkGray);
                g.fillOval(circleX, circleY, circleDiameter, circleDiameter);

                g.setColor(Color.black);
                g.drawLine(circleX, circleY, circleX + circleDiameter, circleY + circleDiameter);
            }
        };
        panel.setPreferredSize(new Dimension(50, 10));
        return panel;
    }

    private JPanel createNumpadPanel() {
        JPanel numpadPanel = new JPanel();
        numpadPanel.setLayout(new GridLayout(4, 3, 10, 10));

        String[] buttonLabels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "E", "0", "C"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            numpadPanel.add(button);
        }

        return numpadPanel;
    }


    private boolean checkIfId1IsPresent() {
        try {
            // Replace this URL with your actual API endpoint
            String apiUrl = "http://localhost:8080/users/get/1";  // Assuming /users/1 represents checking for id 1

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Attempt to parse the response as JSON
                try {
                    JSONObject jsonResponse = new JSONObject(response.toString());

                    // Assuming the response has an "id" field
                    if (jsonResponse.has("id")) {
                        int id = jsonResponse.getInt("id");
                        return id == 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // Handle HTTP error response
                System.out.println("GET request failed: HTTP error code - " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkIfPinIsCorrect(String enteredPin) {
        try {
            String apiUrl = "http://localhost:8080/users/get/1";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Attempt to parse the response as JSON
                try {
                    JSONObject jsonResponse = new JSONObject(response.toString());

                    // Assuming the response has a "pin" field
                    if (jsonResponse.has("pin")) {
                        String correctPin = jsonResponse.getString("pin");
                        return enteredPin.equals(correctPin);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("GET request failed: HTTP error code - " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public JPanel getInteractionPanel() {
        return interactionPanel;
    }


    public void switchToLoginPanel() {
        loginPanel.setVisible(true);
    }
}