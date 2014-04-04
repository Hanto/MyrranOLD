package Save;
// @author Ivan Delgado Huerta

import Geo.Mapa.Celdas.Mapa;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SaveData 
{    
    public static void saveMap(Mapa mapa)
    {
        Kryo kryo = new Kryo();      
        
        try 
        {
            Output output = new Output(new FileOutputStream(mapa.getNombre()+".bin"));
            kryo.writeObject(output, mapa.getMatriz());
            output.close();
        }
        catch (FileNotFoundException ex) { System.out.println("Fichero de MapaView no encontrado"); }
        
    }
    
    public static Mapa loadMap(String mapID)
    {
        Kryo kryo = new Kryo();

        Mapa mapa = new Mapa(mapID);

        try 
        {
            Input input = new Input(new FileInputStream(mapID+".bin"));

            mapa.setMatriz(kryo.readObject(input, mapa.getMatriz().getClass())); // = kryo.readObject(input, Mundo.get().getMapaView().getClass());
            input.close();
            return mapa;
        }
        catch (FileNotFoundException ex) { System.out.println("Fichero de MapaView no encontrado"); return null; }
    }
    
}
