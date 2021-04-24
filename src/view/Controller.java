package view;

import characters.Settler;
import main.Game;
import main.GameState;
import places.Asteroid;

import java.util.*;

public class Controller {
    private static Controller instance;

    /**
     * Amiket onmagukban ki lehet rajzolni
     */
    private final List<Drawable> drawables;

    private final InterfacePanel interfacePanel;

    /**
     * asteroid-asteroidview parositasok kereshetoseg miatt
     */
    private final Map<Asteroid, AsteroidView> asteroidViewMap;

    private Settler currentSettlerWaitingForInput;

    private Controller(){
        this.drawables = new ArrayList<>();
        this.asteroidViewMap = new HashMap<>();
        this.currentSettlerWaitingForInput = null;

        this.interfacePanel = new InterfacePanel();
        this.drawables.add(interfacePanel);

        //elinditjuk a jatekot azonnal
        Game.getInstance().Start();
    }

    public static Controller getInstance() {
        if(instance == null)
            instance = new Controller();

        return instance;
    }

    /**
     * Mouseclick re hivodik meg
     * @param clickPos
     */
    private void ClickHandler(Position clickPos){

        List<Clickable> allClickables = new ArrayList<>();

        for(AsteroidView av: asteroidViewMap.values()){
            allClickables.addAll(av.GetClickables());
        }

        //mindenesetre ne maradjon semmi se clickelve
        for(Clickable clickable : allClickables)
            clickable.UnClicked();

        //eloszor megnezi, hogy az interface en tortent e a kattintas
        if(!this.interfacePanel.HandleClick(clickPos, currentSettlerWaitingForInput)) {
            this.currentSettlerWaitingForInput = null;
            return;
        }

        //ha nem lehetett interface t lekezelni, megnezi a tobbit is

        //meg azt is le lehet majd kezelni, hogy bizonyos tavolsag felett ne vegye ugy, hogy ranyomtak vkire
        double minLength = Double.MAX_VALUE;
        Clickable closestClickable = null;

        for(Clickable clickable : allClickables){
            double currentLength = clickable.GetBoundingCircle().LengthBetweenClickAndCircle(clickPos);

            if(currentLength < minLength){
                minLength = currentLength;
                closestClickable = clickable;
            }
        }

        //koordinatarendszerek+origo eltolas meg nincs lekezelve, majd az eltoltat adnam oda
        if(closestClickable != null) {
            closestClickable.Clicked(clickPos);
        }
    }

    /**
     *  Idokozonkent hivodik meg
     */
    private void TimeHandler(){
        if(currentSettlerWaitingForInput == null){
            Game.getInstance().NextStep();
        }
    }

    public void CurrentSettlerWaitingForInput(Settler settler){
        this.currentSettlerWaitingForInput = settler;
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
        AsteroidView av = asteroidViewMap.get(dc.GetAsteroid());
        av.AddDrawableCharacter(dc);

        this.DrawAll();
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

        this.DrawAll();

        return av;
    }

    /**
     * listahoz hozzaad, ezt hivja meg a teleportgate siman, asteroid pluszban meg
     * @param d
     */
    public void AddDrawable(Drawable d){
        this.drawables.add(d);
        this.drawables.sort(Comparator.comparingInt(Drawable::GetZIndex));

        this.DrawAll();
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
        AsteroidView av = this.asteroidViewMap.get(dc.GetAsteroid());

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

    public void GameEnded(GameState gameState){
        if(gameState == GameState.SETTLERSLOST){
            //KEKW
        }
        else if(gameState == GameState.SETTLERSWON){
            //woozyface
        }
    }
}
