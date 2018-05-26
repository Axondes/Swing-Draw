package com.company.swingdraw;

import javax.swing.*;

import java.awt.*;


public class BackgroundMenuBar extends JMenuBar {
    Color bgColor=new Color(20,20,20);

    public void setColor(Color color) {
        bgColor=color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());

    }
}
