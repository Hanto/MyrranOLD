package Graficos;

import Constantes.MiscData;
import Resources.Recursos;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;

// @author Ivan Delgado Huerta
public class Nameplate extends Group
{
    //NAMEPLATES
    protected Sprite barraVidaTotal;                //Imagen que contiene el nameplateTotal de la vida del Player
    protected Sprite barraVidaActual;               //Imagen que contiene el fondo de la vida del nameplateTotal del Player
    protected float ultimosHPsRenderizados;
    protected Sprite barraCasteoTotal;
    protected Sprite barraCasteoActual;
    protected float ultimoCasteoRenderizado;
        
    public Nameplate ()
    {
        barraVidaTotal = new Sprite(Recursos.nameplateTotal);
        barraVidaActual = new Sprite(Recursos.nameplateActual);
        barraCasteoTotal = new Sprite(Recursos.nameplateTotal);
        barraCasteoActual = new Sprite(Recursos.nameplateActual);
        barraVidaTotal.setColor(MiscData.NAMEPLATE_Player_Vida);
        barraCasteoTotal.setColor(MiscData.NAMEPLATE_Player_Casteo);

        int alto = (int)barraVidaTotal.getHeight();
        int ancho = (int)barraVidaTotal.getWidth();
        //colocamos la barra de vida encima de la de casteo, -1, para que los rebordes se fusionen y no quede doble reborde
        barraVidaTotal.setPosition(0, alto-1);
        barraVidaActual.setPosition(ancho-1, alto-1);
        barraCasteoActual.setPosition(ancho-1, 0);
        
        this.setWidth(ancho);
        this.setHeight(alto);
        this.setBounds(0, 0, ancho, alto);
    }

    public void setHPsPercent (float HPsPercent)
    {
        float tamaño = (1 - HPsPercent) * this.getWidth();
        if (tamaño != barraVidaActual.getWidth()) barraVidaActual.setSize(-(int) tamaño, this.getHeight());
    }

    public void setCastingTimePercent (float castingTimePercent)
    {
        float tamaño = (1-castingTimePercent)*this.getWidth();
        if (tamaño != barraCasteoActual.getWidth()) barraCasteoActual.setSize(-(int)tamaño, this.getHeight());
    }
   
    @Override public void act(float delta)
    {
        float alto = this.getHeight();
        float ancho = this.getWidth();
        
        super.act(delta);
        barraVidaTotal.setPosition(this.getX(), this.getY()+alto-1);
        barraVidaActual.setPosition(this.getX()+ancho-1, this.getY()+alto-1);
        barraCasteoTotal.setPosition(this.getX(), this.getY());
        barraCasteoActual.setPosition(this.getX()+ancho-1, this.getY());        
    }
    
    @Override public void draw (Batch batch, float alpha)
    {
        barraVidaTotal.draw(batch, alpha);
        barraVidaActual.draw(batch, alpha);
        barraCasteoTotal.draw(batch, alpha);
        barraCasteoActual.draw(batch, alpha);
    }
    
    public void dibujarNameplate (Batch batch, float alpha)
    {
        float ancho = this.getWidth();
        float alto = this.getHeight();
        
        int tamañoBarraVida = (int)((1-0.5)*ancho);//(int)((1-personaje.getHPsPercent())*ancho);
        int tamañoBarraCasteo = (int)((1-0.5)*ancho);//(int)((1-personaje.getCastingTimePercent())*ancho);
        
        barraVidaTotal.draw(batch, alpha);
        if (tamañoBarraVida != ultimosHPsRenderizados)
        {
            barraVidaActual.setSize(-tamañoBarraVida, alto);
            ultimosHPsRenderizados = tamañoBarraVida;
        }
        barraVidaActual.draw(batch, alpha);
        
        barraCasteoTotal.draw(batch, alpha);
        if (tamañoBarraCasteo != ultimoCasteoRenderizado)
        {
            barraCasteoActual.setSize(-tamañoBarraCasteo, alto);
            ultimoCasteoRenderizado = tamañoBarraCasteo;
        }
        barraCasteoActual.draw(batch, alpha);
    }
}
