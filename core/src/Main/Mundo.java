package Main;

import Actores.Mobs.Personajes.PCs.Player;
import Actores.Mobs.Proyectil;
import Constantes.MiscData;
import Geo.Celda;
import Geo.Muro;
import Geo.Terreno;
import Skill.Aura.BDebuff;
import UI.BarraTerrenos;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import static Resources.Recursos.atlas;
 //* @author Ivan Delgado Huerta

//SINGLETON PATTERN:
public class Mundo
{
    public static Player player;
    public static Array<Terreno> listaDeTerrenos = new Array<>();
    public static Array<Muro> listaDeMuros = new Array<>();
    public static Array<Player> listaDePlayers = new Array<>();
    public static Array<Proyectil> listaDeProyectiles = new Array<>();
    public static Celda[][] mapa = new Celda[MiscData.MAPA_Max_X][MiscData.MAPA_Max_Y];
    public static TiledMap tiledMap = new TiledMap();
    public static OrthogonalTiledMapRenderer mapRenderer;
    public static World world;



    private volatile static Mundo mundo;
    private Stage stageMundo;

    private BarraTerrenos barraTerrenos;
    private RayHandler rayHandler;

    public void setStageMundo (Stage stage)             { stageMundo = stage; }
    public void setRayHandler (RayHandler ray)          { rayHandler = ray; }
    public void setBarraTerrenos (BarraTerrenos barra)  { barraTerrenos = barra; }
    public Stage getStageMundo ()                       { return stageMundo; }
    public RayHandler getRayHandler ()                  { return rayHandler; }
    public BarraTerrenos getBarraTerrenos ()            { return barraTerrenos; }

    private Mundo ()                                    { }

    //Singleton Pattern:
    public static Mundo getMundo ()
    {
        if (mundo == null)
        synchronized (Mundo.class)
        {
            if (mundo == null)
                mundo = new Mundo();
        }
        return mundo;
    }

    public Player añadirPlayer (int numRaza, int posX, int posY, String nombre, Stage stageUI)
    {
        Player pc = new Player(0, posX, posY, nombre, stageUI);
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
    
    public static void añadirTerreno (String nombreTerreno)
    {
        Terreno terreno = new Terreno();
        terreno.setNombre(nombreTerreno);
        terreno.setTextura(nombreTerreno);
        terreno.setColor(Color.GRAY);
        Mundo.listaDeTerrenos.add(terreno);
    }
    
    public static void añadirMuro (String muroBase, String muroMedio, String muroTecho)
    {
        TextureRegion muroBaseTex = new TextureRegion(atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+muroBase));
        TextureRegion muroMedioTex = new TextureRegion(atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+muroMedio));
        TextureRegion muroTechoTex = new TextureRegion(atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+muroTecho));
        Muro muro = new Muro(muroBaseTex, muroMedioTex, muroTechoTex);
        Mundo.listaDeMuros.add(muro);
    }
}
