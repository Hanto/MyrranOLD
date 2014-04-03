package Vista.Model;// Created by Hanto on 02/04/2014.

import Actores.Mobs.Proyectil;

public interface MundoModelObservador
{
    public void añadirProyectil(Proyectil proyectil);
    public void eliminarProyectil(Proyectil proyectil);
}
