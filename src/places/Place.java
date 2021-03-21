package places;

import characters.Character;

/**
 * A Place osztály egy olyan absztrakt osztály amely a játékban olyan dolgokat reprezentál amelyre rá lehet repülni.
 */
public abstract class Place {
    /**
     * @param character
     * @return
     */
    public abstract boolean Move(Character character);
}
