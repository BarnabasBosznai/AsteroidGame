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

        Controller.getInstance().DrawAll(graphics, cameraPos);
    }

    //TODO
    private class MouseClickedListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //System.out.println("cameraPosSaved: "+cameraPosSaved.x+" "+cameraPosSaved.y);


            //System.out.println("LastClicked: "+lastClickPos.x+" "+lastClickPos.y);

        }

        @Override
        public void mousePressed(MouseEvent e) {
            lastClickPos.x = e.getX();
            lastClickPos.y = e.getY();
            //System.out.println("KameraPos: "+cameraPos.x+" "+cameraPos.y);
            //System.out.println("l: "+e.getX()+" "+e.getY());
            Controller.getInstance().ClickHandler(lastClickPos, cameraPos);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            cameraPosSaved.x = cameraPos.x;
            cameraPosSaved.y = cameraPos.y;
            //System.out.println("cameraPosSaved: "+cameraPosSaved.x+" "+cameraPosSaved.y);
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
                cameraPos.x = cameraPosSaved.x - e.getX() + lastClickPos.x;
                cameraPos.y = cameraPosSaved.y - e.getY() + lastClickPos.y; // ez valószínűleg negálni kell
                if (cameraPos.x<0) cameraPos.x=0;
                if (cameraPos.x>10000) cameraPos.x=10000;
                if (cameraPos.y<0) cameraPos.y=0;
                if (cameraPos.y>5630) cameraPos.y=5630;
                //System.out.println("KameraPos: "+cameraPos.x+" "+cameraPosSaved.x+" "+e.getX()+" "+lastClickPos.x);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}