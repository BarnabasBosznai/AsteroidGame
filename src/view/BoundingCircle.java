package view;

public class BoundingCircle {
    Position pos;
    double radius;

    public BoundingCircle(Position p, double r){
        this.pos = p;
        this.radius = r;
    }

    public double LengthBetweenClickAndCircle(Position clickPos){
        double length = Math.sqrt((clickPos.x - pos.x) * (clickPos.x - pos.x) + (clickPos.y - pos.y) * (clickPos.y - pos.y));

        return length - radius;
    }
}
