package UI.Elementos;
// @author Ivan Delgado Huerta

import Constantes.MiscData;
import Geo.GeoBook;
import Geo.Mapa.Celdas.Terreno;
import Graficos.Texto;
import Main.Mundo;
import Resources.Recursos;
import Save.SaveData;
import Skill.Spell.Data.SpellsData;
import UI.UIBook;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;

public class BarraTerrenos extends Group
{
    private Stage stageUI;

    private Table tablaTerrenos;
    private ScrollPane scrollpane;
    
    private Array<CasillaTerreno> barra = new Array<>();
    private Texto[] botonCapas = new Texto[MiscData.MAPA_Max_Capas_Terreno];
    private Texto botonSave;
    private Image botonBorrarTerreno;
    
    private int altoScrollPane= MiscData.TILESIZE*2*10;
    private static int numColumnas = 2;
    
    public static class CasillaTerreno
    {
        public Group apariencia = new Group();
        public String terrenoID;
    }
    
    public BarraTerrenos (Stage stage)
    {
        stageUI = stage;

        tablaTerrenos = new Table().top().left();
        this.setY(Gdx.graphics.getHeight()/2-altoScrollPane/2);

        int fila = 0;
        for (Terreno terreno : GeoBook.get().listaDeTerrenos.values())
        {
            fila++;
            final CasillaTerreno casilla = new CasillaTerreno();

            TextureRegion textura = new TextureRegion(terreno.getTextura(), 0, MiscData.TILESIZE*1, MiscData.TILESIZE*2, MiscData.TILESIZE*2);
            casilla.apariencia.addActor(new Image(textura));
            casilla.terrenoID = terreno.getID();
            //Guardamos todos los datos de cada elemento computado en el array barra, para que si tenemos que hacer cambios, usar estos datos precalculados
            barra.add(casilla);
            //Añadimos la apariencia de cada elemento en la tabla
            tablaTerrenos.add(casilla.apariencia).left().height(MiscData.TILESIZE*2).width(MiscData.TILESIZE*2);
            //Añadimos, en la tabla, una nueva fila cada numColumnas-elementos
            if ((fila+1)%numColumnas == 0) tablaTerrenos.row();
            //A cada icono le activamos un clicklistener, que seleccionara ese tipo de terreno como el activo
            casilla.apariencia.addListener(new InputListener()
            {
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    Mundo.player.setTerrenoSeleccionado(casilla.terrenoID);
                    return true;
                }
            });   
        }

        scrollpane = new ScrollPane(tablaTerrenos);
        scrollpane.setBounds(0, 0, MiscData.TILESIZE*2*numColumnas, altoScrollPane);
        this.addActor(scrollpane);
        
        scrollpane.addListener(new InputListener()
        {   //para que se puede hacer scroll de la barra de terreno con solo pasar el raton por encima:
            @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
            { scrollpane.getStage().setScrollFocus(scrollpane); }
            
            //para que no haga scroll cuando el raton este fuera de la ventana de terrenos
            @Override public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
            { scrollpane.getStage().setScrollFocus(null); } 
            
            //Añdimos un listener para el scroll, para configurar su velocidad, hay que parar su propagacion con event.stop() para que no salte el que esta programado de base
            @Override public boolean scrolled(InputEvent event, float x, float y, int amount)
            { scrollpane.setScrollY(scrollpane.getScrollY()+ MiscData.TILESIZE*3*amount); event.stop(); return true; }
        });
        
        //Selector de CAPAS:
        for (int i=0; i< MiscData.MAPA_Max_Capas_Terreno; i++)
        {   
            final int numCapa = i;
            botonCapas[i] = new Texto("Capa "+numCapa, Recursos.font14, Color.ORANGE, Color.BLACK, 0, 0, Align.left, Align.bottom, 2);
            botonCapas[i].setPosition(4, altoScrollPane+48+17*numCapa);
            this.addActor(botonCapas[i]);
            
            botonCapas[i].addListener(new InputListener()
            {
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {   
                    Mundo.player.setCapaTerrenoSelecionada(numCapa);
                    for (int j=0; j<botonCapas.length; j++) 
                    {   botonCapas[j].setColorNormal(Color.ORANGE); }
                    botonCapas[numCapa].setColorNormal(Color.GREEN);
                    return true;
                }
            });
        }
        botonCapas[0].setColorNormal(Color.GREEN);
        
        //Boton SALVAR matriz:
        botonSave = new Texto("Save", Recursos.font14, Color.WHITE, Color.BLACK, 0, 0, Align.left, Align.bottom, 2);
        botonSave.setPosition(4, altoScrollPane+48+ MiscData.MAPA_Max_Capas_Terreno*17);
        this.addActor(botonSave);
        
        botonSave.addListener(new InputListener()
        {
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {   SaveData.saveMap(GeoBook.get().getMapaControlador().getMapaModel()); return true; }
        });
        
        //Boton BORRAR:
        botonBorrarTerreno = new Image(Recursos.botonBorrarTerreno);
        botonBorrarTerreno.setPosition(0, altoScrollPane);
        this.addActor(botonBorrarTerreno);
        
        botonBorrarTerreno.addListener(new InputListener()
        {
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {   Mundo.player.setTerrenoSeleccionado(""); return true; }
        });
    }
    
    //Para reorganizar la barra en mas columnas:
    public void setNumColumnas(int columnas)
    {
        tablaTerrenos.clear();
        numColumnas = columnas;
                
        for (int i=0; i<barra.size;i++)
        {  
            tablaTerrenos.add(barra.get(i).apariencia).left().height(MiscData.TILESIZE*2).width(MiscData.TILESIZE*2);
            if ((i+1)%columnas == 0) tablaTerrenos.row();
            scrollpane.setBounds(scrollpane.getX(), scrollpane.getY(), MiscData.TILESIZE*2*columnas, altoScrollPane);
        }
    }
    
    //Por si añadimos mas terrenos y hay que recomputar y actualizar todo:
    public void actualizarBarraTerrenos ()
    {
        tablaTerrenos.clear();
        barra.clear();

        int fila =0;
        for (Terreno terreno : GeoBook.get().listaDeTerrenos.values())
        {
            fila++;
            final CasillaTerreno casilla = new CasillaTerreno();

            TextureRegion textura = new TextureRegion(terreno.getTextura(), 0, MiscData.TILESIZE*1, MiscData.TILESIZE*2, MiscData.TILESIZE*2);
            casilla.apariencia.addActor(new Image(textura));
            casilla.terrenoID = terreno.getID();
            
            barra.add(casilla);
            tablaTerrenos.add(casilla.apariencia).left().height(MiscData.TILESIZE*2).width(MiscData.TILESIZE*2);
            
            if ((fila+1)%numColumnas == 0) tablaTerrenos.row();

            casilla.apariencia.addListener(new InputListener()
            {
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    Mundo.player.setTerrenoSeleccionado(casilla.terrenoID);
                    return true;
                }
            });
        }
    }
    
    //Oculta o muestra la barra de Terrenos
    public void mostrarOcultarBarraTerreno ()
    {
        BarraTerrenos barraTerrenos = UIBook.get().getBarraTerrenos();

        if (Mundo.player.getSpellSeleccionado() == SpellsData.TERRAFORMAR_ID)
        {
            if (UIBook.get().mostrarBarraTerrenos == false)
            {
                barraTerrenos.clearActions();
                if (barraTerrenos.getStage() == null)
                {
                    barraTerrenos.setX(-MiscData.TILESIZE*2*numColumnas);
                    stageUI.addActor(barraTerrenos);
                }
               barraTerrenos.addAction(Actions.moveTo(5, barraTerrenos.getY(), 0.5f, Interpolation.sine));
                UIBook.get().mostrarBarraTerrenos = true;
            }
        }
        else
        {
            if (UIBook.get().mostrarBarraTerrenos == true)
            {
                barraTerrenos.clearActions();
                barraTerrenos.addAction(Actions.sequence(Actions.moveTo(-MiscData.TILESIZE * 2 * numColumnas, barraTerrenos.getY(), 0.5f, Interpolation.sine), Actions.removeActor()));
                UIBook.get().mostrarBarraTerrenos = false;
            }    
        }
    }
}
