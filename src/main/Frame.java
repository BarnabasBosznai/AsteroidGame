package main;

import javax.swing.*;

/**
 * Létrehozza az ablakot.
 * Rábízza a mneüre a játék vezérlését.
 * Frissíti a képernyőt 1/60 másodpercenként.
 */
public class Frame extends JFrame{

    Panel panel;

    /**
     * Létrehozza az ablakot.
     * Rábízza a mneüre a játék vezérlését.
     * Ha mósodul a menü állapota, akkor új ablakot készít az új menühöz.
     * Frissíti a képernyőt 1/60 másodpercenként.
     */
    public Frame() {
        Panel panel = new Panel();

        this.add(panel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("AsteroidGame");
        this.setSize(800, 800);
        this.setResizable(false);
        this.setLocation(300, 300);

        while (true) {

            this.repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                System.err.println("Ütközés történt!");
                System.exit(-3);
            }
        }
    }
}

