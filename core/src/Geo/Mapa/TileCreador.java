package Geo.Mapa;// Created by Hanto on 31/03/2014.

import Constantes.MiscData;
import Geo.GeoBook;
import Geo.Mapa.Celdas.Celda;
import Geo.Mapa.Celdas.Terreno;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static Constantes.MiscData.TILESIZE;

public class TileCreador
{
    public TextureRegion cuadranteNO;
    private boolean NOizquierda = false;
    private boolean NOdiagonal = false;
    private boolean NOarriba = false;
    public TextureRegion cuadranteNE;
    private boolean NEarriba = false;
    private boolean NEdiagonal = false;
    private boolean NEderecha = false;
    public TextureRegion cuadranteSE;
    private boolean SEderecha = false;
    private boolean SEdiagonal = false;
    private boolean SEabajo = false;
    public TextureRegion cuadranteSO;
    private boolean SOabajo = false;
    private boolean SOdiagonal = false;
    private boolean SOizquierda = false;

    public TileCreador(int X, int Y, int numCapa, Celda[][] mapa)   { generarTextura(X, Y, numCapa, mapa);}

    public void generarTextura(int X, int Y, int capa, Celda[][] mapa)
    {
        String numTerreno = mapa[X][Y].getTerrenoID()[capa];
        //if (numTerreno) return;
        //Buscamos que terreno tiene esa celda
        Terreno terreno = GeoBook.get().listaDeTerrenos.get(numTerreno);

        calcularAdyacencias(X, Y, mapa, capa);
        generarTexturaNO(terreno);
        generarTexturaNE(terreno);
        generarTexturaSO(terreno);
        generarTexturaSE(terreno);
    }

    private void calcularAdyacencias (int X, int Y, Celda[][] mapa, int capa)
    {
        //Calculamos las adyacendias en todas las direcciones con terrenos de su mismo tipo
        if      (Y+1 >= MiscData.MAPA_Max_Y)                { NOarriba = false; NEarriba = false; }
        else if (mapa[X][Y].getTerrenoID()[capa].equals
                (mapa[X][Y + 1].getTerrenoID()[capa]))      { NOarriba = true; NEarriba = true; }

        if      (Y-1 < 0)                                   { SOabajo = false; SEabajo = false; }
        else if (mapa[X][Y].getTerrenoID()[capa].equals
                (mapa[X][Y-1].getTerrenoID()[capa]))        { SOabajo = true; SEabajo = true; }

        if      (X-1 < 0)                                   { NOizquierda = false; SOizquierda = false; }
        else if (mapa[X][Y].getTerrenoID()[capa].equals
                (mapa[X-1][Y].getTerrenoID()[capa]))        { NOizquierda = true; SOizquierda = true; }

        if      (X+1 >= MiscData.MAPA_Max_X)                { NEderecha = false; SEderecha = false; }
        else if (mapa[X][Y].getTerrenoID()[capa].equals
                (mapa[X+1][Y].getTerrenoID()[capa]))        { NEderecha = true; SEderecha = true; }

        if      (X+1 >= MiscData.MAPA_Max_X ||
                 Y+1 >= MiscData.MAPA_Max_Y)                { NEdiagonal = false; }

        else if (mapa[X][Y].getTerrenoID()[capa].equals
                (mapa[X+1][Y+1].getTerrenoID()[capa]))      { NEdiagonal = true; }

        if      (X-1<0 || Y-1<0)                            { SOdiagonal = false; }

        else if (mapa[X][Y].getTerrenoID()[capa].equals
                (mapa[X-1][Y-1].getTerrenoID()[capa]))      { SOdiagonal = true; }

        if      (X-1 <0 || Y+1 >= MiscData.MAPA_Max_Y)      { NOdiagonal = false; }

        else if (mapa[X][Y].getTerrenoID()[capa].equals
                (mapa[X-1][Y+1].getTerrenoID()[capa]))      { NOdiagonal = true; }

        if      (X+1 >= MiscData.MAPA_Max_X || Y-1<0)       { SEdiagonal = false; }

        else if (mapa[X][Y].getTerrenoID()[capa].equals
                (mapa[X+1][Y-1].getTerrenoID()[capa]))      { SEdiagonal = true; }
    }

    private void generarTexturaNO(Terreno terreno)
    {
        int cuadrante = TILESIZE/2;

        if (  NOizquierda &&  NOdiagonal &&  NOarriba ) { cuadranteNO = new TextureRegion(terreno.getTextura(), cuadrante*2, cuadrante*4, cuadrante, cuadrante); }
        if (  NOizquierda &&  NOdiagonal && !NOarriba ) { cuadranteNO = new TextureRegion(terreno.getTextura(), cuadrante*2, cuadrante*2, cuadrante, cuadrante); }
        if (  NOizquierda && !NOdiagonal &&  NOarriba ) { cuadranteNO = new TextureRegion(terreno.getTextura(), cuadrante*2, cuadrante*0, cuadrante, cuadrante); }
        if (  NOizquierda && !NOdiagonal && !NOarriba ) { cuadranteNO = new TextureRegion(terreno.getTextura(), cuadrante*2, cuadrante*2, cuadrante, cuadrante); }
        if ( !NOizquierda &&  NOdiagonal &&  NOarriba ) { cuadranteNO = new TextureRegion(terreno.getTextura(), cuadrante*0, cuadrante*4, cuadrante, cuadrante); }
        if ( !NOizquierda &&  NOdiagonal && !NOarriba ) { cuadranteNO = new TextureRegion(terreno.getTextura(), cuadrante*0, cuadrante*2, cuadrante, cuadrante); }
        if ( !NOizquierda && !NOdiagonal &&  NOarriba ) { cuadranteNO = new TextureRegion(terreno.getTextura(), cuadrante*0, cuadrante*4, cuadrante, cuadrante); }
        if ( !NOizquierda && !NOdiagonal && !NOarriba ) { cuadranteNO = new TextureRegion(terreno.getTextura(), cuadrante*0, cuadrante*2, cuadrante, cuadrante); }
    }

    private void generarTexturaNE(Terreno terreno)
    {
        int cuadrante = TILESIZE/2;

        if (  NEderecha &&  NEdiagonal &&  NEarriba )   { cuadranteNE = new TextureRegion(terreno.getTextura(), cuadrante*1, cuadrante*4, cuadrante, cuadrante); }
        if (  NEderecha &&  NEdiagonal && !NEarriba )   { cuadranteNE = new TextureRegion(terreno.getTextura(), cuadrante*1, cuadrante*2, cuadrante, cuadrante); }
        if (  NEderecha && !NEdiagonal &&  NEarriba )   { cuadranteNE = new TextureRegion(terreno.getTextura(), cuadrante*3, cuadrante*0, cuadrante, cuadrante); }
        if (  NEderecha && !NEdiagonal && !NEarriba )   { cuadranteNE = new TextureRegion(terreno.getTextura(), cuadrante*1, cuadrante*2, cuadrante, cuadrante); }
        if ( !NEderecha &&  NEdiagonal &&  NEarriba )   { cuadranteNE = new TextureRegion(terreno.getTextura(), cuadrante*3, cuadrante*4, cuadrante, cuadrante); }
        if ( !NEderecha &&  NEdiagonal && !NEarriba )   { cuadranteNE = new TextureRegion(terreno.getTextura(), cuadrante*3, cuadrante*2, cuadrante, cuadrante); }
        if ( !NEderecha && !NEdiagonal &&  NEarriba )   { cuadranteNE = new TextureRegion(terreno.getTextura(), cuadrante*3, cuadrante*4, cuadrante, cuadrante); }
        if ( !NEderecha && !NEdiagonal && !NEarriba )   { cuadranteNE = new TextureRegion(terreno.getTextura(), cuadrante*3, cuadrante*2, cuadrante, cuadrante); }
    }

    private void generarTexturaSO(Terreno terreno)
    {
        int cuadrante = TILESIZE/2;

        if (  SOizquierda &&  SOdiagonal &&  SOabajo )  { cuadranteSO = new TextureRegion(terreno.getTextura(), cuadrante*2, cuadrante*3, cuadrante, cuadrante); }
        if (  SOizquierda &&  SOdiagonal && !SOabajo )  { cuadranteSO = new TextureRegion(terreno.getTextura(), cuadrante*2, cuadrante*5, cuadrante, cuadrante); }
        if (  SOizquierda && !SOdiagonal &&  SOabajo )  { cuadranteSO = new TextureRegion(terreno.getTextura(), cuadrante*2, cuadrante*1, cuadrante, cuadrante); }
        if (  SOizquierda && !SOdiagonal && !SOabajo )  { cuadranteSO = new TextureRegion(terreno.getTextura(), cuadrante*2, cuadrante*5, cuadrante, cuadrante); }
        if ( !SOizquierda &&  SOdiagonal &&  SOabajo )  { cuadranteSO = new TextureRegion(terreno.getTextura(), cuadrante*0, cuadrante*3, cuadrante, cuadrante); }
        if ( !SOizquierda &&  SOdiagonal && !SOabajo )  { cuadranteSO = new TextureRegion(terreno.getTextura(), cuadrante*0, cuadrante*5, cuadrante, cuadrante); }
        if ( !SOizquierda && !SOdiagonal &&  SOabajo )  { cuadranteSO = new TextureRegion(terreno.getTextura(), cuadrante*0, cuadrante*3, cuadrante, cuadrante); }
        if ( !SOizquierda && !SOdiagonal && !SOabajo )  { cuadranteSO = new TextureRegion(terreno.getTextura(), cuadrante*0, cuadrante*5, cuadrante, cuadrante); }
    }

    private void generarTexturaSE(Terreno terreno)
    {
        int cuadrante = TILESIZE/2;

        if (  SEderecha &&  SEdiagonal &&  SEabajo )    { cuadranteSE = new TextureRegion(terreno.getTextura(), cuadrante*1, cuadrante*3, cuadrante, cuadrante); }
        if (  SEderecha &&  SEdiagonal && !SEabajo )    { cuadranteSE = new TextureRegion(terreno.getTextura(), cuadrante*1, cuadrante*5, cuadrante, cuadrante); }
        if (  SEderecha && !SEdiagonal &&  SEabajo )    { cuadranteSE = new TextureRegion(terreno.getTextura(), cuadrante*3, cuadrante*1, cuadrante, cuadrante); }
        if (  SEderecha && !SEdiagonal && !SEabajo )    { cuadranteSE = new TextureRegion(terreno.getTextura(), cuadrante*1, cuadrante*5, cuadrante, cuadrante); }
        if ( !SEderecha &&  SEdiagonal &&  SEabajo )    { cuadranteSE = new TextureRegion(terreno.getTextura(), cuadrante*3, cuadrante*3, cuadrante, cuadrante); }
        if ( !SEderecha &&  SEdiagonal && !SEabajo )    { cuadranteSE = new TextureRegion(terreno.getTextura(), cuadrante*3, cuadrante*5, cuadrante, cuadrante); }
        if ( !SEderecha && !SEdiagonal &&  SEabajo )    { cuadranteSE = new TextureRegion(terreno.getTextura(), cuadrante*3, cuadrante*3, cuadrante, cuadrante); }
        if ( !SEderecha && !SEdiagonal && !SEabajo )    { cuadranteSE = new TextureRegion(terreno.getTextura(), cuadrante*3, cuadrante*5, cuadrante, cuadrante); }
    }
}
