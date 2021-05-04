package view;

import java.awt.*;
import java.util.*;

public class EventFeed extends Drawable {
    private final Queue<EventBox> eventQueue;
    private final int animationTimeMax;

    public EventFeed(){
        this.eventQueue = new PriorityQueue<>(25, Comparator.comparingInt(EventBox::GetTimeLeft));
        this.animationTimeMax = 200;
        this.zIndex = 1000;
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
                eb.SetPosition(new Position(780, 10 + ct * 35));
                ++ct;
                eb.Draw(graphics, cameraPos);
                eb.DecreaseTimeLeft();
            }
        }
    }

    public class EventBox extends Drawable{

        private final String string;
        private final Font font;
        private Position pos;
        private final Dimension eventBoxDimensions;
        private int timeLeft;

        public EventBox(String string, int animationTimeMax){
            this.string = string;
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
            DrawCenteredText(graphics);
        }

        private void DrawCenteredText(Graphics2D g){
            FontMetrics metrics = g.getFontMetrics(this.font);
            int x = this.pos.x + (eventBoxDimensions.width - metrics.stringWidth(this.string)) / 2;
            int y = this.pos.y + eventBoxDimensions.height - (eventBoxDimensions.height - (-(int) metrics.getLineMetrics(this.string, g).getBaselineOffsets()[2])) / 2 - 3;
            g.drawString(this.string, x, y);
        }
    }
}