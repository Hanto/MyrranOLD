package Vista.Model.Player;// Created by Hanto on 03/04/2014.

import com.badlogic.gdx.graphics.Color;

public interface PlayerObservador
{
    public void playerPosition(int x, int y);
    public void playerIsCasteando (boolean isCasteando);

    public void playerHPsPercent(float HpsPercent);
    public void playerCastingTimePercent(float castingTimePercent);
    public void playerModifacionHPs(int HPs, Color color);
}
