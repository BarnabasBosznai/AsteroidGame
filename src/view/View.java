package view;

import java.util.*;

public class View {
    private final List<Drawable> drawables;

    public View(){
        this.drawables = new ArrayList<>();
    }

    public void AddDrawable(Drawable drawable){
        this.drawables.add(drawable);
        this.drawables.sort(Comparator.comparingInt(Drawable::GetZIndex));
    }

    public void DrawAll(){
        for(Drawable drawable : drawables){
            drawable.Draw();
        }
    }
}
