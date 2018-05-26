package com.company.swingdraw;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenuBar extends JPanel {
    DrawSpace def;

    boolean drawingTabletBool = false;

    File imageSave = new File(System.getProperty("user.home") + "\\Pictures\\picture.png");

    File imageOpen;

    File videoSave = new File(System.getProperty("user.home") + "\\Videos\\video.mp4");

    File soundSave = new File(System.getProperty("user.home") + "\\Videos\\sound.mp3");

    SoundCreator sound;

    ZoomInfo zoomInfo;

    MediaMerger merger;

    public VideoCreator main = new VideoCreator(def, zoomInfo);


    public MainMenuBar (JFrame frame, DrawSpace canvas, ColorPicker colorPicker, ZoomInfo zoomInfo) {

        if(imageSave.exists())

        def = canvas;
        this.zoomInfo = zoomInfo;

        UIManager.put("MenuBar.background", new Color(20,20,20));
        UIManager.put("Menu.background", new Color(20,20,20));
        UIManager.put("MenuItem.background", new Color(20,20,20));
        UIManager.put("Menu.foreground", new Color(255,255,255));
        UIManager.put("MenuBar.foreground", new Color(255,255,255));
        UIManager.put("MenuItem.foreground", new Color(255,255,255));
        UIManager.put("Menu.opaque", true);
        UIManager.put("MenuBar.opaque", true);
        UIManager.put("MenuItem.opaque", true);
        UIManager.put("PopupMenu.border", false);
        UIManager.put("MenuBar.border", false);
        UIManager.put("MenuItem.acceleratorForeground", new Color (150,150,150));

        BackgroundMenuBar menuBar = new BackgroundMenuBar();

        JMenu file = new JMenu("File");

        menuBar.add(file);

        JMenuItem save = new JMenuItem(new AbstractAction("Save") {
            public void actionPerformed(ActionEvent e){
                save();
            }
        });
        save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem newF = new JMenuItem(new AbstractAction("New") {
            public void actionPerformed(ActionEvent e){
                frame.setVisible(false);
                def.resetZoom();
                NewFile tempNew = new NewFile(def, frame);
            }
        });
        newF.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem openF = new JMenuItem(new AbstractAction("Open") {
            public void actionPerformed(ActionEvent e){
                open();
            }
        });
        openF.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem saveAs = new JMenuItem(new AbstractAction("Save As") {
            public void actionPerformed(ActionEvent e){
                saveNew();
            }
        });

        JMenuItem saveAsVideo = new JMenuItem(new AbstractAction("Save Video As") {
            public void actionPerformed(ActionEvent e){
                saveNewVideo();
            }
        });

        JMenuItem exit = new JMenuItem(new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });

        JMenu edit = new JMenu("Edit");

        menuBar.add(edit);

        JMenuItem drawingTablet = new JMenuItem(new AbstractAction("Toggle Drawing Tablet"){
            public void actionPerformed(ActionEvent e){
                toggle();
            }
        });

        JMenuItem undo = new JMenuItem(new AbstractAction("Undo"){
            public void actionPerformed(ActionEvent e){
                def.undo();
            }
        });

        undo.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem redo = new JMenuItem(new AbstractAction("Redo"){
            public void actionPerformed(ActionEvent e){
                def.redo();
            }
        });

        redo.setAccelerator(KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenu view = new JMenu("View");

        menuBar.add(view);

        JMenuItem zoomIn = new JMenuItem(new AbstractAction("Zoom In"){
            public void actionPerformed(ActionEvent e){
                def.zoom(.2);
            }
        });

        zoomIn.setAccelerator(KeyStroke.getKeyStroke('=', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem zoomOut = new JMenuItem(new AbstractAction("Zoom Out"){
            public void actionPerformed(ActionEvent e){
                def.zoom(-.2);
            }
        });

        JMenu tools = new JMenu("Tools");

        menuBar.add(tools);

        JMenuItem pen = new JMenuItem(new AbstractAction("Pen"){
            public void actionPerformed(ActionEvent e){
                def.setMode("pen");
            }
        });

        pen.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem pan = new JMenuItem(new AbstractAction("Pan"){
            public void actionPerformed(ActionEvent e){
                def.setMode("pan");
            }
        });

        pan.setAccelerator(KeyStroke.getKeyStroke('H', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem eraser = new JMenuItem(new AbstractAction("Eraser"){
            public void actionPerformed(ActionEvent e){
                def.setMode("eraser");
            }
        });

        eraser.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem clear = new JMenuItem(new AbstractAction("Clear - White Background"){
            public void actionPerformed(ActionEvent e){
                def.clear(Color.WHITE, Color.BLACK);
            }
        });

        clear.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem clear1 = new JMenuItem(new AbstractAction("Clear - Black Background"){
            public void actionPerformed(ActionEvent e){
                def.clear(Color.BLACK, Color.WHITE);
            }
        });

        clear1.setAccelerator(KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenu colors = new JMenu("Colors");

        menuBar.add(colors);

        JMenuItem Red = new JMenuItem(new AbstractAction("Red"){
            public void actionPerformed(ActionEvent e){
                colorPicker.setRed(255);
                colorPicker.setGreen(0);
                colorPicker.setBlue(0);
            }
        });

        Red.setAccelerator(KeyStroke.getKeyStroke('1', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem Green = new JMenuItem(new AbstractAction("Green"){
            public void actionPerformed(ActionEvent e){
                colorPicker.setRed(0);
                colorPicker.setGreen(255);
                colorPicker.setBlue(0);
            }
        });

        Green.setAccelerator(KeyStroke.getKeyStroke('4', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem Blue = new JMenuItem(new AbstractAction("Blue"){
            public void actionPerformed(ActionEvent e){
                colorPicker.setRed(0);
                colorPicker.setGreen(0);
                colorPicker.setBlue(255);
            }
        });

        Blue.setAccelerator(KeyStroke.getKeyStroke('5', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem Orange = new JMenuItem(new AbstractAction("Orange"){
            public void actionPerformed(ActionEvent e){
                colorPicker.setRed(255);
                colorPicker.setGreen(165);
                colorPicker.setBlue(0);
            }
        });

        Orange.setAccelerator(KeyStroke.getKeyStroke('2', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem Yellow = new JMenuItem(new AbstractAction("Yellow"){
            public void actionPerformed(ActionEvent e){
                colorPicker.setRed(255);
                colorPicker.setGreen(255);
                colorPicker.setBlue(0);
            }
        });

        Yellow.setAccelerator(KeyStroke.getKeyStroke('3', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem Purple = new JMenuItem(new AbstractAction("Purple"){
            public void actionPerformed(ActionEvent e){
                colorPicker.setRed(128);
                colorPicker.setGreen(0);
                colorPicker.setBlue(128);
            }
        });

        Purple.setAccelerator(KeyStroke.getKeyStroke('6', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem Black = new JMenuItem(new AbstractAction("Black"){
            public void actionPerformed(ActionEvent e){
                colorPicker.setRed(0);
                colorPicker.setGreen(0);
                colorPicker.setBlue(0);
            }
        });

        Black.setAccelerator(KeyStroke.getKeyStroke('7', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem White = new JMenuItem(new AbstractAction("White"){
            public void actionPerformed(ActionEvent e){
                colorPicker.setRed(255);
                colorPicker.setGreen(255);
                colorPicker.setBlue(255);
            }
        });

        White.setAccelerator(KeyStroke.getKeyStroke('8', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenu video = new JMenu("Video");
        menuBar.add(video);

        JMenuItem Record = new JMenuItem(new AbstractAction("Record Canvas"){
            public void actionPerformed(ActionEvent e){
                new Thread(){
                    public void run() {
                        main = new VideoCreator(def, zoomInfo);

                        main.setOutputFile(videoSave);

                        main.createVideoFromCanvas();
                    }
                }.start();

                new Thread(){
                    public void run() {
                        sound = new SoundCreator();

                        sound.setFile(soundSave);

                        sound.startAudio();
                    }
                }.start();
            }
        });

        JMenuItem Record1 = new JMenuItem(new AbstractAction("Record Screen"){
            public void actionPerformed(ActionEvent e){
                new Thread(){
                    public void run() {
                        main = new VideoCreator(def, zoomInfo);

                        main.setOutputFile(videoSave);

                        main.createVideoFromScreens();

                    }
                }.start();

                new Thread(){
                    public void run() {
                        sound = new SoundCreator();

                        sound.setFile(soundSave);

                        sound.startAudio();
                    }
                }.start();
            }
        });


        Record.setAccelerator(KeyStroke.getKeyStroke('L', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        JMenuItem Stop = new JMenuItem(new AbstractAction("Stop"){
            public void actionPerformed(ActionEvent e){
                main.setPleaseStop(true);

                sound.finish();

                new Thread(){
                    public void run() {
                        try{
                            Thread.sleep(1000);
                        } catch (InterruptedException e){

                        }

                        merger = new MediaMerger();
                        merger.start(videoSave, soundSave, videoSave.getAbsolutePath().substring(0, videoSave.getAbsolutePath().length() - 4) + "final.mp4");
                    }
                }.start();
            }
        });

        Stop.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));





        zoomOut.setAccelerator(KeyStroke.getKeyStroke('-', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        file.add(newF);
        file.add(openF);
        file.add(save);
        file.add(saveAs);
        file.add(saveAsVideo);
        file.add(exit);

        edit.add(drawingTablet);
        edit.add(undo);
        edit.add(redo);

        view.add(zoomIn);
        view.add(zoomOut);

        tools.add(pen);
        tools.add(pan);
        tools.add(eraser);
        tools.add(clear);
        tools.add(clear1);

        colors.add(Red);
        colors.add(Orange);
        colors.add(Yellow);
        colors.add(Green);
        colors.add(Blue);
        colors.add(Purple);
        colors.add(Black);
        colors.add(White);

        video.add(Record);
        video.add(Record1);
        video.add(Stop);

        frame.setJMenuBar(menuBar);

        toggle();
    }

    public void save () {

        try {
            ImageIO.write(def.saveImage(), "png", imageSave);
        } catch (IOException e) {

        }
    }

    public void actualSave(){
        try {
            ImageIO.write(def.saveImage(), "png", imageSave);
        } catch (IOException e){

        }
    }

    public void saveNew () {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(imageSave.getParentFile());


        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            imageSave = new File(fileChooser.getSelectedFile().toString() + ".png");
            // save to file
        }
        try {
            ImageIO.write(def.saveImage(), "png", imageSave);
        } catch (IOException e){

        }
    }

    public void saveNewVideo () {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(videoSave.getParentFile());

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            videoSave = new File(fileChooser.getSelectedFile().toString() + ".mp4");

            soundSave = new File(fileChooser.getSelectedFile().toString() + ".mp3");
            // save to file
        }
    }

    public void open () {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            imageOpen = new File(fileChooser.getSelectedFile().toString());
            // save to file
        }
        try {
            BufferedImage tempImage = ImageIO.read(imageOpen);
            def.openDetails(tempImage, false);
        } catch (IOException e){

        }
    }

    public void toggle () {
        drawingTabletBool = !drawingTabletBool;
        def.drawingTabletToggle(drawingTabletBool);
    }
}
