package Actores.Mobs.Personajes.PCs;

import Actores.Mobs.Personajes.PC;
import Constantes.MiscData;
import MobileEstados.Player.PlayerEstado;
import Vista.Model.Player.PlayerModel;
import Vista.Model.Player.PlayerObservador;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

//* @author Ivan Delgado Huerta
// Player representa de todos los personajes controlados por el jugador, el que esta siendo usado localmente desde esta maquina
public class Player extends PC implements PlayerModel
{
    private Array<PlayerObservador> listaObservadores = new Array<>();

    public int nivelDeZoom = 0;
    
    protected PlayerEstado estado;
    public int numAnimacion = 0;
    public boolean castear = false;
    public boolean disparar = false;
    public boolean irArriba = false;
    public boolean irAbajo = false;
    public boolean irIzquierda = false;
    public boolean irDerecha = false;
    
    public boolean rumboNorte = false;
    public boolean rumboSur = false;
    public boolean rumboEste = false;
    public boolean rumboOeste = false;
    
    //Teclas de Direccion para mover al personaje, remapeables:
    protected int teclaArriba = Keys.W;
    protected int teclaAbajo = Keys.S;
    protected int teclaIzquierda = Keys.A;
    protected int teclaDerecha = Keys.D;
    
    public void procesarInput()                 { estado.procesarInput(); }
    public PlayerEstado getEstado()             { return estado; }
    
    //CONSTRUCTOR:
    public Player(final int numRaza, int posX, int posY, String nombre)
    {   super(numRaza, nombre);
        inicializar (posX, posY);
        estado = new PlayerEstado(this);
    }
        
    private void inicializar (int posX, int posY)
    {   velocidadMax = MiscData.PLAYER_VelocidadMax_Pixeles_Sec;
        setPosition (posX, posY);
        notificarPlayerPositicion(posX, posY);
    }
    
    public void setPosition (float X, float Y)
    {   x = X; y = Y;
        actor.setPosition((int)X, (int)Y);
        notificarPlayerPositicion((int) X, (int) Y);
    }
    public void setX (float X)
    {   x = X;
        actor.setX((int)X);
        notificarPlayerPositicion((int) x, (int) y);
    }
    public void setY (float Y)
    {   y = Y;
        actor.setY((int)Y);
        notificarPlayerPositicion((int) x, (int) y);
    }
    
    public void moverse (float delta)
    {   //Sur
        if (rumboSur && !rumboEste && !rumboOeste)      
        { setY((float)(y+ -(Math.sin(Math.toRadians(90d))*velocidadMax)*velocidadMod*delta)); }
        //Norte
        else if (rumboNorte && !rumboEste && !rumboOeste)
        { setY((float)(y+ -(Math.sin(Math.toRadians(270d))*velocidadMax)*velocidadMod*delta)); }
        //Este
        else if (rumboEste && !rumboNorte && !rumboSur)       
        { setX((float)(x+ (Math.cos(Math.toRadians(0d))*velocidadMax)*velocidadMod*delta)); }
        //Oeste
        else if (rumboOeste && !rumboNorte && !rumboSur)       
        { setX((float)(x+ (Math.cos(Math.toRadians(180d))*velocidadMax)*velocidadMod*delta)); }
        //SurOeste
        else if (rumboSur && rumboOeste)   
        { setY((float)(y+ -(Math.sin(Math.toRadians(135d))*velocidadMax)*velocidadMod*delta));
          setX((float)(x+ (Math.cos(Math.toRadians(135d))*velocidadMax)*velocidadMod*delta)); }
        //SurEste
        else if (rumboSur && rumboEste)   
        { setY((float)(y+ -(Math.sin(Math.toRadians(45d))*velocidadMax)*velocidadMod*delta));
          setX((float)(x+ (Math.cos(Math.toRadians(45d))*velocidadMax)*velocidadMod*delta)); }
        //NorOeste
        else if (rumboNorte && rumboOeste)   
        { setY((float)(y+ -(Math.sin(Math.toRadians(225d))*velocidadMax)*velocidadMod*delta));
          setX((float)(x+ (Math.cos(Math.toRadians(225d))*velocidadMax)*velocidadMod*delta)); }
        //NorEste
        else if (rumboNorte && rumboEste)   
        { setY((float)(y+ -(Math.sin(Math.toRadians(315d))*velocidadMax)*velocidadMod*delta));
          setX((float)(x+ (Math.cos(Math.toRadians(315d))*velocidadMax)*velocidadMod*delta)); }
    }
    
    public void actualizarCastingTime(float delta)
    {   //Actualizamos el cooldown de casteo:
        if ( isCasteando )
        {
            actualCastingTime = actualCastingTime + delta;
            notificarCastingTimePercent(getCastingTimePercent());
        }
        if ( isCasteando && actualCastingTime >= totalCastingTime)
        {
            actualCastingTime = 0;
            totalCastingTime =0;
            isCasteando = false;
            notificarPlayerIsCasteando(isCasteando);
            notificarCastingTimePercent(getCastingTimePercent());
        }
    }

    public void setCastingTime(float totalCastingTime)
    {
        super.setCastingTime(totalCastingTime);
        notificarPlayerIsCasteando(isCasteando);
    }
    
    public void castear ()
    {
        if (castear && !isCasteando && spellSeleccionado.length() > 0) { estado.procesarInput(); }
    }
    
    public void actualizar (float delta)
    {   
        estado.actualizar();
        moverse(delta);
        actualizarCastingTime (delta);
        castear();
    }

    @Override public void modificarHPs (int HPs, Color color)
    {
        super.modificarHPs(HPs, color);
        notificarHPsPercent(this.getHPsPercent());
        notificarModificarHPs(HPs, color);
    }

    @Override public void añadirObservador(PlayerObservador observador)
    {   listaObservadores.add(observador); }

    @Override public void eliminarObservador(PlayerObservador observador)
    {   listaObservadores.add(observador); }

    public void notificarPlayerPositicion(int x, int y)
    {
        for (PlayerObservador observador: listaObservadores)
            observador.playerPosition(x, y);
    }

    public void notificarPlayerIsCasteando(boolean isCasteando)
    {
        for (PlayerObservador observador: listaObservadores)
            observador.playerIsCasteando(isCasteando);
    }

    public void notificarCastingTimePercent(float castingTimePercent)
    {
        for (PlayerObservador observador: listaObservadores)
            observador.playerCastingTimePercent(castingTimePercent);
    }

    public void notificarHPsPercent (float HpsPercent)
    {
        for (PlayerObservador observador: listaObservadores)
            observador.playerHPsPercent(HpsPercent);
    }

    public void notificarModificarHPs (int HPs, Color color)
    {
        for (PlayerObservador observador: listaObservadores)
            observador.playerModifacionHPs(HPs, color);
    }
}