package Vista.View;// Created by Hanto on 02/04/2014.

import Constantes.MiscData;
import Geo.Mapa.TileCreador;
import Resources.Recursos;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class TiledMapVista extends TiledMap
{
    private Vista vista;

    public TiledMapVista (Vista vista)
    {
        this.vista = vista;
        crearTiledMap();
    }

    public void inicializar()
    {
        borrarTodosLosLayers();
        crearTiledMap();
    }

    public void crearTiledMap()
    {
        TiledMapTileLayer.Cell cell;
        StaticTiledMapTile tileNO;
        StaticTiledMapTile tileNE;
        StaticTiledMapTile tileSO;
        StaticTiledMapTile tileSE;

        for (int numCapa=0; numCapa< MiscData.MAPA_Max_Capas_Terreno; numCapa++)
        {
            TiledMapTileLayer suelo = new TiledMapTileLayer(MiscData.MAPA_Max_X*2, MiscData.MAPA_Max_Y*2, MiscData.TILESIZE/2, MiscData.TILESIZE/2);
            for (int x = 0; x < MiscData.MAPA_Max_X; x++)
            {
                for (int y = 0; y < MiscData.MAPA_Max_Y; y++)
                {   //si contiene un ID a un terreno valido cargamos su textura, si no, lo ignoramos
                    if (vista.mapa.getMatriz()[x][y].getTerrenoID()[numCapa].length()>0)
                    {
                        TileCreador tileCreador = new TileCreador(x, y, numCapa, vista.mapa.getMatriz());

                        tileNO = new StaticTiledMapTile(tileCreador.cuadranteNO);
                        tileNE = new StaticTiledMapTile(tileCreador.cuadranteNE);
                        tileSO = new StaticTiledMapTile(tileCreador.cuadranteSO);
                        tileSE = new StaticTiledMapTile(tileCreador.cuadranteSE);

                        cell = new TiledMapTileLayer.Cell();
                        cell.setTile(tileNO);
                        suelo.setCell(x*2, y*2+1, cell);

                        cell = new TiledMapTileLayer.Cell();
                        cell.setTile(tileNE);
                        suelo.setCell(x*2+1, y*2+1, cell);

                        cell = new TiledMapTileLayer.Cell();
                        cell.setTile(tileSO);
                        suelo.setCell(x*2, y*2, cell);

                        cell = new TiledMapTileLayer.Cell();
                        cell.setTile(tileSE);
                        suelo.setCell(x*2+1, y*2, cell);
                    }
                }
            }
            getLayers().add(suelo);
        }
        setRenderGrid(vista.renderGrid);
    }

    private void borrarTodosLosLayers ()
    {
        while (getLayers().getCount()>0)
            getLayers().remove(0);
    }

    //Cuando se crea un Tile se modifican los circundantes tambien, por eso hay que regenerar todos los que esten
    //a su alrededor cuando modificamos uno
    public void crearTile (int x, int y, int numCapa)
    {
        crearSubTile(x - 1, y - 1, numCapa);
        crearSubTile(x - 1, y + 0, numCapa);
        crearSubTile(x - 1, y + 1, numCapa);
        crearSubTile(x + 0, y - 1, numCapa);
        crearSubTile(x + 0, y + 0, numCapa);
        crearSubTile(x + 0, y + 1, numCapa);
        crearSubTile(x + 1, y - 1, numCapa);
        crearSubTile(x + 1, y + 0, numCapa);
        crearSubTile(x + 1, y + 1, numCapa);
    }

    private void crearSubTile (int x, int y, int numCapa)
    {
        if (x<0 || y<0 || x> MiscData.MAPA_Max_X || y> MiscData.MAPA_Max_Y) { return; }

        String numTerreno = vista.mapa.getMatriz()[x][y].getTerrenoID()[numCapa];
        if (numTerreno.length()<=0) borrarTile(x, y, numCapa );
        else
        {
            TileCreador tileCreador = new TileCreador(x, y, numCapa, vista.mapa.getMatriz());

            StaticTiledMapTile tileNO = new StaticTiledMapTile(tileCreador.cuadranteNO);
            StaticTiledMapTile tileNE = new StaticTiledMapTile(tileCreador.cuadranteNE);
            StaticTiledMapTile tileSO = new StaticTiledMapTile(tileCreador.cuadranteSO);
            StaticTiledMapTile tileSE = new StaticTiledMapTile(tileCreador.cuadranteSE);

            TiledMapTileLayer suelo = (TiledMapTileLayer)getLayers().get(numCapa);
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(tileNO);
            suelo.setCell(x*2, y*2+1, cell);

            cell = new TiledMapTileLayer.Cell();
            cell.setTile(tileNE);
            suelo.setCell(x*2+1, y*2+1, cell);

            cell = new TiledMapTileLayer.Cell();
            cell.setTile(tileSO);
            suelo.setCell(x*2, y*2, cell);

            cell = new TiledMapTileLayer.Cell();
            cell.setTile(tileSE);
            suelo.setCell(x*2+1, y*2, cell);
        }
    }

    private void borrarTile (int x, int y, int numCapa)
    {
        TiledMapTileLayer suelo = (TiledMapTileLayer)getLayers().get(numCapa);
        suelo.setCell(x*2, y*2+1, null);
        suelo.setCell(x*2+1, y*2+1, null);
        suelo.setCell(x*2, y*2, null);
        suelo.setCell(x*2+1, y*2, null);
    }

    public void setRenderGrid (boolean verGrid)
    {
        if ( verGrid && !vista.renderGrid)   { addGrid(); vista.renderGrid = true; }
        if (!verGrid &&  vista.renderGrid)   { removeGrid(); vista.renderGrid = false; }
    }

    private void addGrid ()
    {
        TiledMapTileLayer layerGrid = new TiledMapTileLayer(MiscData.MAPA_Max_X*2, MiscData.MAPA_Max_Y*2, MiscData.TILESIZE, MiscData.TILESIZE);
        StaticTiledMapTile grid = new StaticTiledMapTile(Recursos.grid);

        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        for (int x = 0; x < MiscData.MAPA_Max_X*2; x++)
        {
            for (int y = 0; y < MiscData.MAPA_Max_Y*2; y++)
            {
                cell.setTile(grid);
                layerGrid.setCell(x, y, cell);
            }
        }
        layerGrid.setName("LayerGrid");
        getLayers().add(layerGrid);

    }

    private void removeGrid ()
    {
        MapLayer layerGrid = getLayers().get("LayerGrid");
        getLayers().remove(layerGrid);
    }
}
