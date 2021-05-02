package view;

import java.awt.*;

public class Button implements Clickable {
    private String string;
    private final int fontSize;
    private Color backGroundColor;

    private Rectangle border;
    private Rectangle inner;
    private Font font;
    private boolean state;
    private Runnable callback;

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
        graphics.fillRect(border.x, border.y, border.width, border.height);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(inner.x, inner.y, inner.width, inner.height);
        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        drawCenteredText(graphics);
    }
}
