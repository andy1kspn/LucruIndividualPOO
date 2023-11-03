package com.frontend.li;

import com.frontend.li.Conectare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class InsertCard extends JPanel {
    private JPanel rectanglePanel;
    private JPanel interactionPanel;
    private BufferedImage[] backgroundImages;
    private int currentImageIndex;
    private Timer animationTimer;
    private JLabel customLabel;

    public InsertCard() {
        try {
            backgroundImages = new BufferedImage[]{
                    ImageIO.read(new File("C:\\Users\\Spinu Andrei\\Desktop\\Java\\LI\\src\\main\\java\\main.png")),
                    ImageIO.read(new File("C:\\Users\\Spinu Andrei\\Desktop\\Java\\LI\\src\\main\\java\\animation1.png")),
                    ImageIO.read(new File("C:\\Users\\Spinu Andrei\\Desktop\\Java\\LI\\src\\main\\java\\animation2.png")),
                    ImageIO.read(new File("C:\\Users\\Spinu Andrei\\Desktop\\Java\\LI\\src\\main\\java\\animation3.png")),
                    ImageIO.read(new File("C:\\Users\\Spinu Andrei\\Desktop\\Java\\LI\\src\\main\\java\\main.png"))

            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentImageIndex = 0;

        animationTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImageIndex < backgroundImages.length - 1) {
                    currentImageIndex++;
                } else {
                    animationTimer.stop();

                    customLabel.setText("PROCESARE...");

                    Timer delayTimer = new Timer(3000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            closePanelAndOpenNewOne();
                        }
                    });
                    delayTimer.setRepeats(false);
                    delayTimer.start();
                }
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !animationTimer.isRunning()) {
                    animationTimer.start();
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();

        setLayout(null);

        customLabel = new JLabel("Apăsați pe ENTER pentru a introduce cardul!");
        customLabel.setForeground(Color.BLUE);
        customLabel.setBounds(70, 50, 500, 250);
        add(customLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImages != null && backgroundImages.length > 0) {
            g.drawImage(backgroundImages[currentImageIndex], 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void closePanelAndOpenNewOne() {
        Window parentWindow = SwingUtilities.windowForComponent(this);
        if (parentWindow != null) {
            parentWindow.dispose();
        }

        Conectare.showNewPanel();
    }

    public static void showNewPanel() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Moldindconbank ATM - Dezvoltat de Spinu Andrei IA2201");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            InsertCard insertCard = new InsertCard();
            frame.getContentPane().add(insertCard);
            frame.setResizable(false);
            frame.setSize(417, 485);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
