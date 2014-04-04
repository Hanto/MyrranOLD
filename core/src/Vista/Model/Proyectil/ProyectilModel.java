package Vista.Model.Proyectil;// Created by Hanto on 04/04/2014.

public interface ProyectilModel
{
    //OBSERVADORES:
    public void añadirObservador (ProyectilObservador observador);
    public void eliminarObservador (ProyectilObservador observador);
    //GET:
    public double getDireccion ();
    public int getX();
    public int getY();
}
