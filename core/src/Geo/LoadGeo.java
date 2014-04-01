package Geo;// Created by Hanto on 31/03/2014.

import Geo.Data.TerrenosData;
import Geo.Mapa.Celdas.Terreno;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LoadGeo
{
    public static void inicializar (TextureAtlas atlas)
    {
        cargarRecursos(atlas);
        cargarTerrenos();
        cargarMuros();
    }

    public static void cargarRecursos(TextureAtlas atlas)
    {
        //TERRENOS:
        GeoBook.get().salvarTexturaTerreno("Tierra4", atlas);

        //TERRENOS y TEXTURA:
        GeoBook.get().salvarTexturaYTerreno("Tierra3", atlas);
        GeoBook.get().salvarTexturaYTerreno("Tierra1", atlas);
        GeoBook.get().salvarTexturaYTerreno("Tierra2", atlas);
        GeoBook.get().salvarTexturaYTerreno("Cesped1", atlas);
        GeoBook.get().salvarTexturaYTerreno("Cesped2", atlas);
        GeoBook.get().salvarTexturaYTerreno("Cesped3", atlas);
        GeoBook.get().salvarTexturaYTerreno("Baldosas3", atlas);
        GeoBook.get().salvarTexturaYTerreno("Baldosas1", atlas);
        GeoBook.get().salvarTexturaYTerreno("Baldosas2", atlas);
        GeoBook.get().salvarTexturaYTerreno("Arena1", atlas);

        //MUROS:
        GeoBook.get().salvarTexturaMuroBaseMedio("MuroBase", atlas);
        GeoBook.get().salvarTexturaMuroBaseMedio("MuroMedio", atlas);
        GeoBook.get().salvarTexturaMuroBaseTecho("MuroTecho", atlas);
    }

    public static void cargarTerrenos()
    {
        Terreno terreno = new Terreno();
        terreno.setId(TerrenosData.T001_ID);
        terreno.setNombre(TerrenosData.T001_Nombre);
        terreno.setColor(TerrenosData.T001_Color);
        terreno.setIsSolido(TerrenosData.T001_isSolido);
        terreno.setTextura(GeoBook.get().listaDeTexturasTerreno.get(TerrenosData.T001_ID));
        GeoBook.get().añadirTerreno(terreno);
    }

    public static void cargarMuros()
    {
        GeoBook.get().salvarMuro("Muro Standard", "MuroBase", "MuroMedio", "MuroTecho");
    }
}
