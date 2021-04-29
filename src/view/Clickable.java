package view;

public interface Clickable {
    void Clicked(Position pos);
    void UnClicked();
    boolean ClickedCheck(Position clickPos, Position cameraPos);


}
