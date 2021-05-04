package view;

import main.Game;
import main.GameState;

import java.awt.*;

public class EndGameAnimation extends Drawable {

    private GameState endGameState;
    private Color fontColor;
    private String string;
    private Font font;

    public EndGameAnimation(){
        this.zIndex = 101;
        this.endGameState = Game.getInstance().GetGameState();
        if(endGameState == GameState.SETTLERSWON) {
            this.fontColor = Color.GREEN;
            this.string = "Nyertek a telepesek!";
            this.font = new Font("Dialog", Font.ITALIC, 80);
        }
        else if(endGameState == GameState.SETTLERSLOST){
            this.fontColor = Color.RED;
            this.string = "Vesztettek a telepesek :(";
            this.font = new Font("Dialog", Font.ITALIC, 80);
        }
    }

    private void DrawCenteredText(Graphics2D g){
        FontMetrics metrics = g.getFontMetrics(this.font);
        Position winSize = Controller.getInstance().GetWindowSize();
        int x = (winSize.x - metrics.stringWidth(this.string)) / 2;
        int y = winSize.y - (winSize.y - (-(int) metrics.getLineMetrics(this.string, g).getBaselineOffsets()[2])) / 2;
        g.drawString(this.string, x, y);
    }

    @Override
    public void Draw(Graphics2D g, Position cameraPos) {
        g.setFont(this.font);
        g.setColor(this.fontColor);
        this.DrawCenteredText(g);
    }
}
