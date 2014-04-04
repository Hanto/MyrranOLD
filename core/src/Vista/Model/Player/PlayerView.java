package Vista.Model.Player;// Created by Hanto on 03/04/2014.

public interface PlayerView
{
    //OBSERVADORES:
    public void añadirObservador (PlayerObservador observador);
    public void eliminarObservador (PlayerObservador observador);
    //GET:
    public int getX();
    public int getY();
    public float getHPsPercent();
    public boolean isCasteando();
}
