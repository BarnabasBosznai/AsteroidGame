package view;

import characters.Character;
import characters.Robot;
import characters.Settler;
import characters.UFO;
import main.Game;
import main.GameState;
import places.Asteroid;
import places.TeleportGate;

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
    private final Map<Settler, SettlerView> settlerViewMap;
    private Clickable currentClickedAsteroid;
    private AsteroidView lastClickedAsteroid;

    private Settler currentSettlerWaitingForInput;

    private boolean canCallNextStep = false;

    private Controller(){
        this.drawables = new ArrayList<>();
        this.asteroidViewMap = new HashMap<>();
        this.settlerViewMap = new HashMap<>();
        this.currentSettlerWaitingForInput = null;
        this.lastClickedAsteroid = null;
        this.interfacePanel = new InterfacePanel();
        this.drawables.add(interfacePanel);
    }

    public static Controller getInstance() {
        if (instance == null) {
            synchronized (Controller.class) {
                if (instance == null) {
                    instance = new Controller();
                }
            }

        }

        return instance;
    }

    public static void StartGame(){
        Game.getInstance().Start();
    }

    /**
     * Mouseclick re hivodik meg
     * @param clickPos
     */
    public boolean ClickHandler(Position clickPos, Position cameraPos){

        //eloszor megnezi, hogy az interface en tortent e a kattintas
        if(!this.interfacePanel.HandleClick(clickPos)) {
            return true;
        }
        //ha nem lehetett interface t lekezelni, megnezi a tobbit is
        // megnézi az esetleg már kattintott aszteroidát
        if (currentClickedAsteroid!=null && currentClickedAsteroid.ClickedCheck(clickPos,cameraPos)){
            currentClickedAsteroid.Clicked(clickPos, cameraPos);
            return true;
        }

        // ha ez sem, megnézi mindet

        ArrayList<AsteroidView> allClickables = new ArrayList<>(asteroidViewMap.values());

        //mindenesetre ne maradjon semmi se clickelve
        for(Clickable clickable : allClickables)
            clickable.UnClicked();

        currentClickedAsteroid=null;
        for(Clickable clickable : allClickables){
            if(clickable.ClickedCheck(clickPos, cameraPos)) {
                currentClickedAsteroid = clickable;
                lastClickedAsteroid = (AsteroidView) clickable;
            }
        }

        //koordinatarendszerek+origo eltolas meg nincs lekezelve, majd az eltoltat adnam oda
        if(currentClickedAsteroid != null) {
            currentClickedAsteroid.Clicked(clickPos, cameraPos);
            return true;
        }
        else
            return false;

    }

    public void TimerTicked(){
        if(canCallNextStep)
            Game.getInstance().NextStep();

    }

    public void StepEnded(){
        this.canCallNextStep = this.currentSettlerWaitingForInput == null;
    }

    public void CurrentSettlerWaitingForInput(Settler settler){
        this.currentSettlerWaitingForInput = settler;
        interfacePanel.SetCurrentWaitingSettler(currentSettlerWaitingForInput);
    }

    public void SettlerStepped(){
        this.currentSettlerWaitingForInput = null;
        this.canCallNextStep = true;
    }

    public Settler GetCurrentSettlerWaitingForInput(){
        return this.currentSettlerWaitingForInput;
    }

    /**
     * kirajzol mindent is
     */
    public void DrawAll(Graphics2D g, Position cameraPos, Position cursorPos){
        synchronized (drawables) {
            /* (AsteroidView astview : asteroidViewMap.values()) {
                astview.Draw_Neighbours_and_Teleports(g, cameraPos);
            }*/
            if(currentSettlerWaitingForInput != null)
                asteroidViewMap.get(currentSettlerWaitingForInput.GetAsteroid()).Draw_Neighbours_and_Teleports(g, cameraPos, Color.WHITE);
            asteroidViewMap.forEach((asteroid, asteroidView) ->  {
                if(Math.sqrt((asteroidView.GetPos().x + 30 - cameraPos.x - cursorPos.x) * (asteroidView.GetPos().x + 30 - cameraPos.x - cursorPos.x) +
                        (asteroidView.GetPos().y + 30 - cameraPos.y - cursorPos.y) * (asteroidView.GetPos().y + 30 - cameraPos.y  - cursorPos.y)) < AsteroidView.asteroidRadius)
                    asteroidView.Draw_Neighbours_and_Teleports(g, cameraPos, Color.CYAN);
            });
            if(lastClickedAsteroid != null)
                lastClickedAsteroid.Draw_Neighbours_and_Teleports(g, cameraPos, Color.CYAN);

            for (Drawable drawable : drawables) {
                drawable.Draw(g, cameraPos);
            }
        }

        g.setColor(Color.GRAY); // Tesztelésre csak talán
        g.setFont(new Font("Dialog",Font.PLAIN,14));
        g.drawString("X: "+cameraPos.x,0,12);
        g.drawString("Y: "+cameraPos.y,0,28);
    }

    private void AddDrawableCharacter(DrawableCharacter dc){
        AsteroidView av = asteroidViewMap.get(dc.GetAsteroid());
        av.AddDrawableCharacter(dc);
    }
    /**
     * Megfelelo asteroidView hoz hozzaadja a drawableCharactert
     * @param ufo
     */
    public UFOView AddUFOView(UFO ufo){
        UFOView uv = new UFOView(ufo);
        this.AddDrawableCharacter(uv);

        return uv;
    }

    public RobotView AddRobotView(Robot robot){
        RobotView rv = new RobotView(robot);
        this.AddDrawableCharacter(rv);

        return rv;
    }

    public SettlerView AddSettlerView(Settler settler){
        SettlerView sv = new SettlerView(settler);
        this.AddDrawableCharacter(sv);
        this.settlerViewMap.put(settler, sv);

        return sv;
    }

    /**
     * View hoz hozzaad egy asteroidview t, itt tortenik init is a koordinatak miatt
     * @param asteroid
     */
    public void AddAsteroidView(Asteroid asteroid, Position position){
        AsteroidView av = new AsteroidView(asteroid, position, 2);
        asteroid.setView(av);

        //System.out.println("AddAsteroidView called with: " + asteroid.toString() + " - " + av.toString());

        this.AddDrawable(av);
        var x = this.asteroidViewMap.put(av.GetAsteroid(), av);
        //asteroidViewMap.forEach((key, value) -> System.out.println(key + ":" + value));
        //System.out.println();

        //System.out.println("\tPut in map.");

    }

    public void AddTeleportGateView(TeleportGate teleportGate1, TeleportGate teleportGate2){
        TeleportGateView tv = new TeleportGateView(teleportGate1, teleportGate2, 1);
        this.AddDrawable(tv);
    }

    public AsteroidView GetAsteroidView(Asteroid asteroid){
        return this.asteroidViewMap.get(asteroid);
    }

    public SettlerView GetSettlerView(Settler settler){
        return this.settlerViewMap.get(settler);
    }

    /**
     * listahoz hozzaad, ezt hivja meg a teleportgate siman, asteroid pluszban meg
     * @param d
     */
    public void AddDrawable(Drawable d){
        synchronized (drawables) {
            this.drawables.add(d);
            this.drawables.sort(Comparator.comparingInt(Drawable::GetZIndex));
        }
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

    public void SettlerDied(SettlerView sv){
        this.CharacterDied(sv);

        this.settlerViewMap.values().remove(sv);
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
