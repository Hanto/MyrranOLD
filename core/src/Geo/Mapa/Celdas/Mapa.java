package Geo.Mapa.Celdas;// Created by Hanto on 01/04/2014.

import Constantes.MiscData;
import Vista.Model.Mapa.MapaModel;
import Vista.Model.Mapa.MapaObservador;
import com.badlogic.gdx.utils.Array;

import java.util.UUID;

public class Mapa implements MapaModel
{
    private String id;
    private String nombre;
    private Celda[][] matriz = new Celda[MiscData.MAPA_Max_X][MiscData.MAPA_Max_Y];
    private Array<MapaObservador> listaObservadores = new Array<>();

    //SET
    public void setId (String ID)                   { id = ID; }
    public void setNombre (String s)                { nombre = s; }
    public void setMatriz (Celda[][] matrix)        { matriz = matrix; }
    //GET
    public String getId ()                          { return id;}
    public String getNombre ()                      { return nombre; }
    @Override public Celda[][] getMatriz ()         { return matriz; }

    //CONSTRUCTOR:
    public Mapa(String ID, String Nombre)           { id = ID; nombre = Nombre; }
    public Mapa(String Nombre)
    {
        UUID idUUID = UUID.randomUUID();
        id = idUUID.toString();
        nombre = Nombre;

        //inicializamos la matriz que contiene el Mapa con Celdas nuevas:
        for (int x = 0; x < MiscData.MAPA_Max_X; x++)
        {
            for (int y = 0; y < MiscData.MAPA_Max_Y; y++)
            {
                Celda celda = new Celda();
                matriz[x][y] = celda;
            }
        }
    }

    @Override public Mapa getMapa ()                    { return this; }
    @Override public boolean setTerreno (int x, int y, int numCapa, String iDTerreno)
    {
        if (x<0 || y<0 || x> MiscData.MAPA_Max_X || y> MiscData.MAPA_Max_Y) { return false; }
        else
        {
            matriz[x][y].getTerrenoID()[numCapa] = iDTerreno;
            notificarSetTerreno(x, y, numCapa);
            return true;
        }
    }

    @Override public boolean setMuro (int x, int y, String iDMuro)
    {
        if (x<0 || y<0 || x> MiscData.MAPA_Max_X || y> MiscData.MAPA_Max_Y) { return false; }
        else if (matriz[x][y].getMuroID().length() <= 0)
        {
            matriz[x][y].setMuroId(iDMuro);
            notificarSetMuro(x, y);
            return true;
        }
        else return false;
    }

    @Override public void añadirObservador(MapaObservador observador)
    {   listaObservadores.add(observador); }

    @Override public void eliminarObservador(MapaObservador observador)
    {   listaObservadores.removeValue(observador, true); }

    public void notificarSetTerreno(int x, int y, int numCapa)
    {   for (MapaObservador observador : listaObservadores)
            observador.setTerreno(x,y,numCapa);
    }

    public void notificarSetMuro(int x, int y)
    {   for (MapaObservador observador : listaObservadores)
            observador.setMuro(x, y);
    }
}
