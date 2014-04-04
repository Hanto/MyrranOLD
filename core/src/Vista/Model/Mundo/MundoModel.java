package Vista.Model.Mundo;// Created by Hanto on 02/04/2014.

import Actores.Mobs.Proyectil;

public interface MundoModel extends MundoView
{
    //SET:
    public void añadirProyectil (Proyectil proyectil);
    public void eliminarProyectil (Proyectil proyectil);

}
