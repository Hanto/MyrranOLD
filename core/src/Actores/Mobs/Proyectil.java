package Actores.Mobs;

import Actores.Mob;
import Interfaces.Caster;
import Interfaces.Debuffeable;
import Interfaces.Vulnerable;
import Main.Mundo;
import Skill.Spell.Spell;
import Skill.Spell.TiposSpell.Bolt;
import Vista.Model.Proyectil.ProyectilModel;
import Vista.Model.Proyectil.ProyectilObservador;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

//@author Ivan Delgado Huerta
public class Proyectil extends Mob implements ProyectilModel
{
    protected Caster owner;                     //Quien castea el spell
    protected Spell spell;                      //Datos del spell del cual proviene
    protected float duracionActual=0;           //Tiempo de vida que lleva el proyectil en el mundo
    protected float duracionMaxima;             //Tiempo maximo en segundos que permanece el proyectil

    //SET:
    public void setDuracionMaxima (float DuracionMaxima)    { duracionMaxima = DuracionMaxima; }
    public void setOwner (Caster Owner)                     { owner = Owner; }
    public void setSpell (Spell spell)                      { this.spell = spell; }
    //GET:
    public Spell getSpell()                                 { return spell; }
    public double getDireccion()                            { return direccion;}

    //Constructor:
    public Proyectil ()                                     { }

    public void crear ()                                    { Mundo.get().añadirProyectil(this);  }
    public void expirar ()                                  { Mundo.get().eliminarProyectil(this); notificarExpiracion(); }

    public void consumirse(float delta)
    {
        duracionActual = duracionActual + delta;
        if (duracionActual > duracionMaxima) expirar();
    }

    public void setPosition(float origenX, float origenY)
    {
        x=origenX; y =origenY;
        notificarPosicion((int)origenX, (int)origenY);
    }

    @Override public void setDireccion (double d)
    {
        super.setDireccion(d);
        notificarDireccion(d);
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

        float newPosX =  (float)(x+ (Math.cos(direccion))*velocidad*velocidadMod*delta);
        float newPosY =  (float)(y+ (Math.sin(direccion))*velocidad*velocidadMod*delta);

        setPosition(newPosX, newPosY);
    }
    
    public void actualizar (float delta)
    {
        consumirse(delta);
        moverse(delta);
    }

    protected Array<ProyectilObservador>listaObservadores = new Array<>();

    public void añadirObservador(ProyectilObservador observador)
    {   listaObservadores.add(observador); }

    public void eliminarObservador(ProyectilObservador observador)
    {   listaObservadores.removeValue(observador, true); }

    public void notificarPosicion(int x, int y)
    {   for (ProyectilObservador observador: listaObservadores)
            observador.proyectilPosition(x, y);
    }

    public void notificarDireccion (double direccion)
    {   for (ProyectilObservador observador: listaObservadores)
            observador.proyectilDireccion(direccion);
    }

    public void notificarExpiracion ()
    {   for (ProyectilObservador observador: listaObservadores)
            observador.proyectilExpirar();
    }
}
