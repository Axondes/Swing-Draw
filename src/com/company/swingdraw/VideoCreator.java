package com.company.swingdraw;

import java.awt.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.ICodec;

import javax.sound.sampled.*;
import javax.swing.*;

public class VideoCreator {

    private static final long FRAME_RATE = 60;
    private File outputFile;
    private AtomicBoolean pleaseStop = new AtomicBoolean(false);
    private boolean stoppedCreation = true;
    private DrawSpace def;
    private ZoomInfo zoomInfo;

    public VideoCreator(DrawSpace def, ZoomInfo zoomInfo) {
        this.def = def;
        this.zoomInfo = zoomInfo;
    }

    public VideoCreator(File outputFile) {
        this.outputFile = outputFile;
    }

    public void createVideoFromCanvas() {
        IMediaWriter writer = null;

        try {
            if (getOutputFile() == null) {
                throw new IllegalStateException(
                        "Output video file cannot be null");
            }

            setStoppedCreation(false);

            writer = ToolFactory.makeWriter(getOutputFile().getAbsolutePath());
            writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, def.getWidth(), def.getHeight());

            long startTime = System.nanoTime();

            zoomInfo.changeRecording();
            zoomInfo.changeZoom(def.getZoom());

            while (!getPleaseStop()) {
                BufferedImage screen = def.saveImage();
                BufferedImage bgrScreen = convertToType(screen, BufferedImage.TYPE_3BYTE_BGR);

                long timeStamp = System.nanoTime() - startTime;

                writer.encodeVideo(0, bgrScreen, timeStamp, TimeUnit.NANOSECONDS);

                try {
                    Thread.currentThread().sleep(1000 / FRAME_RATE);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
            setStoppedCreation(true);
        } finally {
            if(writer!=null){
                writer.flush();
                writer.close();
                zoomInfo.changeRecording();
                zoomInfo.changeZoom(def.getZoom());
            }

        }
    }

    public void createVideoFromScreens() {
        IMediaWriter writer = null;

        try {
            if (getOutputFile() == null) {
                throw new IllegalStateException(
                        "Output video file cannot be null");
            }

            setStoppedCreation(false);

            writer = ToolFactory.makeWriter(getOutputFile().getAbsolutePath());
            writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, def.getWidth(), def.getHeight());

            long startTime = System.nanoTime();

            zoomInfo.changeRecording();
            zoomInfo.changeZoom(def.getZoom());

            while (!getPleaseStop()) {
                BufferedImage screen = def.saveImage(true);
                BufferedImage bgrScreen = convertToType(screen, BufferedImage.TYPE_3BYTE_BGR);


                long timeStamp = System.nanoTime() - startTime;

                writer.encodeVideo(0, bgrScreen, timeStamp, TimeUnit.NANOSECONDS);

                try {
                    Thread.currentThread().sleep(1000 / FRAME_RATE);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
            setStoppedCreation(true);
        } finally {
            if(writer!=null){
                writer.flush();
                writer.close();
                zoomInfo.changeRecording();
                zoomInfo.changeZoom(def.getZoom());
            }

        }
    }

    private BufferedImage getDesktopScreenshot() {
        try {
            Robot robot = new Robot();
            Rectangle captureSize = new Rectangle((int)def.getLocationOnScreen().getX(), (int)def.getLocationOnScreen().getY(),(int)def.getBounds().getWidth(),(int)def.getBounds().getHeight());
            return robot.createScreenCapture(captureSize);
        } catch (AWTException e) {
            throw new RuntimeException(
                    "Error occurred while getting desktop screenshot", e);
        }
    }


    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public boolean getPleaseStop() {
        return pleaseStop.get();
    }

    public void setPleaseStop(boolean pleaseStop) {
        this.pleaseStop.set(pleaseStop);
    }

    public boolean isStoppedCreation() {
        return stoppedCreation;
    }

    private void setStoppedCreation(boolean stoppedCreation) {
        this.stoppedCreation = stoppedCreation;
    }

    public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
        BufferedImage image = null;

        if (sourceImage.getType() == targetType) {
            image = sourceImage;
        } else {
            image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }

        return image;
    }

    public static BufferedImage componentToImage(JComponent component, boolean visible) {
        BufferedImage img;
        if (visible) {
            img = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TRANSLUCENT);
            Graphics2D g2d = (Graphics2D) img.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            component.paintAll(g2d);
        } else {
            component.setSize(component.getPreferredSize());
            img = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TRANSLUCENT);
            CellRendererPane crp = new CellRendererPane();
            crp.add(component);
            crp.paintComponent(img.createGraphics(), component, crp, component.getBounds());
        }

        BufferedImage resizedImg = new BufferedImage(1920, 1080, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, 1920, 1080, null);
        g2.dispose();

        return resizedImg;
    }
}