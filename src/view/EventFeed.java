package view;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class EventFeed extends Drawable {
    private final Queue<EventBox> eventQueue;
    private final int animationTimeMax;
    private final Dimension eventBoxDimension;

    public EventFeed(){
        this.eventQueue = new PriorityQueue<>(25, Comparator.comparingInt(EventBox::GetTimeLeft));
        this.animationTimeMax = 200;
        this.zIndex = 1000;
        this.eventBoxDimension = new Dimension(200,25);
    }

    public void EventHappened(String eventDescription, int animationTimeMax){
        if(eventQueue.size() == 4)
            eventQueue.poll();
        this.eventQueue.add(new EventBox(eventDescription, animationTimeMax));
    }

    @Override
    public void Draw(Graphics2D graphics, Position cameraPos) {
        if(eventQueue.size() > 0){
            boolean finished = false;
            while(!finished && eventQueue.size() > 0){
                if(eventQueue.peek().GetTimeLeft() <= 0) {
                    eventQueue.poll();
                }
                else
                    finished = true;
            }

            int ct = 0;
            for(EventBox eb : eventQueue){
                Position winSize = Controller.getInstance().GetWindowSize();
                eb.SetPosition(new Position(winSize.x - eventBoxDimension.width - 20, 10 + ct * (eventBoxDimension.height + 10)));
                ++ct;
                eb.Draw(graphics, cameraPos);
                eb.DecreaseTimeLeft();
            }
        }
    }

    private class EventBox extends Drawable{

        private final String text;
        private Font font;
        private Position pos;
        private final Dimension eventBoxDimensions;
        private int timeLeft;

        public EventBox(String string, int animationTimeMax){
            this.text = string;
            this.eventBoxDimensions = new Dimension(200, 25);

            this.font = new Font("Dialog", Font.PLAIN, 20);

            this.timeLeft = animationTimeMax;
        }

        public void SetPosition(Position pos){
            this.pos = pos;
        }

        public void DecreaseTimeLeft(){
            --timeLeft;
        }

        public int GetTimeLeft(){
            return this.timeLeft;
        }

        @Override
        public void Draw(Graphics2D graphics, Position cameraPos) {
            graphics.setColor(Color.GRAY);
            graphics.fillRect(pos.x, pos.y, eventBoxDimensions.width, eventBoxDimensions.height);
            graphics.setColor(Color.WHITE);
            graphics.setFont(font);
            Font font = Font.decode("Dialog");
            Rectangle2D r2d = graphics.getFontMetrics(font).getStringBounds(this.text, graphics);
            this.font = font.deriveFont((float) (font.getSize2D() * (eventBoxDimensions.width - 30) / r2d.getWidth()));
            DrawCenteredText(graphics);
        }

        private void DrawCenteredText(Graphics2D g){
            FontMetrics metrics = g.getFontMetrics(this.font);
            int x = this.pos.x + (eventBoxDimensions.width - metrics.stringWidth(this.text)) / 2;
            int y = this.pos.y + eventBoxDimensions.height - (eventBoxDimensions.height - (-(int) metrics.getLineMetrics(this.text, g).getBaselineOffsets()[2])) / 2 - 3;
            g.drawString(this.text, x, y);
        }
    }
}