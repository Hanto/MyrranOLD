package Actores.Mobs;

import Actores.Mob;
import Graficos.Pixie;
import Interfaces.Caster;
import Interfaces.Debuffeable;
import Interfaces.Vulnerable;
import Main.Mundo;
import Skill.Spell.Spell;
import Skill.Spell.TiposSpell.Bolt;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

//@author Ivan Delgado Huerta
public class Proyectil extends Mob
{
    protected Caster owner;                  //Quien castea el spell
    protected Spell spell;
   
    protected float duracionActual=0;           //Tiempo de vida que lleva el proyectil en el mundo
    protected float duracionMaxima;             //Tiempo maximo en segundos que permanece el proyectil
    protected PointLight luz;                   //Luz que genera el Bolt

    //SET:
    public void setDuracionMaxima (float DuracionMaxima)    { duracionMaxima = DuracionMaxima; }
    public void setOwner (Caster Owner)                     { owner = Owner; }
    public void setSpell (Spell spell)                      { this.spell = spell; }
    public void setPixie (Pixie pixie)                      { this.actor.addActor(new Pixie(pixie)); }
    //GET:
    public Spell getSpell()                                 { return spell; }
    
    
    public Proyectil (Pixie pixie)
    {   //Creamos la animacion del proyectil y ajustamos su centro de gravedad, lo hacemos totalmente invisible 
        //y le ponemos una animacion de fade in para que aparezca suavemente
        this.actor.addActor(new Pixie(pixie));
        this.actor.setOrigin(pixie.getWidth()/2, pixie.getHeight()/2);
        this.actor.setColor(0, 0, 0, 0);
        this.actor.addAction(Actions.fadeIn(0.1f));
        luz = new PointLight(Mundo.get().getRayHandler(), 100, new Color(1,0.5f,0.5f,0.4f), 200, 0, 0);
    }
    
    public void expirar ()                                  { luz.remove(); Mundo.get().eliminarProyectil(this); }
    public void crear ()                                    { Mundo.get().añadirProyectil(this);  }
    public void consumirse(float delta)
    {
        duracionActual = duracionActual + delta;
        if (duracionActual > duracionMaxima) expirar();
    }
    
    public void setPosition(float origenX, float origenY)
    {   //tenemos que mover la entidad y su actor:
        x=origenX; y =origenY;
        actor.setPosition(origenX, origenY);
    }
    
    @Override public void setDireccion (double d)
    {   //al cambiar la direccion del proyectil, tambien tenemos que alterar su rotacion
        super.setDireccion(d);
        //this.pixie.rotate((float)Math.toDegrees(direccion));
        this.actor.setRotation((float)Math.toDegrees(direccion));
    }
    
    public void procesarColision (Vulnerable target)
    {
        int daño = Math.round(spell.skillStats()[Bolt.STAT_Daño].valorBase);
        target.modificarHPs(daño, Color.RED);
        if (target instanceof Debuffeable)
        {
            spell.aplicarAuras(owner, (Debuffeable)target);
        }
    }
    
    public void moverse(float delta)
    {
        oldPosX = x;
        oldPosY = y;

        x=  (float)(x+ (Math.cos(direccion))*velocidad*velocidadMod*delta);
        y=  (float)(y+ (Math.sin(direccion))*velocidad*velocidadMod*delta);

        actor.setPosition(x, y);
        luz.setPosition(x+actor.getOriginX(), y+actor.getOriginY());
    }
    
    public void actualizar (float delta)
    {
        consumirse(delta);
        moverse(delta);
    }
}
