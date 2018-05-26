/*@author Draceon/Sven
*Adapted from Sylsau
*Some code is borrowed from Sylsau as I am trying to figure out how to use Java graphics.
*A lot has been modified and added such as the use of varying strokes and changeable colors.
*I initially tried to implement listeners, but that didn't work, so I reverted to Sylsau's method,
*The paintcomponent and clear methods are almost entirely Sylsau's work. I do understand how they work.
*/

package com.company.swingdraw;

import javax.swing.*;
import javax.imageio.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import jpen.*;
import jpen.Pen;
import jpen.event.PenAdapter;
import jpen.event.PenListener;
import jpen.owner.PenOwner;
import jpen.owner.multiAwt.AwtPenToolkit;
import java.util.Stack;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class DrawSpace extends JComponent{

    private final SizedStack<Image> undoStack = new SizedStack<>(15);

    private final SizedStack<Image> redoStack = new SizedStack<>(15);

    private int compWidth = 1620;

    private int compHeight = 1080;

    private Image image;

    private Graphics2D g2;

    private int initx, inity, finx, finy;

    private int StrokeSize;

    private double offsetX = 0, offsetY = 0, initXOffset, initYOffset;

    private int width = 1620, height = 1080;

    double zoomX = 1, zoomY = 1;

    JFrame Picker;
    ColorPicker colorPicker;

    private boolean drawingTablet;

    private int pressureStroke = 0;

    private String mode = "pen";

    private ZoomInfo zoomInfo;

    private ColorPicker c;

    private Color eraser = Color.WHITE;

    public DrawSpace(ColorPicker c, StrokePicker s, ZoomInfo z){

        this.c = c;

        zoomInfo = z;

        StrokeSize = s.getStrokeChosen();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // save coord x,y when mouse is pressed
                saveToUndoStack(image);

                StrokeSize = s.getStrokeChosen();

                initx = e.getX();
                inity = e.getY();
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                // save coord x,y when mouse is pressed
                initXOffset = offsetX;
                initYOffset = offsetY;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                //Sets the color of the paint and the width of the stroke.
                if(mode.equals("pen") || mode.equals("eraser")) {
                    g2.setPaint(c.getColorChosen());

                    if(mode.equals("eraser")){
                        g2.setPaint(eraser);
                    }

                    if (drawingTablet == false) {
                        g2.setStroke(new BasicStroke(StrokeSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    } else if (drawingTablet == true) {
                        g2.setStroke(new BasicStroke(pressureStroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    }


                    //Sets the destination points.

                    finx = e.getX();
                    finy = e.getY();

                    if (g2 != null) {
                        //Draws the line if the canvas exists
                        g2.drawLine((int)((initx / zoomX) - (offsetX)), (int)((inity / zoomY) - (offsetY)), (int)((finx / zoomX) - (offsetX)), (int)((finy / zoomY)-(offsetY)));
                        //Repaints the canvas to update everything
                        repaint();
                        //Resets the initial values to the new values
                        initx = finx;
                        inity = finy;
                    }
                }else if(mode.equals("pan")){
                    finx = e.getX();
                    finy = e.getY();
                    offsetX = (((double)finx - (double)initx)/zoomX + (initXOffset));
                    offsetY = (((double)finy - (double)inity)/zoomY + (initYOffset));

                    setImage(image);

                    repaint();
                }
            }
        });



        AwtPenToolkit.addPenListener(this, new PenAdapter() {
            @Override
            public void penLevelEvent(PLevelEvent ev) {
                paintStroke(ev);
            }
        });
    }

    public void clear(Color a, Color b) {
        g2.setPaint(a);
        //Draw a filled rectangle that is white.

        g2.fillRect(0, 0, width, height);

        g2.setPaint(b);

        c.setRed(b.getRed());
        c.setGreen(b.getGreen());
        c.setBlue(b.getBlue());

        eraser = a;

        double widthRatio = (double)getWidth()/(double)width;
        double heightRatio = (double)getHeight()/(double)height;

        if(widthRatio < heightRatio){
            zoom(widthRatio - 1);
        }

        if(heightRatio < widthRatio){
            zoom(heightRatio - 1);
        }

        repaint();
    }

    public void paintComponent (Graphics g){
        if (image == null) {
            // Creates an image if non exists.
            image = createImage(width, height);
            g2 = (Graphics2D) image.getGraphics();
            // Enables antialiasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Clears the canvas

            clear(Color.WHITE, Color.BLACK);
        }

        g.drawImage(image, (int) (offsetX * zoomX), (int) (offsetY * zoomY), (int)(width*zoomX),(int)(height*zoomY),null);
    }

    public void paintStroke (PLevelEvent ev) {
        double pressure = ev.pen.getLevelValue(PLevel.Type.PRESSURE);

        pressureStroke = (int)(StrokeSize * ((pressure*0.9) + 0.1)) + 1;
    }

    public BufferedImage saveImage () {
        BufferedImage bImage = new BufferedImage(image.getWidth(null), image.getHeight(null), TYPE_INT_RGB);

        Graphics2D tempGr = bImage.createGraphics();
        tempGr.drawImage(image, 0, 0, null);
        tempGr.dispose();

        return bImage;
    }

    public BufferedImage saveImage (boolean a) {
        BufferedImage bImage = new BufferedImage(image.getWidth(null), image.getHeight(null), TYPE_INT_RGB);

        Graphics2D tempGr = bImage.createGraphics();
        tempGr.drawImage(image, (int) (offsetX*zoomX), (int) (offsetY*zoomY), (int)((width + (int)offsetX)*zoomX),(int)((height + (int)offsetY)*zoomY), 0,0,width,height,null);
        tempGr.dispose();

        return bImage;
    }

    public void drawingTabletToggle (boolean e) {
        drawingTablet = e;
    }

    public void undo () {
        saveToRedoStack(image);
        if(undoStack.size() > 0){
            openDetails(bufferImage(undoStack.pop()),true);
        }
    }

    public void redo () {
        saveToUndoStack(image);
        if(redoStack.size() > 0){
            setImage(redoStack.pop());
        }
    }

    private void saveToUndoStack (Image backup){
        undoStack.push(bufferImage(backup));
    }

    private void saveToRedoStack (Image backup){
        redoStack.push(bufferImage(backup));
    }

    public BufferedImage bufferImage(Image backup){
        BufferedImage copy = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics copyG = copy.createGraphics();
        copyG.drawImage(backup, 0, 0, getWidth(), getHeight(), null);
        return copy;
    }

    public void setImage (Image backup) {
        Graphics2D picture = (Graphics2D) backup.getGraphics();

        picture.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2 = picture;

        image = backup;

        repaint();
    }

    public void openDetails (BufferedImage open, boolean undo) {
        width = open.getWidth();
        height = open.getHeight();

        image = null;

        repaint();

        image = open;

        if(undo == false) {
            resetZoom();

            double widthRatio = (double) getWidth() / (double) width;
            double heightRatio = (double) getHeight() / (double) height;

            if (widthRatio < heightRatio) {
                zoom(widthRatio - 1);
            }

            if (heightRatio < widthRatio) {
                zoom(heightRatio - 1);
            }
        }

        if(undo == true) {
            zoom(0);
        }

        repaint();

        setImage(image);
    }

    public void zoom (double zoomFactor){
        zoomX += zoomFactor;
        zoomY += zoomFactor;

        g2.scale(zoomX, zoomY);

        setImage(image);

        zoomInfo.changeZoom(zoomX);

        repaint();
    }

    public void setMode (String newmode){
        mode = newmode;
    }

    public void setDimensions (int w, int h){
        width = w;
        height = h;

        image = null;

        repaint();
    }

    public void resetZoom () {
        zoomX = 1;
        zoomY = 1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setComp (double inWidth, double inHeight){
    }

    public double getZoom () {
        return zoomX;
    }

    public double getOffsetX () {
        return offsetX;
    }

    public double getOffsetY () {
        return offsetY;
    }
}
