package view;

public interface Clickable {
    void Clicked(Position pos);
    void UnClicked();
    BoundingCircle GetBoundingCircle();
    boolean ClickedCheck(Position clickPos, Position cameraPos);


}
