package view;

import java.awt.*;

public class Button implements Clickable {
    /*private final int borderX;
    private final int borderY;
    private final int borderWidthX;
    private final int borderWidthY;
    private final int innerX;
    private final int innerY;
    private final int innerWidthX;
    private final int innerWidthY;
    private final int stringPosX;
    private final int stringPosY;*/
    private String string;
    private final int fontSize;
    private Color backGroundColor;

    private Rectangle border;
    private Rectangle inner;
    private Font font;
    private boolean state;
    private Runnable callback;

    /*public Button(int borderX, int borderY, int borderWidthX, int borderWidthY, int innerX,
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
        this.state = false;
        this.font = new Font("Dialog",Font.PLAIN,fontSize);
    }*/

    public Button(Rectangle border, Rectangle inner, String string, int fontSize){
        this.border = border;
        this.inner = inner;
        this.string = string;
        this.fontSize = fontSize;
        this.backGroundColor = Color.GRAY;
        this.state = false;
        this.font = new Font("Dialog",Font.PLAIN,fontSize);
    }

    @Override
    public void Clicked(Position pos, Position cameraPos) {
        this.state = true;
        if(callback != null)
            callback.run();
    }

    @Override
    public void UnClicked() {
        this.state = false;
    }

    @Override
    public boolean ClickedCheck(Position clickPos, Position cameraPos) {
        //return true;
        return border.contains(clickPos.x, clickPos.y);
    }

    public void SetString(String string){
        this.string = string;
    }

    public void SetBackGroundColor(Color color){
        this.backGroundColor = color;
    }

    public boolean getState() {
        return state;
    }

    public void setCallback(Runnable runnable) {
        this.callback = runnable;
    }

    private void drawCenteredText(Graphics2D g) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = border.x + (border.width - metrics.stringWidth(string)) / 2;
        int y = border.y + ((border.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(string, x, y);
    }

    public void Draw(Graphics2D graphics){
        graphics.setColor(this.backGroundColor);
        //graphics.fillRect(borderX, borderY, borderWidthX, borderWidthY);
        graphics.fillRect(border.x, border.y, border.width, border.height);

        graphics.setColor(Color.LIGHT_GRAY);
        //graphics.fillRect(innerX, innerY, innerWidthX, innerWidthY);
        graphics.fillRect(inner.x, inner.y, inner.width, inner.height);

        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        drawCenteredText(graphics);
        //graphics.drawString(string, stringPosX, stringPosY);
    }
}
