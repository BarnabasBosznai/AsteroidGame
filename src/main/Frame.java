package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Létrehozza az ablakot.
 * Rábízza a mneüre a játék vezérlését.
 * Frissíti a képernyőt 1/60 másodpercenként.
 */
public class Frame extends JFrame{

    private boolean closed;

    Thread thread;

    /**
     * Létrehozza az ablakot.
     * Rábízza a mneüre a játék vezérlését.
     * Ha mósodul a menü állapota, akkor új ablakot készít az új menühöz.
     * Frissíti a képernyőt 1/60 másodpercenként.
     */
    public Frame() {
        Panel panel = new Panel();

        this.add(panel);

        this.closed = false;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("AsteroidGame");
        this.setSize(800, 500);
        this.setResizable(true);
        this.setLocation(300, 300);
        this.setVisible(true);

        this.addWindowListener(new FrameClosedListener());

        thread = new Thread(() -> {
            while(!closed){
                try {
                    SwingUtilities.invokeLater(this::repaint);
                    //System.out.println("repainted");
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void StartThread(){
        this.thread.start();
    }

    private class FrameClosedListener implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            closed = true;

            try {
                thread.join();
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

