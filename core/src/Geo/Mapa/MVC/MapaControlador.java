package Geo.Mapa.MVC;// Created by Hanto on 01/04/2014.

import Geo.Mapa.Celdas.Mapa;
import box2dLight.RayHandler;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MapaControlador
{
    private MapaView mapaView;                          //Clase que se encarga de la visualizacion de los Datos del Mapa
    private Mapa mapa;                        //Clase que se contiene los Datos del Mapa

    public MapaView getMapaView ()                      { return mapaView; }
    public Mapa getMapa()                     { return mapa; }
    public TiledMap getTiledMap ()                      { return mapaView.getTiledMap(); }
    public OrthogonalTiledMapRenderer getMapRenderer()  { return mapaView.getMapRenderer(); }
    public Stage getStageMundo ()                       { return mapaView.getStageMundo(); }
    public World getWorld()                             { return mapaView.getWorld(); }
    public RayHandler getRayHandler()                   { return mapaView.getRayHandler(); }

    public MapaControlador (Mapa map, Actor player)
    {
        mapa = map;
        mapaView = new MapaView(this, mapa, player);
    }

    public void setMapa(Mapa map)
    {
        mapa = map;
        mapaView.setMapa(map);
    }

    public void setRenderGrid (boolean b)           { mapaView.setRenderGrid(b);}

    public void setTerreno (int x, int y, int numCapa, String iDTerreno)
    {
        mapa.setTerreno(x, y, numCapa, iDTerreno);
        mapaView.crearTile(x, y, numCapa);
    }

    public void crearMuro (int x, int y, String iDMuro)
    {
        if (mapa.setMuro(x, y, iDMuro)) mapaView.crearMuro(x, y, iDMuro);
    }
}
