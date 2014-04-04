package Vista.Model.PlayerEstado;// Created by Hanto on 03/04/2014.

public interface PlayerEstadoModel
{
    //OBSERVADORES:
    public void añadirObservador (PlayerEstadoObservador observador);
    public void eliminarObservador (PlayerEstadoObservador observador);
    //GET:
    public int getEstado();
}
