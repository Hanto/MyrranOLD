package Vista.View;// Created by Hanto on 04/04/2014.

import Graficos.Nameplate;
import Graficos.PixiePC;
import Graficos.Texto;
import Resources.Recursos;
import Vista.Model.Player.PlayerModel;
import Vista.Model.Player.PlayerObservador;
import Vista.Model.PlayerEstado.PlayerEstadoModel;
import Vista.Model.PlayerEstado.PlayerEstadoObservador;
import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class PlayerVista implements PlayerObservador, PlayerEstadoObservador
{
    private Vista vista;

    private PlayerModel player;
    private PlayerEstadoModel playerE;

    private Group actor = new Group();
    private PixiePC pixie;
    private Nameplate nameplate = new Nameplate();
    private Texto nombre;
    private PointLight luz;

    public Group getActor()                                     { return actor; }

    //CONSTRUCTOR:
    public PlayerVista(Vista vista, PlayerModel player, PlayerEstadoModel playerE)
    {
        this.vista = vista;
        this.player = player;
        this.playerE = playerE;
        crearActor();
    }

    public void crearActor()
    {
        luz = new PointLight(vista.rayHandler, 300, new Color(0.3f, 0.3f, 0.3f, 1.0f), 350, this.player.getX(), this.player.getY());
        pixie = new PixiePC(0);
        pixie.setAnimacion(playerE.getEstado(), false);
        nameplate.setPosition(pixie.getWidth()/2 - nameplate.getWidth()/2, pixie.getHeight()+2);
        nameplate.setHPsPercent(player.getHPsPercent());
        nombre = new Texto(player.getNombre(), Recursos.font14, Color.WHITE, Color.BLACK, pixie.getWidth()/2, 0, Align.center, Align.bottom, 1);
        nombre.setPosition(0, pixie.getHeight()+12);

        actor.addActor(nombre);
        actor.addActor(pixie);
        actor.addActor(nameplate);
        actor.setBounds(player.getX(),player.getY(),pixie.getWidth(),pixie.getHeight());

        vista.stageMundo.addActor(actor);
    }

    public void playerPosition(int x, int y)
    {
        luz.setPosition(x, y);
        actor.setPosition(x, y);
    }

    @Override
    public void playerIsCasteando(boolean isCasteando)
    {
        if (player.isCasteando()) pixie.setAnimacion(4, false);
        pixie.setAnimacion(playerE.getEstado(), false);
    }

    public void setPlayerEstado(int iDEstado)
    {
        if (player.isCasteando()) return;

        pixie.setAnimacion(iDEstado, false);
        if (iDEstado >=6)
        {
            Vector2 destino = vista.controlador.convertirCoordenadasPantallaAMundo(Gdx.input.getX(), Gdx.input.getY());

            float origenX = player.getX()+pixie.getWidth()/2;
            float origenY = player.getY()+pixie.getHeight()/2;


            double alpha = Math.atan2(destino.y -origenY, destino.x -origenX);
            double angulo = Math.toDegrees(alpha+2*(Math.PI))%360;

            if (67.5d<=angulo && angulo<112.5d)     { pixie.setAnimacion(11, false); }  //Arriba
            if (22.5d<=angulo && angulo<67.5d)      { pixie.setAnimacion(13, false); }  //ArribaDcha
            if (112.5d<=angulo && angulo<157.5d)    { pixie.setAnimacion(12, false); }  //ArribaIzda
            if (157.5d<=angulo && angulo<202.5d)    { pixie.setAnimacion(6, false); }   //Izda
            if (22.5>angulo && angulo>=0)           { pixie.setAnimacion(7, false); }   //Dcha
            if (337.5<=angulo && angulo<=360)       { pixie.setAnimacion(7, false); }   //Dcha
            if (247.5<=angulo && angulo<292.5)      { pixie.setAnimacion(10, false); }  //Abajo
            if (292.5<=angulo && angulo<337.5)      { pixie.setAnimacion(9, false); }   //AbajoDcha
            if (202.5<=angulo && angulo<247.5)      { pixie.setAnimacion(8, false); }   //AbajoIzda
        }

    }

    @Override public void playerHPsPercent(float HpsPercent)                  { nameplate.setHPsPercent(HpsPercent); }
    @Override public void playerCastingTimePercent(float castingTimePercent)  { nameplate.setCastingTimePercent(castingTimePercent); }
    @Override public void playerModifacionHPs(int HPs, Color color)
    {
        Texto texto = new Graficos.Texto(Integer.toString(HPs), Recursos.font14, color, Color.BLACK, 0, 0, Align.center, Align.bottom, 1);
        texto.setPosition(actor.getWidth()/2+(float)Math.random()*30-15, actor.getHeight()+15);
        texto.scrollingCombatText(actor, 2f);
    }
}
