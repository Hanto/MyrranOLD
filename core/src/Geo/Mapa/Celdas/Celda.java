/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Geo.Mapa.Celdas;

import Constantes.MiscData;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**w
 * @author Ivan Delgado Huerta
 */
public class Celda implements KryoSerializable
{
    private String [] terrenoID = new String[MiscData.MAPA_Max_Capas_Terreno];
    private String muroID = "";
    public Muro muro;
    
    //SET
    public void setMuroId (String muroId)   { muroID = muroId; }
    //GET:
    public String [] getTerrenoID()         { return terrenoID; }
    public String getMuroID()               { return muroID; }

    //CONSTRUCTOR:
    public Celda ()
    {
        for (int i=0; i<terrenoID.length; i++)
            terrenoID[i] = "";
    }
    
    public Celda(Celda celdaOrigen)
    {
        for (int i=0; i<celdaOrigen.terrenoID.length;i++)
        {   terrenoID[i] = celdaOrigen.terrenoID[i];}
        muroID = celdaOrigen.muroID;
    }

    //KryoSerializable:
    @Override public void write(Kryo kryo, Output output)
    {
        for (int i=0;i<MiscData.MAPA_Max_Capas_Terreno;i++)
            output.writeString(terrenoID[i]);
        output.writeString(muroID);
    }

    @Override public void read(Kryo kryo, Input input)
    {
        for (int i=0;i<MiscData.MAPA_Max_Capas_Terreno;i++)
            terrenoID[i] = input.readString();
        muroID = input.readString();
    }    
}
