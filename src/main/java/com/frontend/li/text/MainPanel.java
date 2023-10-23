package com.frontend.li.text;

import javax.swing.*;
import java.awt.*;
import com.frontend.li.misc.Methods;
public class MainPanel extends Methods{
    private JFrame frame;

    public MainPanel() {
        frame = new JFrame("Aplicatie dezvoltata de Spînu Andrei IA2201");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

//DREPTUNGHI ALBASTRU
        JPanel albastru = createBlueRectanglePanel(15, 0, 350, 35, Color.blue);
        frame.add(albastru, BorderLayout.NORTH);
//SCRIS DENUMIRE
        JLabel denumire = createLabelOnBlueRectangle("Moldindconbank", SwingConstants.CENTER, 20, Color.WHITE);
        albastru.add(denumire);

//ECREAN
        JPanel ecran = createPanelWithRectangle(10, 35, 230, 300, Color.gray);
        frame.add(ecran, BorderLayout.CENTER);
//DREAPTA CARD




//ButonTest
      // JPanel button = createPanelWithButton("apasa-ma",250,50,50,50);
     //   frame.add(button);



        frame.setVisible(true);
    }


}