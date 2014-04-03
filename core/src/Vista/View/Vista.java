package Vista.View;//Created by Hanto on 02/04/2014.

import Actores.Mobs.Proyectil;
import Vista.Controlller.Controlador;
import Vista.Controlller.ControladorInterface;
import Vista.Model.MapaModelInterface;
import Vista.Model.MapaModelObservador;
import Vista.Model.MundoModelInterface;
import Vista.Model.MundoModelObservador;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.lang.reflect.Method;

public class Vista implements MapaModelObservador, MundoModelObservador
{
    //CONTROLADOR:
    protected ControladorInterface controlador;
    //MODELO:
    protected MapaModelInterface mapa;
    protected MundoModelInterface mundo;
    //VISTAS:
    protected TiledMapVista tiledMapVista;
    protected StageMundo stageMundo;
    protected Stage stageUI;

    protected OrthographicCamera camara;                            //la OrthographicCamera se encarga de hacer la conversion entre las distancias de juego y los pixeles de pantalla
    protected OrthogonalTiledMapRenderer mapRenderer;
    protected World world;
    protected RayHandler rayHandler;
    protected PointLight luzPlayer;
    protected boolean renderGrid = false;

    public OrthogonalTiledMapRenderer getMapRenderer()              { return mapRenderer; }
    public Stage getStageMundo()                                    { return stageMundo; }
    public Stage getStageUI()                                       { return stageUI; }
    public RayHandler getRayHandler()                               { return rayHandler; }

    public Vista (Controlador controlador, MapaModelInterface mapa, MundoModelInterface mundo)
    {
        this.controlador = controlador;
        this.mapa = mapa;
        this.mundo = mundo;

        this.world = new World(new Vector2(0, -9.8f), false);
        this.rayHandler = new RayHandler(world);
        this.rayHandler.setAmbientLight(0.4f, 0.4f, 0.4f, 1.0f);
        this.luzPlayer = new PointLight(rayHandler, 300, new Color(0.3f, 0.3f, 0.3f, 1.0f), 350, 0, 0);
        this.camara = new OrthographicCamera (Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.tiledMapVista = new TiledMapVista(this);
        this.stageMundo = new StageMundo(this);
        this.stageUI = new Stage();

        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMapVista);
        controlador.getInputMultiplexer().addProcessor(stageMundo);
        controlador.getInputMultiplexer().addProcessor(stageUI);

        this.mapa.añadirObservador(this);
        this.mundo.añadirObservador(this);
    }

    public void setMapa(MapaModelInterface mapa)
    {
        this.mapa.eliminarObservador(this);
        this.mapa = mapa;
        this.mapa.añadirObservador(this);

        tiledMapVista.inicializar();
        stageMundo.inicializar();
    }

    @Override public void añadirProyectil (Proyectil proyectil)     { stageMundo.addActor(proyectil.getActor()); }
    @Override public void eliminarProyectil (Proyectil proyectil)   { stageMundo.getRoot().removeActor(proyectil.getActor()); }
    @Override public void setTerreno(int x, int y, int numCapa)     { tiledMapVista.crearTile(x, y, numCapa); }
    @Override public void setMuro(int x, int y)                     { stageMundo.crearMuro(x, y); }

    public void aplicarNotificacionModel (String nombreMetodo, Object...nuevoValor)
    {
        try
        {
            Class<?>[] parametros = new Class[nuevoValor.length];

            for (int i=0; i<nuevoValor.length;i++)
            {
                parametros[i] = nuevoValor[i].getClass();
                if (nuevoValor[i] instanceof Integer) {parametros[i] = Integer.TYPE;}
            }

            Method method = mapa.getClass().getMethod(nombreMetodo, parametros);
            method.invoke(mapa, nuevoValor);

        }
        catch (Exception e) {System.out.println("Pumba");}
    }
}