package com.company.swingdraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.event.KeyEvent.*;

public class BrushPicker extends JComponent {
    public BrushPicker (DrawSpace def){
        JButton pen = new JButton(new ImageIcon("Pen.png"));
        JButton pan = new JButton(new ImageIcon("Pan.png"));
        JButton eraser = new JButton(new ImageIcon("Eraser.png"));

        UIManager.put("JButton.background", new Color(20,20,20));
        UIManager.put("JButton.foreground", new Color(20,20,20));

        pen.setOpaque(true);
        pan.setOpaque(true);
        eraser.setOpaque(true);

        pen.setBackground(new Color(20,20,20));
        pan.setBackground(new Color(20,20,20));
        eraser.setBackground(new Color(20,20,20));

        pen.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display/center the jdialog when the button is pressed
                def.setMode("pen");
            }
        });

        pan.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display/center the jdialog when the button is pressed
                def.setMode("pan");
            }
        });

        eraser.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display/center the jdialog when the button is pressed
                def.setMode("eraser");
            }
        });

        add(pen);
        add(pan);
        add(eraser);

        setLayout(new FlowLayout());
    }
}
