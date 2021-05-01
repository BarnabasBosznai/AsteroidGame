package view;

public interface Clickable {
    void Clicked(Position pos, Position cameraPos);
    void UnClicked();
    boolean ClickedCheck(Position clickPos, Position cameraPos);


}
