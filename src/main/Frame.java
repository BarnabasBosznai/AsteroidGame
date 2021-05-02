package main;

import view.Controller;
import view.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Létrehozza az ablakot.
 * Rábízza a mneüre a játék vezérlését.
 * Frissíti a képernyőt 1/60 másodpercenként.
 */
public class Frame extends JFrame{

    private boolean closed;

    Thread threadGui;
    Thread threadStep;

    BufferedImage img;

    /**
     * Létrehozza az ablakot.
     * Rábízza a mneüre a játék vezérlését.
     * Ha mósodul a menü állapota, akkor új ablakot készít az új menühöz.
     * Frissíti a képernyőt 1/60 másodpercenként.
     */
    public Frame() {
        Panel panel = new Panel();

        this.add(panel);

        try {
            img = ImageIO.read(new File("icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setIconImage(img);

        this.closed = false;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("AsteroidGame");
        //this.setSize(800, 500); // eltolódott az egész, az interface-ben több lett volna a mágikus szám
        this.getContentPane().setPreferredSize(new Dimension(1000,563)); // "Így nagyobb"
        this.pack();
        this.setResizable(true);
        this.setLocation(300, 150);
        this.setVisible(true);

        this.addWindowListener(new FrameClosedListener());

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension dim = e.getComponent().getSize();
                panel.WindowResized(new Position(dim.width-16,dim.height-39));
                Controller.getInstance().FrameResized(new Position(dim.width-16,dim.height-39)); // mágikus számok
            }
        });

        threadGui = new Thread(() -> {
            while(!closed){
                try {
                    SwingUtilities.invokeLater(this::repaint);

                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadStep = new Thread(() -> {
            while(!closed){
                try {
                    Controller.getInstance().TimerTicked();

                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void StartGame(){
        this.threadGui.start();
        this.threadStep.start();
    }

    private class FrameClosedListener implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            closed = true;

            try {
                threadGui.join();
                threadStep.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }
}

