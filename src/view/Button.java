package view;

import java.awt.*;

public class Button implements Clickable {
    private final int borderX;
    private final int borderY;
    private final int borderWidthX;
    private final int borderWidthY;
    private final int innerX;
    private final int innerY;
    private final int innerWidthX;
    private final int innerWidthY;
    private String string;
    private final int stringPosX;
    private final int stringPosY;
    private final int fontSize;
    private Color backGroundColor;

    public Button(int borderX, int borderY, int borderWidthX, int borderWidthY, int innerX,
                  int innerY, int innerWidthX, int innerWidthY, String string, int stringPosX, int stringPosY, int fontSize){
        this.borderX = borderX;
        this.borderY = borderY;
        this.borderWidthX = borderWidthX;
        this.borderWidthY = borderWidthY;
        this.innerX = innerX;
        this.innerY = innerY;
        this.innerWidthX = innerWidthX;
        this.innerWidthY = innerWidthY;
        this.string = string;
        this.stringPosX = stringPosX;
        this.stringPosY = stringPosY;
        this.fontSize = fontSize;
        this.backGroundColor = Color.GRAY;
    }
    @Override
    public void Clicked(Position pos, Position cameraPos) {

    }

    @Override
    public void UnClicked() {

    }

    @Override
    public boolean ClickedCheck(Position clickPos, Position cameraPos) {
        return false;
    }

    public void SetString(String string){
        this.string = string;
    }

    public void SetBackGroundColor(Color color){
        this.backGroundColor = color;
    }

    public void Draw(Graphics2D graphics){
        graphics.setColor(this.backGroundColor);
        graphics.fillRect(borderX, borderY, borderWidthX, borderWidthY);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(innerX, innerY, innerWidthX, innerWidthY);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Dialog",Font.PLAIN,fontSize));
        graphics.drawString(string, stringPosX, stringPosY);
    }
}
