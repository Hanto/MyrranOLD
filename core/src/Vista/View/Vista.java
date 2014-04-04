package Vista.View;//Created by Hanto on 02/04/2014.

import Actores.Mobs.Proyectil;
import Vista.Controller.Controlador;
import Vista.Controller.ControladorInterface;
import Vista.Model.Mapa.MapaModel;
import Vista.Model.Mapa.MapaObservador;
import Vista.Model.Mapa.MapaView;
import Vista.Model.Mundo.MundoModel;
import Vista.Model.Mundo.MundoObservador;
import Vista.Model.Mundo.MundoView;
import Vista.Model.Player.PlayerModel;
import Vista.Model.Player.PlayerView;
import Vista.Model.PlayerEstado.PlayerEstadoModel;
import Vista.View.Actores.ProyectilVista;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class Vista implements MapaObservador, MundoObservador
{
    //CONTROLADOR:
    public ControladorInterface controlador;
    //MODELO:
    public PlayerView player;
    public PlayerEstadoModel playerE;
    public MapaView mapa;
    public MundoView mundo;
    //VISTAS:
    public Array<ProyectilVista> listaProyectiles = new Array<>();
    public PlayerVista playerVista;

    protected TiledMapMundo tiledMapMundo;
    protected StageMundo stageMundo;
    protected Stage stageUI;

    protected SpriteBatch batch;
    protected OrthographicCamera camara;                            //la OrthographicCamera se encarga de hacer la conversion entre las distancias de juego y los pixeles de pantalla
    protected OrthogonalTiledMapRenderer mapRenderer;
    protected World world;
    public RayHandler rayHandler;
    protected boolean renderGrid = false;

    public Stage getStageMundo()                                    { return stageMundo; }
    public Stage getStageUI()                                       { return stageUI; }
    public Camera getCamara()                                       { return camara; }

    public Vista (Controlador controlador, PlayerModel player, PlayerEstadoModel playerE, MapaModel mapa, MundoModel mundo)
    {
        this.controlador = controlador;
        this.player = player;
        this.playerE = playerE;
        this.mapa = mapa;
        this.mundo = mundo;

        this.world = new World(new Vector2(0, -9.8f), false);
        this.rayHandler = new RayHandler(world);
        this.rayHandler.setAmbientLight(0.4f, 0.4f, 0.4f, 1.0f);
        this.camara = new OrthographicCamera (Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch = new SpriteBatch();

        this.tiledMapMundo = new TiledMapMundo(this);
        this.stageMundo = new StageMundo(this);
        this.stageUI = new Stage();
        this.playerVista = new PlayerVista(this, player, playerE);

        this.stageMundo.getViewport().setCamera(camara);
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMapMundo);
        controlador.getInputMultiplexer().addProcessor(stageMundo);
        controlador.getInputMultiplexer().addProcessor(stageUI);

        this.mapa.añadirObservador(this);
        this.mundo.añadirObservador(this);
        this.player.añadirObservador(playerVista);
        this.playerE.añadirObservador(playerVista);
    }

    public void setMapa(MapaModel mapa)
    {
        this.mapa.eliminarObservador(this);
        this.mapa = mapa;
        this.mapa.añadirObservador(this);

        tiledMapMundo.inicializar();
        stageMundo.inicializar();
    }

    public void render (float delta)
    {
        Gdx.gl.glClearColor(0/2.55f, 0/2.55f, 0/2.55f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camara.position.x = player.getX();
        camara.position.y = player.getY();
        camara.update();

        stageMundo.ordenarPorProfundidad();

        batch.setProjectionMatrix(camara.combined);
        rayHandler.setCombinedMatrix(camara.combined);
        mapRenderer.setView(camara);

        mapRenderer.render();
        batch.begin();
        batch.end();
        stageMundo.act(delta);
        stageMundo.draw();
        rayHandler.updateAndRender();
        stageUI.act(delta);
        stageUI.draw();
    }

    public void resize (int anchura, int altura)
    {
        stageMundo.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        stageUI.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    public void setStageMundoZoom (float nivelZoom)           { camara.zoom = nivelZoom; }

    @Override public void añadirProyectil (Proyectil proyectil)
    {
        ProyectilVista pepo = new ProyectilVista(this, proyectil);
        listaProyectiles.add(pepo);
        proyectil.añadirObservador(pepo);

    }
    @Override public void setTerreno(int x, int y, int numCapa)     { tiledMapMundo.crearTile(x, y, numCapa); }
    @Override public void setMuro(int x, int y)                     { stageMundo.crearMuro(x, y); }


    public void dispose ()
    {
        if (tiledMapMundo != null)  tiledMapMundo.dispose();
        if (stageMundo != null)     tiledMapMundo.dispose();
        if (stageUI != null)        stageUI.dispose();
        if (batch != null)          batch.dispose();
        if (mapRenderer != null)    mapRenderer.dispose();
        if (world != null)          world.dispose();
        if (rayHandler != null)     rayHandler.dispose();
    }
}