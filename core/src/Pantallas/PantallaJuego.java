package Pantallas;// Created by Hanto on 28/03/2014.

import Constantes.MiscData;
import Geo.GeoBook;
import Geo.Mapa.Celdas.Muro;
import Geo.Mapa.MVC.MapaModel;
import Geo.Mapa.MVC.MapaControlador;
import Graficos.PixieArbol;
import Graficos.Texto;
import Main.Mundo;
import Resources.Recursos;
import Save.SaveData;
import Skill.SkillBook;
import Skill.Spell.Data.SpellsData;
import UI.UIBook;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.Myrran.Myrran;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.util.Comparator;

import static Main.Mundo.player;

public class PantallaJuego extends AbstractPantalla
{
    private Texto fps;

    private Stage stageMundo;

    //private MapaView mapaView;
    private MapaControlador mapaControlador;
    private OrthogonalTiledMapRenderer mapRenderer;

    private World world;
    private RayHandler rayHandler;
    private PointLight luzPlayer;


    static class ComparatorActor implements Comparator<Actor>
    {
        @Override
        public int compare(Actor o1, Actor o2)
        {
            float o1Y, o2Y;
            o1Y = o1.getY();
            o2Y = o2.getY();
            if (o1 instanceof Muro && ((Muro)o1).perspectiva < 0 )
            { o1Y = Muro.distanciaPerspectiva-((Muro)o1).muroTecho.getY(); }
            if (o2 instanceof Muro && ((Muro)o2).perspectiva < 0 )
            { o2Y = Muro.distanciaPerspectiva-((Muro)o2).muroTecho.getY(); }
            return (o1Y < o2Y ? 1 : (o1Y == o2Y ? 1 : -1));
        }
    }

    //CONSTRUCTOR:
    public PantallaJuego (Myrran game)
    {
        super (game);

        //Creacion entidades PantallaJuego:
        stageMundo = new Stage();
        world = new World(new Vector2(0, -9.8f), false);
        rayHandler = new RayHandler (world);
        luzPlayer = new PointLight(rayHandler, 300, new Color(0.3f,0.3f,0.3f,1.0f), 350, 0, 0);

        //Carga Recursos y Datos:
        Recursos.crearRecursos();
        SkillBook.get().inicializar(Recursos.atlas);
        GeoBook.get().inicializar(Recursos.atlas);
        UIBook.get().inicializar(stageUI);

        Mundo.get().setStageMundo(stageMundo);
        Mundo.get().setWorld(world);
        Mundo.get().setRayHandler(rayHandler);
        player = Mundo.get().añadirPlayer(0, 0, 0, "Hanto");
        player.setPosition(500, 400);


        //Inicializacion Entidades:
        inputMultiplexer.addProcessor(stageMundo);
        stageMundo.getViewport().setCamera(camara);
        RayHandler.useDiffuseLight(true);
        rayHandler.setCombinedMatrix(camara.combined);
        rayHandler.setAmbientLight(0.4f, 0.4f, 0.4f, 1.0f);
        //luz = new PointLight(rayHandler, 100, new Color(1,1,1,0.7f), 150, 0, 0);
        MapaModel mapaModel = SaveData.loadMap("mapaInicial");
        if (mapaModel == null) mapaModel = new MapaModel("mapaInicial");
        mapaControlador = new MapaControlador(mapaModel);
        mapRenderer = new OrthogonalTiledMapRenderer(mapaControlador.getMapaView());


        GeoBook.get().setMapaControlador(mapaControlador);

    }


    @Override public void show ()
    {
        super.show();

        UIBook.get().getBarraSpells().setSkill(0, SkillBook.get().listaDeSpells.get(SpellsData.FIREBOLT_ID));
        UIBook.get().getBarraSpells().setSkill(1, SkillBook.get().listaDeSpells.get(SpellsData.FROSTBOLT_ID));
        UIBook.get().getBarraSpells().setSkill(2, SkillBook.get().listaDeSpells.get(SpellsData.MUROFORMAR_ID));
        UIBook.get().getBarraSpells().setSkill(3, SkillBook.get().listaDeSpells.get(SpellsData.TERRAFORMAR_ID));
        UIBook.get().getBarraSpells().setSkill(4, SkillBook.get().listaDeSpells.get(SpellsData.INSTAHEAL_ID));

        player.setActualHPs(1);

        player.getPixiePC().setCuerpo(0);
        player.getPixiePC().setBotas(0);
        player.getPixiePC().setGuantes(0);
        player.getPixiePC().setPeto(0);
        player.getPixiePC().setHombreras(0);
        player.getPixiePC().setPantalones(0);

        //player.getPixiePC().setCabeza(1);
        //player.getPixiePC().setYelmo(0);
        //player.getPixiePC().setCapaFrontal(1);
        //player.getPixiePC().setCapaTrasera(1);

        /*Pixie fireball = new Pixie(Recursos.listaDePixieProyectiles.get(0));
        stageMundo.addActor(fireball);
        fireball.setPosition(50, 50);
        fireball.setRotation(135);
        fireball.addAction(Actions.moveTo(1900, 1080, 5f, Interpolation.linear));*/

        PixieArbol parbol = new PixieArbol (0, player.getActor());
        parbol.setCopas(0, 1, 2);
        parbol.setPosition(100, 300);
        stageMundo.addActor(parbol);

        PixieArbol parbol2 = new PixieArbol (0, player.getActor());
        parbol2.setCopas(0, 1, 2);
        parbol2.setPosition(200, 200);
        stageMundo.addActor(parbol2);

        PixieArbol parbol3 = new PixieArbol(parbol, player.getActor());
        parbol3.setPosition(450, 150);
        stageMundo.addActor(parbol3);

        Texto texto= new Graficos.Texto("-125", Recursos.font14, Color.RED, Color.BLACK, 0, 0, Align.center, Align.bottom, 1);
        texto.scrollingCombatText(stageMundo, 2f);

        fps = new Graficos.Texto("fps", Recursos.font14, Color.WHITE, Color.BLACK, 0, 0, Align.left, Align.bottom, 2);
        stageUI.addActor(fps);
    }

    @Override public void render (float delta)
    {
        Mundo.actualizarPlayers(delta);
        Mundo.actualizarProyectiles(delta);
        Mundo.actualizarAurasPlayers(delta);

        super.render(delta);

        stageMundo.getActors().sort(new ComparatorActor());

        camara.position.x = player.getX()+player.getPixiePC().getWidth()/2;
        camara.position.y = player.getY()+player.getPixiePC().getWidth()/2;

        camara.update();
        batch.setProjectionMatrix(camara.combined);
        rayHandler.setCombinedMatrix(camara.combined);
        mapRenderer.setView(camara);

        batch.begin();

        batch.end();

        mapRenderer.render();
        stageMundo.act(delta);
        stageMundo.draw();

        luzPlayer.setPosition(Mundo.player.getX()+24, Mundo.player.getY());
        //Vector3 vectorLuz = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        //camara.unproject(vectorLuz);
        //luz.setPosition(vectorLuz.x, vectorLuz.y);
        rayHandler.updateAndRender();

        stageUI.draw();
        fps.setTexto(Integer.toString(Gdx.graphics.getFramesPerSecond())+"fps");
        //modoEntrelazado();
    }

    @Override public void resize(int anchura, int altura)
    {
        super.resize(anchura, altura);
        stageMundo.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    @Override public void dispose ()
    {
        super.dispose();

        if (stageMundo != null) stageMundo.dispose();
        if (rayHandler != null) rayHandler.dispose();
        if (mapaControlador.getMapaView() != null) mapaControlador.getMapaView().dispose();
        if (mapRenderer != null) mapRenderer.dispose();
        if (world != null) world.dispose();

        Recursos.liberarRecursos();
    }
/*
    private void crearMapa(Celda[][] mapa)
    {
        for (int x = 0; x < MiscData.MAPA_Max_X; x++)
        {
            for (int y = 0; y < MiscData.MAPA_Max_Y; y++)
            {
                Celda celda = new Celda();
                celda.getTerrenoID()[0]= TerrenosData.T001_ID;
                mapa[x][y]=celda;
            }
        }

        SaveData.loadMap("mapaInicial.bin");

        Muro muro;
        String muroID;

        for (int x = 0; x < MiscData.MAPA_Max_X; x++)
        {
            for (int y = 0; y < MiscData.MAPA_Max_Y; y++)
            {
                muroID = Mundo.get().getMapaView().map[x][y].getMuroID();
                if (muroID.length() >0)
                {
                    muro = new Muro(GeoBook.get().listaDeMuros.get(muroID));
                    muro.setPosition(x*MiscData.TILESIZE, y*MiscData.TILESIZE);
                    muro.crearMuro(stageMundo, world, player.getActor());

                }
            }
        }
    }*/

    public void modoEntrelazado ()
    {
        shape.setColor(Color.BLACK);
        shape.begin(ShapeRenderer.ShapeType.Line);

        for (int i=0; i<MiscData.WINDOW_Vertical_Resolution;i=i+4)
        {
            shape.line(0, i, MiscData.WINDOW_Horizontal_Resolution, i);
        }
        shape.end();
    }
}
