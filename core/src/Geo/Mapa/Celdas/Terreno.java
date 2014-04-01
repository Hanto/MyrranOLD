package Geo.Mapa.Celdas;

import Constantes.MiscData;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.UUID;

/**
 * @author Ivan Delgado Huerta
 */

//Clase que define los distintos tipos de terreno
public class Terreno 
{
    private String id;                              //ID del tipo de Terreno para poder cargar su template
    private String nombre;                          //Nombre del Terreno ("Jungla", "Camino", "Cesped"...)
    private Color color;                            //Color que se le asigna en su representacion en el minimapa
    private TextureRegion textura;                  //Textura que contiene el grafico del terreno
    private boolean isSolido = false;               //Flag que controla si el terreno es solido o no, y por tanto atravesable por los objetos que pueblan el mundo
    
    //SET:
    public void setId (String s)                    { id = s ; }
    public void setNombre (String s)                { nombre = s; }
    public void setColor (Color c)                  { color = c; }
    public void setTextura (TextureRegion texture)  { textura = texture; }
    public void setIsSolido (boolean b)             { isSolido = b; }
    //GET:
    public String getID()                           { return id; }
    public String getNombre()                       { return nombre; }
    public Color getColor()                         { return color; }
    public TextureRegion getTextura()               { return textura; }
    public boolean getIsSolido()                    { return isSolido; }
        
    public Terreno ()
    {   //Si no hay ningun terreno creado su ID es el 0, si ya existe, cogemos el ultimo ID de la lista y le asignamos el siguiente
        UUID idUUID = UUID.randomUUID();
        id = idUUID.toString();
        nombre = MiscData.TERRENO_Nombre_Nuevo+"_"+id;
        isSolido = false;
    }
    public Terreno (String id)
    {
        this.id = id;
        nombre = id;
        isSolido = false;
    }
}
