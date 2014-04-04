package Geo.Mapa.MVC;

import Constantes.MiscData;
import Geo.GeoBook;
import Geo.Mapa.Celdas.Mapa;
import Geo.Mapa.Celdas.Muro;
import Geo.Mapa.TileCreador;
import Resources.Recursos;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**
 * @author Ivan Delgado Huerta
 */

//Clase que representa la Vista del Mapa, se encarga unicamente de renderizar los datos contenidos en el Mapa
public class MapaView
{
    private Mapa mapa;                              //Mapa del que extrae los Datos
    private MapaControlador mapaControlador;        //Clase a traves de la cual se interactuan con los datos
                                                    //y a la vez los cambios se ven reflejos en el view
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer; //Clase que se encarga de Renderizar el TiledMap
    private Stage stageMundo;
    private World world;                            //Stage, world y actor Player para poder crear entidades Estaticas
    private RayHandler rayHandler;

    private Actor player;                           //como los muros, cuyo comportamiento depende de la posicion del player
    public PointLight luzPlayer;

    private boolean renderGrid = false;             //Si queremos ver un grid que separe los Tiles

    public TiledMap getTiledMap ()                          { return tiledMap; }
    public OrthogonalTiledMapRenderer getMapRenderer()      { return mapRenderer; }
    public Stage getStageMundo()                            { return stageMundo; }
    public World getWorld()                                 { return world; }
    public RayHandler getRayHandler()                       { return rayHandler; }

    public MapaView (MapaControlador mapControler, Mapa map, Actor player)
    {
        this.mapa = map;
        this.mapaControlador = mapControler;

        this.tiledMap = new TiledMap();
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.world = new World(new Vector2(0, -9.8f), false);
        this.stageMundo = new Stage();
        this.rayHandler = new RayHandler(world);
        this.luzPlayer = new PointLight(rayHandler, 300, new Color(0.3f,0.3f,0.3f,1.0f), 350, 0, 0);

        this.player = player;

        crearTiledMap();
        crearMuros();

        RayHandler.useDiffuseLight(true);
    }

    public void setMapa(Mapa mapa)
    {
        this.mapa = mapa;

        borrarTodosLosLayers();
        crearTiledMap();
        crearMuros();
    }

    private void crearTiledMap()
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
                    if (mapa.getMatriz()[x][y].getTerrenoID()[numCapa].length()>0)
                    {
                        TileCreador tileCreador = new TileCreador(x, y, numCapa, mapa.getMatriz());

                        tileNO = new StaticTiledMapTile(tileCreador.cuadranteNO);
                        tileNE = new StaticTiledMapTile(tileCreador.cuadranteNE);
                        tileSO = new StaticTiledMapTile(tileCreador.cuadranteSO);
                        tileSE = new StaticTiledMapTile(tileCreador.cuadranteSE);

                        cell = new Cell();
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
            }
            tiledMap.getLayers().add(suelo);
        }
        setRenderGrid(renderGrid);
    }

    private void borrarTodosLosLayers ()
    {
        while (tiledMap.getLayers().getCount()>0)
            tiledMap.getLayers().remove(0);
    }

    public void setRenderGrid (boolean verGrid)
    {
        if ( verGrid && !renderGrid)   { addGrid(); renderGrid = true; }
        if (!verGrid &&  renderGrid)   { removeGrid(); renderGrid = false; }
    }

    private void addGrid ()
    {
        TiledMapTileLayer layerGrid = new TiledMapTileLayer(MiscData.MAPA_Max_X*2, MiscData.MAPA_Max_Y*2, MiscData.TILESIZE, MiscData.TILESIZE);
        StaticTiledMapTile grid = new StaticTiledMapTile(Recursos.grid);

        Cell cell = new Cell();
        for (int x = 0; x < MiscData.MAPA_Max_X*2; x++)
        {
            for (int y = 0; y < MiscData.MAPA_Max_Y*2; y++)
            {
                cell.setTile(grid);
                layerGrid.setCell(x, y, cell);
            }
        }
        layerGrid.setName("LayerGrid");
        tiledMap.getLayers().add(layerGrid);

    }

    private void removeGrid ()
    {
        MapLayer layerGrid = tiledMap.getLayers().get("LayerGrid");
        tiledMap.getLayers().remove(layerGrid);
    }

    private void crearMuros ()
    {
        Muro muro;
        String muroID;

        for (int x = 0; x < MiscData.MAPA_Max_X; x++)
        {
            for (int y = 0; y < MiscData.MAPA_Max_Y; y++)
            {
                muroID = mapa.getMatriz()[x][y].getMuroID();
                if (muroID.length() >0)
                {
                    muro = new Muro(GeoBook.get().listaDeMuros.get(muroID));
                    muro.setPosition(x*MiscData.TILESIZE, y*MiscData.TILESIZE);
                    muro.crearMuro(world, player);
                }
            }
        }
    }

    public void crearMuro (int x, int y, String iDMuro)
    {
        Muro muro = new Muro(GeoBook.get().listaDeMuros.get(iDMuro));
        muro.setPosition(x*MiscData.TILESIZE, y*MiscData.TILESIZE);
        muro.crearMuro(world, player);

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
        
        String numTerreno = mapa.getMatriz()[x][y].getTerrenoID()[numCapa];
        if (numTerreno.length()<=0) borrarTile(x, y, numCapa );
        else
        {
            TileCreador tileCreador = new TileCreador(x, y, numCapa, mapa.getMatriz());

            StaticTiledMapTile tileNO = new StaticTiledMapTile(tileCreador.cuadranteNO);
            StaticTiledMapTile tileNE = new StaticTiledMapTile(tileCreador.cuadranteNE);
            StaticTiledMapTile tileSO = new StaticTiledMapTile(tileCreador.cuadranteSO);
            StaticTiledMapTile tileSE = new StaticTiledMapTile(tileCreador.cuadranteSE);

            TiledMapTileLayer suelo = (TiledMapTileLayer)tiledMap.getLayers().get(numCapa);
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
    
    private void borrarTile (int x, int y, int numCapa)
    {
        TiledMapTileLayer suelo = (TiledMapTileLayer)tiledMap.getLayers().get(numCapa);
        suelo.setCell(x*2, y*2+1, null);
        suelo.setCell(x*2+1, y*2+1, null);
        suelo.setCell(x*2, y*2, null);
        suelo.setCell(x*2+1, y*2, null);
    }
}




//TextureRegion terreno.getTextura()Suelo = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"Suelo"));
//TextureRegion terreno.getTextura()Suelo2 = new TextureRegion (Recursos.atlas.findRegion(MiscData.ATLAS_Terrenos_LOC+"Suelo2"));
//StaticTiledMapTile tile = new StaticTiledMapTile(terreno.getTextura()Suelo);
//StaticTiledMapTile tile2 = new StaticTiledMapTile(terreno.getTextura()Suelo2);
//Array<StaticTiledMapTile> aTileArray = new Array<>(2);
//aTileArray.add(tile);
//aTileArray.add(tile2);
//AnimatedTiledMapTile aTile = new AnimatedTiledMapTile(0.3f, aTileArray);