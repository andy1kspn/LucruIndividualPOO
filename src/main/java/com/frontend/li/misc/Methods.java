package com.frontend.li.misc;

import javax.swing.*;
import java.awt.*;

public class Methods {
    protected JPanel createPanelWithLabel(String text, int x, int y, int sizex, int sizey) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, sizex, sizey);
        JLabel label = new JLabel(text);
        panel.add(label);
        return panel;
    }
    protected JPanel createPanelWithLabel(String labelText, int alignment, int fontSize) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, fontSize));
        label.setHorizontalAlignment(alignment);
        panel.add(label);
        return panel;
    }
    protected JPanel createPanelWithButton(String buttonText, int x, int y, int sizex, int sizey) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, sizex, sizey);
        JButton button = new JButton(buttonText);
        panel.add(button);
        return panel;
    }
    protected JPanel createPanelWithRectangle(int x, int y, int width, int height, Color color) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.fillRect(x, y, width, height);
            }
        };
        return panel;
    }
    protected JPanel createPanelWithTextInput(String labelText, int x, int y, int width, int height) {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 100, height);
        panel.add(label);

        JTextField textField = new JTextField();
        textField.setBounds(x + 100, y, width - 100, height);
        panel.add(textField);

        return panel;
    }

    protected JPanel createBlueRectanglePanel(int x, int y, int width, int height, Color color) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.fillRect(x, y, width, height);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

    protected JLabel createLabelOnBlueRectangle(String labelText, int alignment, int fontSize, Color labelColor) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, fontSize));
        label.setHorizontalAlignment(alignment);
        label.setForeground(labelColor);
        label.setBounds(0, 0, 400, 30);
        return label;
    }
    protected JPanel createBlueRectanglePanelWithLabel(String labelText, int alignment, int fontSize, Color backgroundColor, Color textColor) {
        JPanel panel = new JPanel();

        // Create a blue rectangle panel
        JPanel blueRectangle = createPanelWithRectangle(15, 0, 350, 35, backgroundColor);
        panel.add(blueRectangle);

        // Create the label
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, fontSize));
        label.setHorizontalAlignment(alignment);
        label.setForeground(textColor);
        blueRectangle.add(label);

        return panel;
    }
}
