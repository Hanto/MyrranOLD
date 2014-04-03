package Vista.View;// Created by Hanto on 02/04/2014.

import Constantes.MiscData;
import Geo.GeoBook;
import Geo.Mapa.Celdas.Muro;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Comparator;

public class StageMundo extends Stage
{
    private Vista vista;

    static class OrdnarPorProfundidad implements Comparator<Actor>
    {
        @Override public int compare(Actor o1, Actor o2)
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

    public StageMundo(Vista vista)
    {
        this.vista = vista;
        crearMuros();
        crearMobs();
    }

    public void inicializar ()
    {
        this.clear();
        crearMuros();
        crearMobs();
    }

    private void crearMuros ()
    {
        Muro muro;
        String muroID;

        for (int x = 0; x < MiscData.MAPA_Max_X; x++)
        {
            for (int y = 0; y < MiscData.MAPA_Max_Y; y++)
            {
                muroID = vista.mapa.getMatriz()[x][y].getMuroID();
                if (muroID.length() >0)
                {
                    muro = new Muro(GeoBook.get().listaDeMuros.get(muroID));
                    muro.setPosition(x*MiscData.TILESIZE, y*MiscData.TILESIZE);
                    muro.crearMuro(vista.world, vista.mundo.getPlayer().getActor());
                    addActor(muro);
                }
            }
        }
    }

    public void crearMuro (int x, int y)
    {
        String iDMuro = vista.mapa.getMatriz()[x][y].getMuroID();
        Muro muro = new Muro(GeoBook.get().listaDeMuros.get(iDMuro));
        muro.setPosition(x*MiscData.TILESIZE, y*MiscData.TILESIZE);
        muro.crearMuro(vista.world, vista.mundo.getPlayer().getActor());
        addActor(muro);

    }

    public void crearMobs ()
    {
        addActor(vista.mundo.getPlayer().getActor());

        for (int i=0; i<vista.mundo.getListaDePlayers().size; i++)
        {   addActor(vista.mundo.getListaDePlayers().get(i).getActor()); }

        for (int i=0; i<vista.mundo.getListaDeProyectiles().size; i++)
        {   addActor(vista.mundo.getListaDeProyectiles().get(i).getActor());}
    }
}
