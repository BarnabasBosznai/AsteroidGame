package view;

import characters.Settler;
import main.Game;
import main.GameState;
import places.Asteroid;

import java.awt.*;
import java.util.*;
import java.util.List;

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

    private Random rand = new Random();

    private Controller(){
        this.drawables = new ArrayList<>();
        this.asteroidViewMap = new HashMap<>();
        this.currentSettlerWaitingForInput = null;

        this.interfacePanel = new InterfacePanel();
        this.drawables.add(interfacePanel);
    }

    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    /**
     * Mouseclick re hivodik meg
     * @param clickPos
     */
    public boolean ClickHandler(Position clickPos, Position cameraPos){

        //eloszor megnezi, hogy az interface en tortent e a kattintas
        if(this.interfacePanel.HandleClick(clickPos, currentSettlerWaitingForInput)) {
            return true;
        }

        ArrayList<AsteroidView> allClickables = new ArrayList<>();

        for(AsteroidView av: asteroidViewMap.values()){
            allClickables.add(av);
        }

        //mindenesetre ne maradjon semmi se clickelve
        for(Clickable clickable : allClickables)
            clickable.UnClicked();

        //ha nem lehetett interface t lekezelni, megnezi a tobbit is

        //meg azt is le lehet majd kezelni, hogy bizonyos tavolsag felett ne vegye ugy, hogy ranyomtak vkire
        double minLength = Double.MAX_VALUE;
        Clickable closestClickable = null;

        for(Clickable clickable : allClickables){
            if(clickable.ClickedCheck(clickPos, cameraPos)) {
                closestClickable = clickable;
            }
        }

        //koordinatarendszerek+origo eltolas meg nincs lekezelve, majd az eltoltat adnam oda
        if(closestClickable != null) {
            closestClickable.Clicked(clickPos);
            return true;
        }
        else
            return false;

    }

    public void StepEnded(){
        if(this.currentSettlerWaitingForInput == null)
            this.CallNextStep();
    }

    private void CallNextStep(){
        Game.getInstance().NextStep();
    }

    public void CurrentSettlerWaitingForInput(Settler settler){
        this.currentSettlerWaitingForInput = settler;
    }

    public void SettlerStepped(){
        this.currentSettlerWaitingForInput = null;
        this.CallNextStep();
    }

    public Settler GetCurrentSettlerWaitingForInput(){
        return this.currentSettlerWaitingForInput;
    }

    /**
     * kirajzol mindent is
     */
    public void DrawAll(Graphics2D g, Position cameraPos){
        for(Drawable drawable : drawables){
            drawable.Draw(g, cameraPos);
        }
    }

    /**
     * Megfelelo asteroidView hoz hozzaadja a drawableCharactert
     * @param dc
     */
    public void AddDrawableCharacter(DrawableCharacter dc){
        AsteroidView av = asteroidViewMap.get(dc.GetAsteroid());
        av.AddDrawableCharacter(dc);
    }

    /**
     * View hoz hozzaad egy asteroidview t, itt tortenik init is a koordinatak miatt
     * @param asteroid
     */
    public AsteroidView AddAsteroidView(Asteroid asteroid){
        Position pos = new Position(rand.nextInt(1000), rand.nextInt(1000)); //ide majd vmi okos
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
    }

    /**
     * DrawableCharacter jelzett, hogy modellben meghalt a karakter, itt is alkalmazkodik
     * @param dc
     */
    public void CharacterDied(DrawableCharacter dc){
        AsteroidView av = this.asteroidViewMap.get(dc.GetAsteroid());

        av.RemoveDrawableCharacter(dc);
    }

    /**
     * TeleportGateView jelzett, h modellben felrobbant teleportkapu, toroljuk viewbol
     * @param tv
     */
    public void TeleportGateDestroyed(TeleportGateView tv){
        this.drawables.remove(tv);
    }

    /**
     * Asteroida felrobbant, nem kene tovabb kirajzolni (karakterek magukat elintezik)
     * @param av
     */
    public void AsteroidExploded(AsteroidView av){
        this.drawables.remove(av);
        this.asteroidViewMap.values().remove(av);
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
