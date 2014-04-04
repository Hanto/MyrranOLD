package Vista.Model.Mapa;// Created by Hanto on 03/04/2014.

import Geo.Mapa.Celdas.Celda;
import Geo.Mapa.Celdas.Mapa;

public interface MapaView
{
    //OBSERVADORES:
    public void añadirObservador (MapaObservador observador);
    public void eliminarObservador (MapaObservador observador);
    //GET:
    public Celda[][] getMatriz ();
    public Mapa getMapa();

}
