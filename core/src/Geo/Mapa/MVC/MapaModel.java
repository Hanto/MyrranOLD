package Geo.Mapa.MVC;// Created by Hanto on 01/04/2014.

import Constantes.MiscData;
import Geo.Mapa.Celdas.Celda;
import Geo.Mapa.MapObserver.MapaObservado;
import Geo.Mapa.MapObserver.MapaObservador;
import com.badlogic.gdx.utils.Array;

import java.util.UUID;

public class MapaModel implements MapaObservado
{
    private String id;
    private String nombre;
    public Celda[][] matriz = new Celda[MiscData.MAPA_Max_X][MiscData.MAPA_Max_Y];
    private Array<MapaObservador> listaObservadores = new Array<>();

    //SET
    public void setId (String ID)                   { id = ID; }
    public void setNombre (String s)                { nombre = s; }
    //GET
    public String getId ()                          { return id;}
    public String getNombre ()                      { return nombre; }

    //CONSTRUCTOR:
    public MapaModel(String ID, String Nombre)      { id = ID; nombre = Nombre; }
    public MapaModel(String Nombre)
    {
        UUID idUUID = UUID.randomUUID();
        id = idUUID.toString();
        nombre = Nombre;

        //inicializamos la matriz que contiene el MapaModel con Celdas nuevas:
        for (int x = 0; x < MiscData.MAPA_Max_X; x++)
        {
            for (int y = 0; y < MiscData.MAPA_Max_Y; y++)
            {
                Celda celda = new Celda();
                matriz[x][y] = celda;
            }
        }
    }

    public boolean setTerreno (int x, int y, int numCapa, String iDTerreno)
    {
        if (x<0 || y<0 || x> MiscData.MAPA_Max_X || y> MiscData.MAPA_Max_Y) { return false; }
        else
        {
            matriz[x][y].getTerrenoID()[numCapa] = iDTerreno;
            notificarObservadores(x, y, numCapa);
            return true;
        }
    }

    public boolean setMuro (int x, int y, String iDMuro)
    {
        if (matriz[x][y].getMuroID().length() <= 0)
        {
            matriz[x][y].setMuroId(iDMuro);
            return true;
        }
        else return false;
    }

    @Override public void registrarObservador(MapaObservador observador)
    {   listaObservadores.add(observador); }

    @Override public void eliminarObservador(MapaObservador observador)
    {   listaObservadores.removeValue(observador, true); }

    @Override public void notificarObservadores(int x, int y, int numCapa)
    {
        for (int i=0; i<listaObservadores.size; i++)
        {   listaObservadores.get(i).actualizarTerreno(x, y, numCapa); }
    }
}
