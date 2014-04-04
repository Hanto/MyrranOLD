package Vista.Model.Mapa;// Created by Hanto on 02/04/2014.

public interface MapaModel extends MapaView
{
    //SET:
    public boolean setTerreno (int x, int y, int numCapa, String iDTerreno);
    public boolean setMuro (int x, int y, String iDMuro);
}