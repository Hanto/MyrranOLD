package Vista.Model.Mundo;// Created by Hanto on 03/04/2014.

import Actores.Mobs.Personajes.PCs.Player;
import Actores.Mobs.Proyectil;
import com.badlogic.gdx.utils.Array;

public interface MundoView
{
    //OBSERVADORES:
    public void añadirObservador (MundoObservador observador);
    public void eliminarObservador (MundoObservador observador);
    //GET:
    public Player getPlayer();
    public Array<Player> getListaDePlayers();
    public Array<Proyectil> getListaDeProyectiles();
}
