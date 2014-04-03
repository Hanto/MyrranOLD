package Vista.Model;// Created by Hanto on 02/04/2014.

import Geo.Mapa.Celdas.Celda;
import Geo.Mapa.MVC.Mapa;

public interface MapaModelInterface
{
    //GET:
    public Celda[][] getMatriz ();
    public Mapa getMapa();
    //SET:
    public boolean setTerreno (int x, int y, int numCapa, String iDTerreno);
    public boolean setMuro (int x, int y, String iDMuro);
    //OBSERVADORES:
    public void añadirObservador (MapaModelObservador observador);
    public void eliminarObservador (MapaModelObservador observador);

}