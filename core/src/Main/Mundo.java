package Main;

import Actores.Mobs.Personajes.PCs.Player;
import Actores.Mobs.Proyectil;
import Geo.Mapa.MVC.MapaView;
import Skill.Aura.BDebuff;
import box2dLight.RayHandler;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
 //* @author Ivan Delgado Huerta

//SINGLETON PATTERN:
public class Mundo
{
    private volatile static Mundo mundo;

    public static Player player;
    public static Array<Player> listaDePlayers = new Array<>();
    public static Array<Proyectil> listaDeProyectiles = new Array<>();

    private Stage stageMundo;
    private RayHandler rayHandler;
    private World world;
    private MapaView mapaView;

    public void setStageMundo (Stage stage)             { stageMundo = stage; }
    public void setWorld (World w)                      { world = w; }
    public void setRayHandler (RayHandler ray)          { rayHandler = ray; }
    public void setMapaView(MapaView map)                      { mapaView = map; }

    public Stage getStageMundo ()                       { return stageMundo; }
    public RayHandler getRayHandler ()                  { return rayHandler; }
    public World getWorld ()                            { return world; }
    public MapaView getMapaView()                              { return mapaView; }


    //Singleton Pattern:
    private Mundo ()                                    { }
    public static Mundo get()
    {
        if (mundo == null)
        synchronized (Mundo.class)
        {
            if (mundo == null)
                mundo = new Mundo();
        }
        return mundo;
    }

    public Player añadirPlayer (int numRaza, int posX, int posY, String nombre)
    {
        Player pc = new Player(0, posX, posY, nombre);
        Mundo.listaDePlayers.add(pc);
        stageMundo.addActor(pc.getActor());
        return pc;
    }
    
    public void añadirProyectil (Proyectil proyectil)
    {
        Mundo.listaDeProyectiles.add(proyectil);
        //proyectil.getPixie().setColor(0f, 0f, 0f, 0f);
        //proyectil.getPixie().addAction(Actions.fadeOut(1.5f, Interpolation.linear));
        stageMundo.addActor(proyectil.getPixie()); 
    }
        
    public void eliminarProyectil (Proyectil proyectil)
    {
        Mundo.listaDeProyectiles.removeValue(proyectil, true);
        stageMundo.getRoot().removeActor(proyectil.getPixie());
    }
        
    public static void actualizarPlayers (float delta)
    {
        for (int i=0; i<listaDePlayers.size; i++)
        { listaDePlayers.get(i).actualizar(delta); }
    }
    
    public static void actualizarProyectiles (float delta)
    {
        for (int i=0; i<listaDeProyectiles.size; i++)
        { listaDeProyectiles.get(i).actualizar(delta); }
    }
    
    public static void actualizarAurasPlayers (float delta)
    {
        for (int i=0; i<listaDePlayers.size; i++)
        {
            Array<BDebuff> listaDeAuras = listaDePlayers.get(i).listaDeAuras;
            for (int j=0; j<listaDeAuras.size;j++)
            {   listaDeAuras.get(j).actualizarDebuff(); }
        }
    }
}
