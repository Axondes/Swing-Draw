package com.company.swingdraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import javax.swing.plaf.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

        /*@author Draceon/Sven
* Everything is created by me except for within the DrawSpace file, check DrawSpace for details.
 */

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }

        JFrame frame = new JFrame("Swing Draw");
        frame.setSize(1920, 1080);
        frame.getContentPane().setBackground(new Color(40,40,40));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);

        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        StrokePicker strokePicker = new StrokePicker();
        ZoomInfo zoomInfo = new ZoomInfo();
        DrawSpace canvas = new DrawSpace(colorPicker, strokePicker, zoomInfo);
        SideBar bar = new SideBar();
        BrushPicker brushPicker = new BrushPicker(canvas);

        bar.setBounds(1620,0,300,frame.getContentPane().getHeight());

        frame.add(bar);

        colorPicker.setBounds(0,0,300,200);

        bar.add(colorPicker);

        strokePicker.setBounds(0,230,300,50);

        bar.add(strokePicker);

        brushPicker.setBounds(0, 280, 300, 50);

        bar.add(brushPicker);

        zoomInfo.setBounds (0,1065,1920,100);

        frame.add(zoomInfo);

        canvas.setBounds(0,0,3840,2160);

        frame.add(canvas);

        MainMenuBar menuBar = new MainMenuBar(frame, canvas, colorPicker, zoomInfo);

        frame.addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent e){
                canvas.setBounds(0,0,frame.getContentPane().getWidth() - 300,frame.getContentPane().getHeight() - 25);
                canvas.setComp(frame.getContentPane().getWidth() - 300, frame.getContentPane().getHeight() - 25);
                bar.setBounds(frame.getContentPane().getWidth() - 300,0,300,frame.getContentPane().getHeight());
                zoomInfo.setBounds(0, frame.getContentPane().getHeight() - 25, frame.getContentPane().getWidth(), 100);
            }
        });

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        NewFile dialog = new NewFile(canvas, frame);
    }
}
