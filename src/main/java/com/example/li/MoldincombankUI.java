package com.example.li;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

public class MoldincombankUI extends JFrame {
    private JPanel rectanglePanel;
    private JPanel interactionPanel;

    public MoldincombankUI() {
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

                    // Display success label
                    JLabel successLabel = new JLabel("Success");
                    successLabel.setFont(new Font("Arial", Font.BOLD, 24));
                    successLabel.setForeground(Color.GREEN);
                    successLabel.setBounds(50, 50, 200, 50);  // Set position and size
                    interactionPanel.add(successLabel);
                    successLabel.setVisible(true);

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
}
