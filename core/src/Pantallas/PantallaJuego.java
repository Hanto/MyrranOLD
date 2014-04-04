package Pantallas;// Created by Hanto on 28/03/2014.

import Geo.GeoBook;
import Geo.Mapa.MVC.MapaControlador;
import Graficos.PixieArbol;
import Graficos.Texto;
import Main.Mundo;
import Resources.Recursos;
import Skill.SkillBook;
import Skill.Spell.Data.SpellsData;
import UI.UIBook;
import Vista.Controller.Controlador;
import com.Myrran.Myrran;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class PantallaJuego extends AbstractPantalla
{
    private Texto fps;

    //private Stage stageMundo;
    private MapaControlador mapaControlador;
    private Controlador controlador;

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

        Mundo.get().setStageMundo(mapaControlador.getStageMundo());
        Mundo.get().setRayHandler(mapaControlador.getRayHandler());

        //luz = new PointLight(rayHandler, 100, new Color(1,1,1,0.7f), 150, 0, 0);
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

/*
        Mundo.get().player.getPixiePC().setCuerpo(0);
        Mundo.get().player.getPixiePC().setBotas(0);
        Mundo.get().player.getPixiePC().setGuantes(0);
        Mundo.get().player.getPixiePC().setPeto(0);
        Mundo.get().player.getPixiePC().setHombreras(0);
        Mundo.get().player.getPixiePC().setPantalones(0);*/

        Mundo.get().player.modificarHPs(-500, Color.RED);

        //player.getPixiePC().setCabeza(1);
        //player.getPixiePC().setYelmo(0);
        //player.getPixiePC().setCapaFrontal(1);
        //player.getPixiePC().setCapaTrasera(1);

        /*Pixie fireball = new Pixie(Recursos.listaDePixieProyectiles.get(0));
        stageMundo.addActor(fireball);
        fireball.playerPosition(50, 50);
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

        controlador.render(delta);

        fps.setTexto(Integer.toString(Gdx.graphics.getFramesPerSecond())+"fps");
    }

    @Override public void resize(int anchura, int altura)
    {
        super.resize(anchura, altura);
        controlador.resize(anchura, altura);
    }

    @Override public void dispose ()
    {
        super.dispose();

        if (mapaControlador.getStageMundo() != null) mapaControlador.getStageMundo().dispose();
        if (mapaControlador.getRayHandler() != null) mapaControlador.getRayHandler().dispose();
        if (mapaControlador.getTiledMap() != null) mapaControlador.getTiledMap().dispose();
        if (mapaControlador.getMapRenderer() != null) mapaControlador.getMapRenderer().dispose();
        if (mapaControlador.getWorld() != null) mapaControlador.getWorld().dispose();

        controlador.dispose();
        Recursos.liberarRecursos();
    }
}
