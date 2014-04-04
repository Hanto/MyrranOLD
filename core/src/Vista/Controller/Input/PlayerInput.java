package Vista.Controller.Input;
// @author Ivan Delgado Huerta

import Main.Mundo;
import UI.Elementos.BarraSpells;
import UI.Elementos.BarraTerrenos;
import UI.UIBook;
import Vista.Controller.Controlador;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class PlayerInput implements InputProcessor
{
    private Controlador controlador;

    //CONSTRUCTOR:
    public PlayerInput (Controlador controlador)
    {   this.controlador = controlador; }

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
                String antiguoSpellSeleccionado = controlador.player.getSpellSeleccionado();
                controlador.player.setSpellSeleccionado(barraspells.barra.get(i).spellID);
                //le ponemos reborde al spell seleccionado y se lo quitamos al que estaba seleccionado:
                barraspells.actualizarApariencia(controlador.player.getSpellSeleccionado());
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
    {
        Mundo.get().player.getEstado().clickPantalla = controlador.convertirCoordenadasPantallaAMundo(screenX, screenY);
        if (button == Buttons.LEFT )    { Mundo.get().player.castear = true; Mundo.get().player.procesarInput(); return false; }
        if (button == Buttons.RIGHT)    { Mundo.get().player.disparar = true; Mundo.get().player.procesarInput(); return false; }
        return true;
    }
    
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        Mundo.get().player.getEstado().clickPantalla = controlador.convertirCoordenadasPantallaAMundo(screenX, screenY);
        if (button == Buttons.LEFT)     { Mundo.get().player.castear = false; Mundo.get().player.procesarInput(); return false; }
        if (button == Buttons.RIGHT)    { Mundo.get().player.disparar = false; Mundo.get().player.procesarInput(); return false; }
        return true;
    }
    
    @Override public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        Mundo.get().player.getEstado().clickPantalla = controlador.convertirCoordenadasPantallaAMundo(screenX, screenY);
        return false;
    }
    @Override public boolean mouseMoved(int screenX, int screenY)
    {
        Mundo.get().player.getEstado().clickPantalla = controlador.convertirCoordenadasPantallaAMundo(screenX, screenY);
        return false;
    }
    @Override public boolean scrolled(int amount)                                           
    { 
        if (amount>0)   { Mundo.get().player.nivelDeZoom++; }
        else            { Mundo.get().player.nivelDeZoom--; }

        controlador.aplicarZoom(Mundo.get().player.nivelDeZoom);
        return false; 
    }
}