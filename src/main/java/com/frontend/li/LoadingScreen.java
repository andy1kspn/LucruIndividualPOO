package com.frontend.li;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingWorker;

public class LoadingScreen extends JFrame {
    private CustomPanel panel;
    private JProgressBar progressBar;
    private Timer timer;
    private int progressValue;

    public LoadingScreen() {
        setTitle("Bine ati venit la MICB!");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new CustomPanel();

        progressBar = new JProgressBar(0, 1);   //Din 1 in 15!
        progressBar.setStringPainted(true);

        add(panel);
        panel.setLayout(new BorderLayout());

        panel.add(progressBar, BorderLayout.SOUTH);

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 1; i++) {  //din 1 in 15 inapoi!
                    Thread.sleep(1000);
                    publish(i);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int value = chunks.get(chunks.size() - 1);
                progressBar.setValue(value);
            }

            @Override
            protected void done() {
                dispose();
                openNewPanel();
            }
        };

        worker.execute();
    }

    private void openNewPanel() {
        InsertCard.showNewPanel();
    }

    private class CustomPanel extends JPanel {
        private Image background;

        public CustomPanel() {
            background = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Spinu Andrei\\Desktop\\Java\\LI\\src\\main\\java\\loading_screen.png");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
