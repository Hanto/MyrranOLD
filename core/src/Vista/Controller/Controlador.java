package Vista.Controller;// Created by Hanto on 02/04/2014.

import Geo.Mapa.Celdas.Mapa;
import Save.SaveData;
import Vista.Controller.Input.PlayerGestures;
import Vista.Controller.Input.PlayerInput;
import Vista.Model.Mapa.MapaModel;
import Vista.Model.Mundo.MundoModel;
import Vista.Model.Player.PlayerModel;
import Vista.Model.PlayerEstado.PlayerEstadoModel;
import Vista.View.Vista;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Controlador implements ControladorInterface
{
    //MODELOS:
    public PlayerModel player;
    public PlayerEstadoModel playerE;
    public MapaModel mapa;
    public MundoModel mundo;
    //VISTA:
    private Vista vista;
    //INPUT:
    private InputMultiplexer inputMultiplexer = new InputMultiplexer();
    //GET:
    public InputMultiplexer getInputMultiplexer()                       { return inputMultiplexer; }
    public Stage getStageMundo()                                        { return vista.getStageMundo(); }
    public Stage getStageUI()                                           { return vista.getStageUI(); }
    public Mapa getMapa ()                                              { return mapa.getMapa(); }

    public void render(float delta)                                     { vista.render(delta); }
    public void resize (int anchura, int altura)                        { vista.resize(anchura, altura); }
    public void dispose ()                                              { vista.dispose(); }

    //CONSTRUCTORES:
    public Controlador (String nombreMapa, MundoModel mundo)
    {
        Mapa mapa = SaveData.loadMap(nombreMapa);
        if (mapa == null) { mapa = new Mapa("mapaInicial"); }

        this.player = mundo.getPlayer();
        this.playerE = mundo.getPlayer().getEstado();
        this.mapa = mapa;
        this.mundo = mundo;
        this.vista = new Vista(this, player, this.playerE, this.mapa, this.mundo);

        //El orden de los InputProcessors es importante, ya que va mirando que listener salta en el orden
        //en que hayamos añadido los InputProcessors. Y si hay alguno que tiene el flag de no mirar mas Listeners
        //los que abrian a continuacion ya no se mirarian
        inputMultiplexer.addProcessor(new GestureDetector(new PlayerGestures()));
        inputMultiplexer.addProcessor(new PlayerInput(this));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
    public Controlador (MapaModel mapa, MundoModel mundo)
    {
        this.player = mundo.getPlayer();
        this.mapa = mapa;
        this.mundo = mundo;
        this.vista = new Vista(this, player, this.playerE, this.mapa, this.mundo);
    }

    public void setMapa (MapaModel mapa)
    {
        this.mapa = mapa;
        vista.setMapa(this.mapa);
    }

    public Vector2 convertirCoordenadasPantallaAMundo(int screenX, int screenY)
    {
        Vector3 destino = new Vector3(screenX, screenY, 0);
        vista.getCamara().unproject(destino);
        return new Vector2(destino.x, destino.y);
    }

    public void aplicarZoom(int nivelDeZoom)
    {
        float zoom = 1;
        if (nivelDeZoom < 0) zoom = 1f/(Math.abs(nivelDeZoom)+1f);
        if (nivelDeZoom ==0) zoom = 1f;
        if (nivelDeZoom > 0) zoom = 1f+nivelDeZoom*0.2f;
        vista.setStageMundoZoom(zoom);
    }





/*
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
    }*/
}
