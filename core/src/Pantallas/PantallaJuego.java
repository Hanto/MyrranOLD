package Pantallas;// Created by Hanto on 28/03/2014.

import Constantes.MiscData;
import Geo.GeoBook;
import Geo.Mapa.Celdas.Muro;
import Geo.Mapa.MVC.MapaControlador;
import Graficos.PixieArbol;
import Graficos.Texto;
import Main.Mundo;
import Resources.Recursos;
import Skill.SkillBook;
import Skill.Spell.Data.SpellsData;
import UI.UIBook;
import Vista.Controlller.Controlador;
import com.Myrran.Myrran;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.util.Comparator;

public class PantallaJuego extends AbstractPantalla
{
    private Texto fps;

    //private Stage stageMundo;
    private MapaControlador mapaControlador;
    private Controlador controlador;

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

        Recursos.crearRecursos();
        SkillBook.get().inicializar(Recursos.atlas);
        GeoBook.get().inicializar(Recursos.atlas);



        controlador = new Controlador("mapaInicial", Mundo.get());
        mapaControlador = new MapaControlador(controlador.getMapa(), Mundo.get().player.getActor());

        UIBook.get().inicializar(controlador.getStageUI());

        //mapaControlador.getStageMundo().addActor(Mundo.get().player.getActor());
        //controlador.getStageMundo().addActor(Mundo.get().player.getActor());


        Mundo.get().setStageMundo(mapaControlador.getStageMundo());
        Mundo.get().setRayHandler(mapaControlador.getRayHandler());


        //Inicializacion Entidades:

        //inputMultiplexer.addProcessor(controlador.getStageMundo());
        controlador.getStageMundo().getViewport().setCamera(camara);

        mapaControlador.getRayHandler().setCombinedMatrix(camara.combined);
        //mapaControlador.getRayHandler().setAmbientLight(0.4f, 0.4f, 0.4f, 1.0f);
        //luz = new PointLight(rayHandler, 100, new Color(1,1,1,0.7f), 150, 0, 0);


        GeoBook.get().setMapaControlador(mapaControlador);

        //Mapa mapa = new Mapa ("pim");
        //mapaControlador.setMapa(mapa);
    }


    @Override public void show ()
    {
        super.show();

        UIBook.get().getBarraSpells().setSkill(0, SkillBook.get().listaDeSpells.get(SpellsData.FIREBOLT_ID));
        UIBook.get().getBarraSpells().setSkill(1, SkillBook.get().listaDeSpells.get(SpellsData.FROSTBOLT_ID));
        UIBook.get().getBarraSpells().setSkill(2, SkillBook.get().listaDeSpells.get(SpellsData.MUROFORMAR_ID));
        UIBook.get().getBarraSpells().setSkill(3, SkillBook.get().listaDeSpells.get(SpellsData.TERRAFORMAR_ID));
        UIBook.get().getBarraSpells().setSkill(4, SkillBook.get().listaDeSpells.get(SpellsData.INSTAHEAL_ID));

        Mundo.get().player.setActualHPs(1);

        Mundo.get().player.getPixiePC().setCuerpo(0);
        Mundo.get().player.getPixiePC().setBotas(0);
        Mundo.get().player.getPixiePC().setGuantes(0);
        Mundo.get().player.getPixiePC().setPeto(0);
        Mundo.get().player.getPixiePC().setHombreras(0);
        Mundo.get().player.getPixiePC().setPantalones(0);

        //player.getPixiePC().setCabeza(1);
        //player.getPixiePC().setYelmo(0);
        //player.getPixiePC().setCapaFrontal(1);
        //player.getPixiePC().setCapaTrasera(1);

        /*Pixie fireball = new Pixie(Recursos.listaDePixieProyectiles.get(0));
        stageMundo.addActor(fireball);
        fireball.setPosition(50, 50);
        fireball.setRotation(135);
        fireball.addAction(Actions.moveTo(1900, 1080, 5f, Interpolation.linear));*/

        PixieArbol parbol = new PixieArbol (0, Mundo.get().player.getActor());
        parbol.setCopas(0, 1, 2);
        parbol.setPosition(100, 300);
        controlador.getStageMundo().addActor(parbol);

        PixieArbol parbol2 = new PixieArbol (0, Mundo.get().player.getActor());
        parbol2.setCopas(0, 1, 2);
        parbol2.setPosition(200, 200);
        controlador.getStageMundo().addActor(parbol2);

        PixieArbol parbol3 = new PixieArbol(parbol, Mundo.get().player.getActor());
        parbol3.setPosition(450, 150);
        controlador.getStageMundo().addActor(parbol3);

        fps = new Graficos.Texto("fps", Recursos.font14, Color.WHITE, Color.BLACK, 0, 0, Align.left, Align.bottom, 2);
        controlador.getStageUI().addActor(fps);
    }

    @Override public void render (float delta)
    {
        Mundo.get().actualizarPlayers(delta);
        Mundo.get().actualizarProyectiles(delta);
        Mundo.get().actualizarAurasPlayers(delta);

        super.render(delta);

        mapaControlador.getStageMundo().getActors().sort(new ComparatorActor());

        camara.position.x = Mundo.get().player.getX() + Mundo.get().player.getPixiePC().getWidth()/2;
        camara.position.y = Mundo.get().player.getY() + Mundo.get().player.getPixiePC().getWidth()/2;

        camara.update();
        batch.setProjectionMatrix(camara.combined);
        //mapaControlador.getRayHandler().setCombinedMatrix(camara.combined);
        controlador.getRayHandler().setCombinedMatrix(camara.combined);

        controlador.getMapRenderer().setView(camara);
        controlador.getMapRenderer().render();

        batch.begin();
        batch.end();

        //mapaControlador.getStageMundo().act(delta);
        //mapaControlador.getStageMundo().draw();

        controlador.getStageMundo().act(delta);
        controlador.getStageMundo().draw();


        mapaControlador.getMapaView().luzPlayer.setPosition(Mundo.get().player.getX()+24, Mundo.get().player.getY());
        //Vector3 vectorLuz = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        //camara.unproject(vectorLuz);
        //luz.setPosition(vectorLuz.x, vectorLuz.y);
        //mapaControlador.getRayHandler().updateAndRender();
        controlador.getRayHandler().updateAndRender();

        controlador.getStageUI().act();
        controlador.getStageUI().draw();

        fps.setTexto(Integer.toString(Gdx.graphics.getFramesPerSecond())+"fps");
        //modoEntrelazado();
    }

    @Override public void resize(int anchura, int altura)
    {
        super.resize(anchura, altura);
        mapaControlador.getStageMundo().getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    @Override public void dispose ()
    {
        super.dispose();

        if (mapaControlador.getStageMundo() != null) mapaControlador.getStageMundo().dispose();
        if (mapaControlador.getRayHandler() != null) mapaControlador.getRayHandler().dispose();
        if (mapaControlador.getTiledMap() != null) mapaControlador.getTiledMap().dispose();
        if (mapaControlador.getMapRenderer() != null) mapaControlador.getMapRenderer().dispose();
        if (mapaControlador.getWorld() != null) mapaControlador.getWorld().dispose();

        Recursos.liberarRecursos();
    }

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
