package Vista.Model;// Created by Hanto on 02/04/2014.

import Actores.Mobs.Personajes.PCs.Player;
import Actores.Mobs.Proyectil;
import com.badlogic.gdx.utils.Array;

public interface MundoModelInterface
{
    //GET:
    public Player getPlayer();
    public Array<Player> getListaDePlayers();
    public Array<Proyectil> getListaDeProyectiles();
    //SET:
    public void añadirProyectil (Proyectil proyectil);
    public void eliminarProyectil (Proyectil proyectil);
    //OBSERVADORES:
    public void añadirObservador (MundoModelObservador observador);
    public void eliminarObservador (MundoModelObservador observador);
}
