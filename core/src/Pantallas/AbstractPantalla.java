package Pantallas;// Created by Hanto on 28/03/2014.

import com.Myrran.Myrran;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractPantalla implements Screen
{
    protected final Myrran myrran;
    protected final SpriteBatch batch;
    //protected final ShapeRenderer shape;
    //protected final InputMultiplexer inputMultiplexer;
    //protected final Stage stageUI;

    public static OrthographicCamera camara = new OrthographicCamera (Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  //la OrthographicCamera se encarga de hacer la conversion entre las distancias de juego y los pixeles de pantalla

    public String getNombrePantalla()           { return ((Object)this).getClass().getSimpleName(); }

    //CONSTRUCTOR:
    public AbstractPantalla (Myrran myrran)
    {
        this.myrran = myrran;                       //Es necesario disponer de la clase myrran, para poder por ejemplo cambiar de pantalla con el metodo myrran.navegarA(screen)
        this.batch = new SpriteBatch ();            //El SpriteBatch es el encargado de dibujar Bitmaps en pantalla, no es una variable, es un motor de dibujado, lo creamos para tenerlo listo
        //this.shape = new ShapeRenderer ();          //El shapeRenderer es como el anterior pero encargado de dibujar lineas
        //this.stageUI = new Stage();
        //this.inputMultiplexer = new InputMultiplexer();
    }

    //El metodo show se ejecuta una solo vez cuando se inicializa la pantalla
    @Override public void show()
    {
        Gdx.app.log( myrran.LOG, "SHOW (Inicializando Screen): " + getNombrePantalla());
        //Para decirle que acepte Inputs de los actores
        //inputMultiplexer.addProcessor(stageUI);

        //inputMultiplexer.addProcessor(new GestureDetector(new PlayerGestures()));
        //inputMultiplexer.addProcessor(new PlayerInput());
        //Gdx.input.setInputProcessor(inputMultiplexer);
        //Esto hay que activarlo solo para para conseguir un sistema de coordenanas Y-UP
        //camara.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    //El metodo render se ejecuta 60 veces por segundo
    @Override public void render(float delta)
    {   //limpia todo la pantalla con el color que le digamos
        Gdx.gl.glClearColor(0/2.55f, 0/2.55f, 0/2.55f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //stageUI.act(delta);     //para animar el Stage
        //stageUI.draw();       //para dibujar el Stage, hay que dibujarlo al final de tödo si queremos que se sobreimponga
    }

    @Override public void resize(int anchura, int altura)
    {
        Gdx.app.log( myrran.LOG, "RESIZE (Redimensionando Screen): "+ getNombrePantalla() +" a: "+anchura+" x "+altura);
        //stageUI.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        //stageUI.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    @Override public void pause()
    {   Gdx.app.log( myrran.LOG, "PAUSE (Pausando pantalla): " + getNombrePantalla()); }

    @Override public void resume()
    {   Gdx.app.log( myrran.LOG, "RESUME (Pantalla reanudada): " + getNombrePantalla()); }

    //El metodo Hide se ejecuta al cerrar la pantalla
    @Override public void hide()
    {   //Despues de cerrar la pantalla es neccesario liberar la memoria de todas las texturas que hayamos usado, por eso llamamos al metodo Dispose
        Gdx.app.log( myrran.LOG, "HIDE (Cerrando pantalla): "+ getNombrePantalla());
        dispose();
    }

    //Es el metodo para liberar los recursos usados
    @Override public void dispose()
    {
        Gdx.app.log( myrran.LOG, "DISPOSE (Liberando memoria): "+ getNombrePantalla());
        //Antes de liberar los recursos nos aseguramos que esten llenos, si no, da error
        if (batch != null) batch.dispose();
        //if (shape != null) shape.dispose();
        //if (stageUI != null) stageUI.dispose();
    }
}
