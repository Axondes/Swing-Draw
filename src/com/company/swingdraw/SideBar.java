package com.company.swingdraw;

import javax.swing.*;
import java.awt.*;

public class SideBar extends JPanel {

    public SideBar() {
        setLayout(null);
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(40,40,40));
        g2d.fillRect(0, 0, getWidth(), getHeight());

    }

}
