package Vista.View.Actores;// Created by Hanto on 04/04/2014.

import Graficos.Pixie;
import Skill.SkillBook;
import Skill.Spell.Data.SpellsData;
import Vista.Model.Proyectil.ProyectilModel;
import Vista.Model.Proyectil.ProyectilObservador;
import Vista.View.Vista;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ProyectilVista implements ProyectilObservador
{
    private Vista vista;
    private ProyectilModel proyectilModel;

    private Group actor = new Group();
    private PointLight luz;

    public Group getActor()                     { return actor; }

    //CONSTRUCTOR:
    public ProyectilVista(Vista vista, ProyectilModel proyectilModel)
    {
        this.vista = vista;
        this.proyectilModel = proyectilModel;
        crearActor();
    }

    public void crearActor ()
    {
        luz = new PointLight(vista.rayHandler, 100, new Color(1,0.5f,0.5f,0.4f), 200, proyectilModel.getX(), proyectilModel.getY());
        Pixie pixie = new Pixie(SkillBook.get().listaDePixieProyectiles.get(SpellsData.FIREBOLT_Pixie_Proyectil));
        actor.addActor(pixie);
        actor.setBounds(0,0,pixie.getWidth(), pixie.getHeight());
        actor.setOrigin(pixie.getWidth() / 2, pixie.getHeight() / 2);
        actor.setColor(0,0,0,0);
        actor.addAction(Actions.fadeIn(0.2f));
        ajustarRotacion(proyectilModel.getDireccion());
        vista.getStageMundo().addActor(actor);
    }

    public void ajustarRotacion(double direccion)
    {   actor.setRotation((float)Math.toDegrees(direccion)); }

    @Override public void proyectilPosition(int x, int y)
    {
        actor.setPosition(x, y);
        luz.setPosition(x, y);
    }

    @Override public void proyectilDireccion(double direccion)
    {   ajustarRotacion(proyectilModel.getDireccion()); }

    @Override public void proyectilExpirar()
    {
        vista.getStageMundo().getRoot().removeActor(actor);
        luz.remove();
        proyectilModel.eliminarObservador(this);
        vista.listaProyectiles.removeValue(this, true);
    }
}
