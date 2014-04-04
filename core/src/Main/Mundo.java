package Main;

import Actores.Mobs.Personajes.PCs.Player;
import Actores.Mobs.Proyectil;
import Geo.Mapa.Celdas.Muro;
import Skill.Aura.BDebuff;
import Vista.Model.Mundo.MundoModel;
import Vista.Model.Mundo.MundoObservador;
import box2dLight.RayHandler;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
 //* @author Ivan Delgado Huerta

//SINGLETON PATTERN:
public class Mundo implements MundoModel
{
    public Player player;
    public Array<Player> listaDePlayers = new Array<>();
    public Array<Proyectil> listaDeProyectiles = new Array<>();
    public Array<Muro> listaDeMuros = new Array<>();

    public Array<MundoObservador> listaObservadores = new Array<>();

    private Stage stageMundo;
    private RayHandler rayHandler;

    @Override public Player getPlayer()                         { return player; }
    @Override public Array<Player> getListaDePlayers()          { return listaDePlayers; }
    @Override public Array<Proyectil> getListaDeProyectiles()   { return listaDeProyectiles; }

    public void setStageMundo (Stage stage)                     { stageMundo = stage; }
    public void setRayHandler (RayHandler ray)                  { rayHandler = ray; }
    public RayHandler getRayHandler ()                          { return rayHandler; }

    private Mundo ()
    {
        player = new Player(0, 0, 0, "Hanto");
        player.setPosition(500, 400);
        listaDePlayers.add(player);
    }

    private static class Singleton  { private static final Mundo get = new Mundo(); }
    public static Mundo get()       { return Singleton.get; }
    
    @Override public void añadirProyectil (Proyectil proyectil)
    {
        listaDeProyectiles.add(proyectil);
        notificarAñadirProyectil(proyectil);
    }

    @Override public void eliminarProyectil (Proyectil proyectil)
    {   listaDeProyectiles.removeValue(proyectil, true); }
        
    public void actualizarPlayers (float delta)
    {
        for (int i=0; i<listaDePlayers.size; i++)
        { listaDePlayers.get(i).actualizar(delta); }
    }
    
    public void actualizarProyectiles (float delta)
    {
        for (int i=0; i<listaDeProyectiles.size; i++)
        { listaDeProyectiles.get(i).actualizar(delta); }
    }
    
    public void actualizarAurasPlayers (float delta)
    {
        for (int i=0; i<listaDePlayers.size; i++)
        {
            Array<BDebuff> listaDeAuras = listaDePlayers.get(i).listaDeAuras;
            for (int j=0; j<listaDeAuras.size;j++)
            {   listaDeAuras.get(j).actualizarDebuff(); }
        }
    }

    @Override public void añadirObservador(MundoObservador observador)
    {   listaObservadores.add(observador); }

    @Override public void eliminarObservador(MundoObservador observador)
    {   listaObservadores.removeValue(observador, true); }

    public void notificarAñadirProyectil(Proyectil proyectil)
    {
        for (int i=0; i<listaObservadores.size; i++)
        {   listaObservadores.get(i).añadirProyectil(proyectil); }
    }
}
