package view;

import java.awt.*;

public class PositionView extends Drawable{

    public PositionView(){
        this.zIndex = 100;
    }

    @Override
    public void Draw(Graphics2D graphics, Position cameraPos) {
        graphics.setColor(Color.GRAY); // Tesztelésre csak talán
        graphics.setFont(new Font("Dialog",Font.PLAIN,14));
        graphics.drawString("X: "+cameraPos.x,0,12);
        graphics.drawString("Y: "+cameraPos.y,0,28);
    }
}
