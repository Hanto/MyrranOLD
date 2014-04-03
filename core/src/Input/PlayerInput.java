package Input;
// @author Ivan Delgado Huerta

import Main.Mundo;
import UI.Elementos.BarraSpells;
import UI.Elementos.BarraTerrenos;
import UI.UIBook;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import static Pantallas.AbstractPantalla.camara;

public class PlayerInput implements InputProcessor
{
    @Override
    public boolean keyDown(int keycode) 
    {
        BarraSpells barraspells = UIBook.get().getBarraSpells();
        BarraTerrenos barraTerrenos = UIBook.get().getBarraTerrenos();
                
        for (int i=0;i<barraspells.barra.size;i++)
        { if (barraspells.barra.get(i).keycode == keycode) 
            {
                //si el bind no contiene ningun spell que no pase nada:
                if (barraspells.barra.get(i).spellID.equals("")) break;
                String antiguoSpellSeleccionado = Mundo.get().player.getSpellSeleccionado();
                Mundo.get().player.setSpellSeleccionado(barraspells.barra.get(i).spellID);
                //le ponemos reborde al spell seleccionado y se lo quitamos al que estaba seleccionado:
                barraspells.actualizarApariencia(Mundo.get().player.getSpellSeleccionado());
                barraspells.actualizarApariencia(antiguoSpellSeleccionado);
                //Si seleccionamos el spell EditarTerreno tenemos que mostrar la barra de Terrenos:
                //Y si seleccionamos otro spell y la barra esta mostrada, hay que ocultarla:
                barraTerrenos.mostrarOcultarBarraTerreno();
            }                                               
        }
        
        switch (keycode)
        {
            case Keys.W:    Mundo.get().player.irArriba = true; break;
            case Keys.S:    Mundo.get().player.irAbajo = true; break;
            case Keys.A:    Mundo.get().player.irIzquierda = true; break;
            case Keys.D:    Mundo.get().player.irDerecha = true; break;
        }
        Mundo.get().player.procesarInput();
        return false;
    }
    
    @Override public boolean keyUp(int keycode)                                             
    { 
        switch (keycode)
        {
            case Keys.W:    {Mundo.get().player.irArriba = false; break; }
            case Keys.S:    {Mundo.get().player.irAbajo = false; break;}
            case Keys.A:    {Mundo.get().player.irIzquierda = false; break;}
            case Keys.D:    {Mundo.get().player.irDerecha = false; break;}
        }
        Mundo.get().player.procesarInput();
        return false;
    }
    
    @Override public boolean keyTyped(char character)                                       { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button)   
    {   if (button == Buttons.LEFT )    { Mundo.get().player.castear = true; Mundo.get().player.procesarInput(); return false; }
        if (button == Buttons.RIGHT)    { Mundo.get().player.disparar = true; Mundo.get().player.procesarInput(); return false; }
        return true;
    }
    
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button)     
    {   if (button == Buttons.LEFT)     { Mundo.get().player.castear = false; Mundo.get().player.procesarInput(); return false; }
        if (button == Buttons.RIGHT)    { Mundo.get().player.disparar = false; Mundo.get().player.procesarInput(); return false; }
        return true;
    }
    
    @Override public boolean touchDragged(int screenX, int screenY, int pointer)            { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY)                           { return false; }
    @Override public boolean scrolled(int amount)                                           
    { 
        if (amount>0)   { Mundo.get().player.nivelDeZoom++; }
        else            { Mundo.get().player.nivelDeZoom--; }
        
        switch (Mundo.get().player.nivelDeZoom)
        {
            case 0:     camara.zoom = 1.0f; break;
            case -1:    camara.zoom = 1/2f; break;
            case -2:    camara.zoom = 1/3f; break;
            case -3:    camara.zoom = 1/4f; break;
            case -4:    camara.zoom = 1/5f; break;
            case -5:    camara.zoom = 1/6f; break;
            case -6:    camara.zoom = 1/7f; break;
            case -7:    camara.zoom = 1/8f; break;
            case -8:    camara.zoom = 1/9f; break;
            case -9:    camara.zoom = 1/10f; break;
            case -10:   camara.zoom = 1/11f; break;
        }
        if (Mundo.get().player.nivelDeZoom >0 ) Mundo.get().player.nivelDeZoom = 0;
        if (Mundo.get().player.nivelDeZoom <-10) Mundo.get().player.nivelDeZoom = -10;
        return false; 
    }
}