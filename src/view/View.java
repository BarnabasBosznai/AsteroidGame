package view;

import characters.Character;
import places.Asteroid;

import java.util.*;

public class View {
    private static View instance;

    /**
     * Amiket onmagukban ki lehet rajzolni
     */
    private final List<Drawable> drawables;

    /**
     * asteroid-asteroidview parositasok kereshetoseg miatt
     */
    private final Map<Asteroid, AsteroidView> asteroidViewMap;

    private View(){
        this.drawables = new ArrayList<>();
        this.asteroidViewMap = new HashMap<>();
    }

    public static View getInstance() {
        if(instance == null)
            instance = new View();

        return instance;
    }

    /**
     * kirajzol mindent is
     */
    public void DrawAll(){
        for(Drawable drawable : drawables){
            drawable.Draw();
        }
    }

    /**
     * Megfelelo asteroidView hoz hozzaadja a drawableCharactert
     * @param dc
     */
    public void AddDrawableCharacter(DrawableCharacter dc){
        AsteroidView av = asteroidViewMap.get(dc.GetCharacter().GetAsteroid());
        av.AddDrawableCharacter(dc);
    }

    /**
     * View hoz hozzaad egy asteroidview t, itt tortenik init is a koordinatak miatt
     * @param asteroid
     */
    public AsteroidView AddAsteroidView(Asteroid asteroid){
        Position pos = new Position(0,0); //ide majd vmi okos
        AsteroidView av = new AsteroidView(asteroid, pos, 2);

        this.AddDrawable(av);
        this.asteroidViewMap.put(av.GetAsteroid(), av);

        return av;
    }

    /**
     * listahoz hozzaad, ezt hivja meg a teleportgate siman, asteroid pluszban meg
     * @param d
     */
    public void AddDrawable(Drawable d){
        this.drawables.add(d);
        this.drawables.sort(Comparator.comparingInt(Drawable::GetZIndex));
    }

    /**
     * DrawableCharacter jelzett, h a modellben elmozgott a karakter, itt is valtoztatjuk
     * @param dc
     * @param oldAsteroid
     * @param newAsteroid
     */
    public void CharacterMoved(DrawableCharacter dc, Asteroid oldAsteroid, Asteroid newAsteroid){
        AsteroidView av1 = this.asteroidViewMap.get(oldAsteroid);
        AsteroidView av2 = this.asteroidViewMap.get(newAsteroid);

        av1.RemoveDrawableCharacter(dc);
        av2.AddDrawableCharacter(dc);

        this.DrawAll();
    }

    /**
     * DrawableCharacter jelzett, hogy modellben meghalt a karakter, itt is alkalmazkodik
     * @param dc
     */
    public void CharacterDied(DrawableCharacter dc){
        AsteroidView av = this.asteroidViewMap.get(dc.GetCharacter().GetAsteroid());

        av.RemoveDrawableCharacter(dc);

        this.DrawAll();
    }

    /**
     * TeleportGateView jelzett, h modellben felrobbant teleportkapu, toroljuk viewbol
     * @param tv
     */
    public void TeleportGateDestroyed(TeleportGateView tv){
        this.drawables.remove(tv);

        this.DrawAll();
    }

    /**
     * Asteroida felrobbant, nem kene tovabb kirajzolni (karakterek magukat elintezik)
     * @param av
     */
    public void AsteroidExploded(AsteroidView av){
        this.drawables.remove(av);
        this.asteroidViewMap.values().remove(av);

        this.DrawAll();
    }

    /*Hianyzik Controller resz, de swing nelkul nehez meg,
      de elvileg megoldott oda-vissza kommunikalas
     */
}
