package Geo.Mapa.MVC;// Created by Hanto on 01/04/2014.

public class MapaControlador
{
    private MapaView mapaView;
    private MapaModel mapaModel;

    public MapaView getMapaView ()                  { return mapaView; }
    public MapaModel getMapaModel()                 { return mapaModel; }

    public MapaControlador (MapaModel map)
    {
        mapaModel = map;
        mapaView = new MapaView(this, mapaModel);
        mapaView.crearTiledMap();
        mapaView.crearMuros();
    }

    public void setTerreno (int x, int y, int numCapa, String iDTerreno)
    {
        mapaModel.setTerreno(x, y, numCapa, iDTerreno);
        mapaView.crearTile(x, y, numCapa);
    }

    public void crearMuro (int x, int y, String iDMuro)
    {
        if (mapaModel.setMuro(x, y, iDMuro)) mapaView.crearMuro(x, y, iDMuro);
    }

}
