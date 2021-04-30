package interfaces;

/**
 * Ez egy interface. Az őt megvalósító osztályokat lépteti a Game.
 */
public interface Steppable {

    /**
     * Az adott körben végzendő tevékenységek kiválasztása és elvégzése.
     */
    void Step();
    int GetSteppablePriority();
}
