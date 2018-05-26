package com.company.swingdraw;

import javax.swing.*;
import java.awt.*;

public class ZoomInfo extends JComponent {
    JLabel zoom;
    String recording = "Not Recording";

    public ZoomInfo(){
        zoom = new JLabel ("100%   " + recording);

        zoom.setForeground(Color.WHITE);

        add(zoom);
        setLayout(new FlowLayout());
    }

    public void changeZoom (double zoomLevel){
        zoom.setText(" " + ((int)(zoomLevel * 100)) + "%   " + recording);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(20,20,20));
        g2d.fillRect(0, 0, getWidth(), getHeight());

    }

    public void changeRecording () {
        if(recording.equals("Not Recording")){
            recording = "Recording";
        } else {
            recording = "Not Recording";
        }
    }
}
