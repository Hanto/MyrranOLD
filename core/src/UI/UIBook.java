package UI;// Created by Hanto on 01/04/2014.

import Constantes.MiscData;
import UI.Elementos.BarraSpells;
import UI.Elementos.BarraTerrenos;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class UIBook
{
    private volatile static UIBook ui;

    private BarraSpells barraSpells;
    private BarraTerrenos barraTerrenos;
    public boolean mostrarBarraTerrenos = false;

    public BarraSpells getBarraSpells ()                { return barraSpells; }
    public BarraTerrenos getBarraTerrenos ()            { return barraTerrenos; }

    public void setBarraSpells (BarraSpells barra)      { barraSpells = barra; }
    public void setBarraTerrenos (BarraTerrenos barra)  { barraTerrenos = barra; }

    private UIBook () {}
    public static UIBook get()
    {
        if (ui == null)
        synchronized (UIBook.class)
        {
            if (ui == null)
                ui = new UIBook();
        }
        return ui;
    }

    //Crea todas las barras de UI necesarias y las añade al STAGE del UI:
    public void inicializar(Stage stageUI)
    {
        barraTerrenos = new BarraTerrenos(stageUI);
        barraTerrenos.setNumColumnas(2);

        barraSpells = new BarraSpells(2,9);
        barraSpells.setPosition(MiscData.WINDOW_Horizontal_Resolution/2-barraSpells.getWidth()/2, 5);
        stageUI.addActor(barraSpells);
    }

}
