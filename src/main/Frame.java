package main;

import javax.swing.*;

/**
 * Létrehozza az ablakot.
 * Rábízza a mneüre a játék vezérlését.
 * Frissíti a képernyőt 1/60 másodpercenként.
 */
public class UnblockMeFrame{

    /**
     * Létrehozza az ablakot.
     * Rábízza a mneüre a játék vezérlését.
     * Ha mósodul a menü állapota, akkor új ablakot készít az új menühöz.
     * Frissíti a képernyőt 1/60 másodpercenként.
     */
    public UnblockMeFrame() {
        Panel menu = new Panel();
        int current_menu = -1;
        JFrame f = new JFrame("AsteroidGame");
        while (true) {
            if (current_menu != menu.getState()) {
                f.dispose();
                f = new JFrame("AsteroidGame");
                menu.draw_Menu(f);
                if (f.getTitle().equals("AsteroidGame"))
                    System.exit(0);
                current_menu = menu.getState();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            }
            f.repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                System.err.println("Ütközés történt!");
                System.exit(-3);
            }
        }
    }
}

