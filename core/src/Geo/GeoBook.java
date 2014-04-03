package Geo;// Created by Hanto on 31/03/2014.

import Constantes.MiscData;
import Geo.Mapa.Celdas.Muro;
import Geo.Mapa.Celdas.Terreno;
import Geo.Mapa.MVC.Mapa;
import Geo.Mapa.MVC.MapaControlador;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class GeoBook
{
    private volatile static GeoBook geoBook;

    private MapaControlador mapaControlador;

    //Terrenos y Muros
    public HashMap<String, Mapa> listaDeMapas = new HashMap<>();
    public HashMap<String, Terreno> listaDeTerrenos = new HashMap<>();
    public HashMap<String, Muro> listaDeMuros = new HashMap<>();

    //Resources:
    public HashMap<String, TextureRegion> listaDeTexturasTerreno = new HashMap<>();
    public HashMap<String, TextureRegion> listaDeTexturasMuroBaseMedio = new HashMap<>();
    public HashMap<String, TextureRegion> listaDeTexturasMuroTecho = new HashMap<>();

    public MapaControlador getMapaControlador()             { return mapaControlador; }
    public void setMapaControlador (MapaControlador mapaC)  { mapaControlador = mapaC; }

    public void añadirTerreno (Terreno terreno)             { listaDeTerrenos.put(terreno.getID(), terreno); }

    //CONSTRUCTOR:
    private GeoBook() {}
    public static GeoBook get()
    {
        if (geoBook == null)
            synchronized (GeoBook.class)
            {
                if (geoBook == null)
                    geoBook = new GeoBook();
            }
        return geoBook;
    }

    public void inicializar (TextureAtlas atlas)
    {
        LoadGeo.inicializar(atlas);
    }

    public void salvarTexturaTerreno (String iDTerreno, TextureAtlas atlas)
    {
        try
        {
            TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_Terrenos_LOC + iDTerreno));
            listaDeTexturasTerreno.put(iDTerreno, texture);
        }
        catch (Exception e) {System.out.println("ERROR Textura Terreno: ["+iDTerreno+"] no encontrada."); }
    }

    public void salvarTexturaMuroBaseMedio (String iDMuro, TextureAtlas atlas)
    {
        try
        {
            TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_Muros_LOC + iDMuro));
            listaDeTexturasMuroBaseMedio.put(iDMuro, texture);
        }
        catch (Exception e) {System.out.println("ERROR Textura Muro BaseMedio: ["+iDMuro+"] no encontrada."); }
    }

    public void salvarTexturaMuroBaseTecho (String iDMuro, TextureAtlas atlas)
    {
        try
        {
            TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_Muros_LOC + iDMuro));
            listaDeTexturasMuroTecho.put(iDMuro, texture);
        }
        catch (Exception e) {System.out.println("ERROR Textura Muro Techo: ["+iDMuro+"] no encontrada."); }
    }

    public void salvarTexturaYTerreno (String iDTerreno, TextureAtlas atlas)
    {
        try
        {
            TextureRegion texture = new TextureRegion(atlas.findRegion(MiscData.ATLAS_Terrenos_LOC + iDTerreno));
            listaDeTexturasTerreno.put(iDTerreno, texture);
            Terreno terreno = new Terreno();
            terreno.setId(iDTerreno);
            terreno.setNombre(iDTerreno);
            terreno.setColor(Color.GRAY);
            terreno.setIsSolido(false);
            terreno.setTextura(texture);
            añadirTerreno(terreno);
        }
        catch (Exception e) {System.out.println("ERROR Textura: ["+iDTerreno+"] no encontrada."); }
    }

    public void salvarMuro (String iDMuro, String iDMuroBase, String iDMuroMedio, String iDMuroTecho)
    {
        TextureRegion texturaBase = listaDeTexturasMuroBaseMedio.get(iDMuroBase);
        TextureRegion texturaMedio = listaDeTexturasMuroBaseMedio.get(iDMuroMedio);
        TextureRegion texturaTecho = listaDeTexturasMuroTecho.get(iDMuroTecho);
        Muro muro = new Muro( iDMuro, texturaBase, texturaMedio, texturaTecho);
        listaDeMuros.put(iDMuro, muro);
    }
}
