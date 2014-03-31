package com.Myrran;

import Pantallas.PantallaJuego;
import Pantallas.PantallaMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class Myrran extends Game
{
    public String LOG = Myrran.class.getSimpleName();
    public enum tipoPantalla { pantallaMenu, pantallaJuego }

	@Override public void create ()     { nagevarA(tipoPantalla.pantallaJuego); }

    public void nagevarA (tipoPantalla pantalla)
    {
        Screen screen;
        switch (pantalla)
        {
            case pantallaMenu:  screen = new PantallaMenu(this); break;
            case pantallaJuego: screen = new PantallaJuego(this); break;
            default:            screen = new PantallaMenu(this); break;
        }
        setScreen(screen);
    }
}
