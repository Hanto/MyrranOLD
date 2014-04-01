package Geo.Mapa.MVC;

import Constantes.MiscData;
import Geo.GeoBook;
import Geo.Mapa.Celdas.Muro;
import Geo.Mapa.MapObserver.MapaObservador;
import Geo.Mapa.TileCreador;
import Main.Mundo;
import Resources.Recursos;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author Ivan Delgado Huerta
 */

public class MapaView extends TiledMap implements MapaObservador
{
    private MapaModel mapaModel;                          //MapaModel del que extrae los Datos
    private MapaControlador mapaControlador;
    private Stage stage;
    private World world;
    private Actor player;

    public MapaView (MapaControlador mapControler, MapaModel map)
    {
        mapaModel = map;
        mapaControlador = mapControler;
        mapaModel.registrarObservador(this);
    }

    public void crearTiledMap()
    {
        Cell cell;
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
                    if (mapaModel.matriz[x][y].getTerrenoID()[numCapa].length()>0)
                    {
                        TileCreador tileCreador = new TileCreador(x, y, numCapa, mapaModel.matriz);

                        tileNO = new StaticTiledMapTile(tileCreador.cuadranteNO);
                        tileNE = new StaticTiledMapTile(tileCreador.cuadranteNE);
                        tileSO = new StaticTiledMapTile(tileCreador.cuadranteSO);
                        tileSE = new StaticTiledMapTile(tileCreador.cuadranteSE);

                        cell = new Cell();
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
        if (false)
        {
            TiledMapTileLayer layerGrid = new TiledMapTileLayer(MiscData.MAPA_Max_X*2, MiscData.MAPA_Max_Y*2, MiscData.TILESIZE, MiscData.TILESIZE);
            StaticTiledMapTile grid = new StaticTiledMapTile(Recursos.grid);
            cell = new Cell();

            for (int x = 0; x < MiscData.MAPA_Max_X*2; x++)
            {
                for (int y = 0; y < MiscData.MAPA_Max_Y*2; y++)
                {   //si contiene un ID a un terreno valido cargamos su textura, si no, lo ignoramos
                    cell.setTile(grid);
                    layerGrid.setCell(x, y, cell);
                }
            }
            getLayers().add(layerGrid);
        }
    }

    public void crearMuros ()
    {
        Muro muro;
        String muroID;

        for (int x = 0; x < MiscData.MAPA_Max_X; x++)
        {
            for (int y = 0; y < MiscData.MAPA_Max_Y; y++)
            {
                muroID = mapaModel.matriz[x][y].getMuroID();
                if (muroID.length() >0)
                {
                    muro = new Muro(GeoBook.get().listaDeMuros.get(muroID));
                    muro.setPosition(x*MiscData.TILESIZE, y*MiscData.TILESIZE);
                    muro.crearMuro(Mundo.get().getStageMundo(), Mundo.get().getWorld(), Mundo.player.getActor());
                }
            }
        }
    }

    public void crearMuro (int x, int y, String iDMuro)
    {
        Muro muro = new Muro(GeoBook.get().listaDeMuros.get(iDMuro));
        muro.setPosition(x*MiscData.TILESIZE, y*MiscData.TILESIZE);
        muro.crearMuro(Mundo.get().getStageMundo(), Mundo.get().getWorld(), Mundo.player.getActor());

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

    public void crearSubTile (int x, int y, int numCapa)
    {
        if (x<0 || y<0 || x> MiscData.MAPA_Max_X || y> MiscData.MAPA_Max_Y) { return; }
        
        String numTerreno = mapaModel.matriz[x][y].getTerrenoID()[numCapa];
        if (numTerreno.length()<=0) borrarTile(x, y, numCapa );
        else
        {
            TileCreador tileCreador = new TileCreador(x, y, numCapa, mapaModel.matriz);

            StaticTiledMapTile tileNO = new StaticTiledMapTile(tileCreador.cuadranteNO);
            StaticTiledMapTile tileNE = new StaticTiledMapTile(tileCreador.cuadranteNE);
            StaticTiledMapTile tileSO = new StaticTiledMapTile(tileCreador.cuadranteSO);
            StaticTiledMapTile tileSE = new StaticTiledMapTile(tileCreador.cuadranteSE);

            TiledMapTileLayer suelo = (TiledMapTileLayer)getLayers().get(numCapa);
            Cell cell = new Cell();
            cell.setTile(tileNO);
            suelo.setCell(x*2, y*2+1, cell);

            cell = new Cell();
            cell.setTile(tileNE);
            suelo.setCell(x*2+1, y*2+1, cell);

            cell = new Cell();
            cell.setTile(tileSO);
            suelo.setCell(x*2, y*2, cell);

            cell = new Cell();
            cell.setTile(tileSE);
            suelo.setCell(x*2+1, y*2, cell);
        }
    }
    
    public void borrarTile (int x, int y, int numCapa)
    {
        TiledMapTileLayer suelo = (TiledMapTileLayer)getLayers().get(numCapa);
        suelo.setCell(x*2, y*2+1, null);
        suelo.setCell(x*2+1, y*2+1, null);
        suelo.setCell(x*2, y*2, null);
        suelo.setCell(x*2+1, y*2, null);
    }

    @Override public void actualizarTerreno(int x, int y, int numCapa)
    {   crearTile(x, y, numCapa); }
}




//TextureRegion terreno.getTextura()Suelo = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"Suelo"));
//TextureRegion terreno.getTextura()Suelo2 = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"Suelo2"));
//StaticTiledMapTile tile = new StaticTiledMapTile(terreno.getTextura()Suelo);
//StaticTiledMapTile tile2 = new StaticTiledMapTile(terreno.getTextura()Suelo2);
//Array<StaticTiledMapTile> aTileArray = new Array<>(2);
//aTileArray.add(tile);
//aTileArray.add(tile2);
//AnimatedTiledMapTile aTile = new AnimatedTiledMapTile(0.3f, aTileArray);