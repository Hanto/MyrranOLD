package Geo.Mapa.MapObserver;// Created by Hanto on 01/04/2014.

public interface MapaObservado
{
    public void registrarObservador (MapaObservador observador);
    public void eliminarObservador (MapaObservador observador);
    public void notificarObservadores (int x, int y, int numCapa);
}
