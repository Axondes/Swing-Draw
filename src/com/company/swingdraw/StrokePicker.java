package com.company.swingdraw;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jpen.*;



public class StrokePicker extends JComponent implements FocusListener{
    int CurrentStroke = 1;

    JFormattedTextField SNum;

    public StrokePicker (){
        JSlider Stroke = new JSlider(0,500, CurrentStroke);
        SNum = new JFormattedTextField(1);
        SNum.setColumns(3);
        SNum.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Stroke.setValue((int)evt.getNewValue());
                CurrentStroke = (int)evt.getNewValue();
            }
        });

        Stroke.setMajorTickSpacing(50);
        Stroke.setMinorTickSpacing(10);
        Stroke.setPaintTicks(true);
        Stroke.setOpaque(false);
        Stroke.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                CurrentStroke = Stroke.getValue();
                SNum.setValue(CurrentStroke);
            }
        });

        setLayout(new FlowLayout());

        add(Stroke);
        add(SNum);
    }

    public int getStrokeChosen () {
        return CurrentStroke;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Stroke", 2,20);
    }

    public void focusGained (FocusEvent e) {
        JFormattedTextField tempText = (JFormattedTextField)e.getSource();
        if(e.getSource() == SNum) {
            tempText.select(0, tempText.getText().length());
        }
    }

    public void focusLost (FocusEvent e) {
        JFormattedTextField tempText = (JFormattedTextField)e.getSource();
        tempText.select(0, 0);
    }
}
