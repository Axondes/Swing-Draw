package com.company.swingdraw;

import javax.swing.*;
import java.awt.*;

public class WaitForEncode extends JFrame {

    public WaitForEncode() {
        setSize(200,200);
        setLayout(null);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        JLabel i = new JLabel("<html><h1 style='text-align:center'>Warning</h1><p style='text-align:center'>The video and audio files are currently being merged, please wait until this window closes before turning off the program.</p></html>");
        i.setBounds(0,0,175,175);

        add(i);

        setVisible(true);
    }

}
