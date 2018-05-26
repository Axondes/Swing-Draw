package com.company.swingdraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFile extends JFrame {
    public NewFile(DrawSpace def, JFrame frame){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFormattedTextField ImgWidth = new JFormattedTextField((int)screenSize.getWidth());
        JFormattedTextField ImgHeight = new JFormattedTextField((int)screenSize.getHeight());

        JLabel Width = new JLabel("Width");
        JLabel Height = new JLabel("Height");

        JButton Create = new JButton("Create");

        Create.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display/center the jdialog when the button is pressed
                def.setDimensions((int) ImgWidth.getValue(), (int) ImgHeight.getValue());
                frame.setVisible(true);

                dispose();
            }
        });

        ImgWidth.setColumns(5);
        ImgHeight.setColumns(5);

        getContentPane().setBackground(new Color(40,40,40));
        Width.setForeground(Color.WHITE);
        Height.setForeground(Color.WHITE);
        setResizable(false);
        setVisible(true);
        setSize(300,75);

        add(Width);
        add(ImgWidth);
        add(Height);
        add(ImgHeight);
        add(Create);

        setLayout(new FlowLayout());
    }
}
