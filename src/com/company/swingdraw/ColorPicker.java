package com.company.swingdraw;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ColorPicker extends JComponent implements FocusListener{

    JSlider R;
    JSlider G;
    JSlider B;

    JFormattedTextField RNum;
    JFormattedTextField GNum;
    JFormattedTextField BNum;

    int Red = 0;
    int Green = 0;
    int Blue = 0;

    Color Current;


    public ColorPicker (Color Chosen){
        Current = Chosen;

        R = new JSlider(0, 255, Current.getRed());
        G = new JSlider(0, 255, Current.getGreen());
        B = new JSlider(0, 255, Current.getBlue());
        RNum = new JFormattedTextField(0);
        GNum = new JFormattedTextField(0);
        BNum = new JFormattedTextField(0);

        //Defines textfield for red length and adds listeners.

        RNum.setColumns(3);
        RNum.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                R.setValue((int)evt.getNewValue());
                Red = (int)evt.getNewValue();
            }
        });

        //Defines textfield for green length and adds listeners.

        GNum.setColumns(3);
        GNum.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                G.setValue((int)evt.getNewValue());
                Green = (int)evt.getNewValue();
            }
        });

        //Defines textfield for blue length and adds listeners.

        BNum.setColumns(3);
        BNum.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                B.setValue((int)evt.getNewValue());
                Blue = (int)evt.getNewValue();
            }
        });

        R.setMajorTickSpacing(64);
        R.setMinorTickSpacing(4);
        R.setPaintTicks(true);
        R.setOpaque(false);
        R.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                Red = R.getValue();
                RNum.setValue(Red);
                rePaint();
            }
        });
        G.setMajorTickSpacing(64);
        G.setMinorTickSpacing(4);
        G.setPaintTicks(true);
        G.setOpaque(false);
        G.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                Green = G.getValue();
                GNum.setValue(Green);
                rePaint();
            }
        });
        B.setMajorTickSpacing(64);
        B.setMinorTickSpacing(4);
        B.setPaintTicks(true);
        B.setOpaque(false);
        B.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                Blue = B.getValue();
                BNum.setValue(Blue);
                rePaint();
            }
        });

        setLayout(new FlowLayout());

        setBackground(new Color(40,40,40));

        add(R);
        add(RNum);
        add(G);
        add(GNum);
        add(B);
        add(BNum);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Current);
        g.fillRect(0, 120, 300,100);

        g.setColor(Color.WHITE);
        g.drawString("Red", 2,20);
        g.drawString("Green", 2,55);
        g.drawString("Blue", 2,90);
    }

    public void rePaint(){
        Current = new Color(Red, Green, Blue);
        repaint();
    }

    public Color getColorChosen (){
        return Current;
    }

    public void setRed(int r){
        Red = r;
        R.setValue(r);
        RNum.setValue(r);
    }

    public void setGreen(int g){
        Green = g;
        G.setValue(g);
        GNum.setValue(g);
    }

    public void setBlue(int b){
        Blue = b;
        B.setValue(b);
        BNum.setValue(b);
    }

    public void focusGained (FocusEvent e) {
        JFormattedTextField tempText = (JFormattedTextField)e.getSource();
        if(e.getSource() == RNum || e.getSource() == GNum || e.getSource() == BNum) {
            tempText.select(0, tempText.getText().length());
        }
    }

    public void focusLost (FocusEvent e) {
        JFormattedTextField tempText = (JFormattedTextField)e.getSource();
        tempText.select(0, 0);
    }
}
