package main;

import view.Controller;
import view.Position;

import javax.sound.sampled.Control;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Panel extends JPanel {


    Position cameraPos, cameraPosSaved;
    Position lastClickPos;
    boolean nem_interface=true;

    public Panel(){
        cameraPos = new Position(0,0);
        cameraPosSaved = new Position(0,0);
        lastClickPos = new Position(0,0);
        this.addMouseListener(new MouseClickedListener());
        this.addMouseMotionListener(new MouseMovedListener());
        this.addKeyListener(new KeyListenerHM());
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        Graphics2D graphics = (Graphics2D) g;

        Controller.getInstance().DrawAll(graphics, cameraPos);
    }


    private class KeyListenerHM implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_SPACE) {

                if (Controller.getInstance().GetCurrentSettlerWaitingForInput()!=null) {
                    Position pos = Controller.getInstance().GetAsteroidView(Controller.getInstance().GetSettlerView(Controller.getInstance().GetCurrentSettlerWaitingForInput()).GetAsteroid()).GetPos();

                    cameraPos.x = pos.x - 500;
                    cameraPos.y = pos.y - 281;
                    cameraPosSaved.x = pos.x - 500;
                    cameraPosSaved.y = pos.y - 281;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
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
            nem_interface = Controller.getInstance().ClickHandler(lastClickPos, cameraPos);
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

            if (nem_interface){
                cameraPos.x = cameraPosSaved.x - e.getX() + lastClickPos.x;
                cameraPos.y = cameraPosSaved.y - e.getY() + lastClickPos.y; // ez valószínűleg negálni kell
                if (cameraPos.x<-2000) cameraPos.x=-2000;
                if (cameraPos.x>2000) cameraPos.x=2000;
                if (cameraPos.y<-2000) cameraPos.y=-2000;
                if (cameraPos.y>2000) cameraPos.y=2000;
                //System.out.println("KameraPos: "+cameraPos.x+" "+cameraPosSaved.x+" "+e.getX()+" "+lastClickPos.x);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}