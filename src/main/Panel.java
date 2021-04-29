package main;

import view.Controller;
import view.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Panel extends JPanel {


    Position cameraPos, cameraPosSaved;
    Position lastClickPos;

    public Panel(){
        cameraPos = new Position(0,0);
        cameraPosSaved = new Position(0,0);
        lastClickPos = new Position(0,0);
        this.addMouseListener(new MouseClickedListener());
        this.addMouseMotionListener(new MouseMovedListener());
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        Graphics2D graphics = (Graphics2D) g;

        Controller.getInstance().DrawAll(graphics);
    }

    //TODO
    private class MouseClickedListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            lastClickPos = new Position(e.getX(),e.getY());
            System.out.println("LastClicked: "+lastClickPos.x+" "+lastClickPos.y);
            Controller.getInstance().ClickHandler(lastClickPos, cameraPos);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("ReleasedSaved: "+cameraPosSaved.x+" "+cameraPosSaved.y);
            System.out.println("Released: "+cameraPos.x+" "+cameraPos.y);
            cameraPosSaved = cameraPos;
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    //TODO
    private class MouseMovedListener implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {
            boolean nem_interface=true;
            if (nem_interface){
                cameraPos.x = cameraPosSaved.x + e.getX() - lastClickPos.x;
                cameraPos.y = cameraPosSaved.y + e.getY() - lastClickPos.y; // ez valószínűleg negálni kell
                System.out.println("KameraPos: "+cameraPos.x+" "+cameraPos.y);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}