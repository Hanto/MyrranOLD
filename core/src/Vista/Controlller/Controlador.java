package Vista.Controlller;// Created by Hanto on 02/04/2014.

import Actores.Mobs.Proyectil;
import Geo.Mapa.MVC.Mapa;
import Input.PlayerGestures;
import Input.PlayerInput;
import Save.SaveData;
import Vista.Model.MapaModelInterface;
import Vista.Model.MundoModelInterface;
import Vista.View.Vista;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Controlador implements ControladorInterface
{
    //MODELOS:
    private MapaModelInterface mapaModel;
    private MundoModelInterface mundoModel;
    //VISTA::
    private Vista vista;
    //INPUT:
    protected InputMultiplexer inputMultiplexer;
    //GET:
    public InputMultiplexer getInputMultiplexer()                       { return inputMultiplexer; }
    public MapRenderer getMapRenderer()                                 { return vista.getMapRenderer(); }
    public Stage getStageMundo()                                        { return vista.getStageMundo(); }
    public Stage getStageUI()                                           { return vista.getStageUI(); }
    public RayHandler getRayHandler()                                   { return vista.getRayHandler(); }
    public Mapa getMapa ()                                              { return mapaModel.getMapa(); }

    //CONSTRUCTORES:
    public Controlador (String nombreMapa, MundoModelInterface mundo)
    {
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new GestureDetector(new PlayerGestures()));
        inputMultiplexer.addProcessor(new PlayerInput());
        Gdx.input.setInputProcessor(inputMultiplexer);

        Mapa mapa = SaveData.loadMap(nombreMapa);
        if (mapa == null) { mapa = new Mapa("mapaInicial"); }

        this.mapaModel = mapa;
        this.mundoModel = mundo;
        this.vista = new Vista(this, mapaModel, mundoModel);
    }

    public Controlador (MapaModelInterface mapa, MundoModelInterface mundo)
    {
        this.mapaModel = mapa;
        this.mundoModel = mundo;
        this.vista = new Vista(this, mapaModel, mundoModel);
    }

    public void setMapa (MapaModelInterface mapa)
    {
        this.mapaModel = mapa;
        vista.setMapa(mapaModel);
    }

    public void añadirProyectil (Proyectil proyectil)                   { mundoModel.añadirProyectil(proyectil); }
    public void eliminarProyectil (Proyectil proyectil)                 { mundoModel.eliminarProyectil(proyectil); }
    public void setTerreno (int x, int y, int numCapa, String iDTerreno){ mapaModel.setTerreno(x, y, numCapa, iDTerreno); }
    public void setMuro (int x, int y, String iDMuro)                   { mapaModel.setMuro(x, y, iDMuro); }
}
