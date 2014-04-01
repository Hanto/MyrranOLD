package Save;
// @author Ivan Delgado Huerta

import Geo.Mapa.MVC.MapaModel;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SaveData 
{    
    public static void saveMap(MapaModel mapaModel)
    {
        Kryo kryo = new Kryo();      
        
        try 
        {
            Output output = new Output(new FileOutputStream(mapaModel.getNombre()+".bin"));
            kryo.writeObject(output, mapaModel.matriz);
            output.close();
        }
        catch (FileNotFoundException ex) { System.out.println("Fichero de MapaView no encontrado"); }
        
    }
    
    public static MapaModel loadMap(String mapID)
    {
        Kryo kryo = new Kryo();

        MapaModel mapaModel = new MapaModel(mapID);

        try 
        {
            Input input = new Input(new FileInputStream(mapID+".bin"));

            mapaModel.matriz = kryo.readObject(input, mapaModel.matriz.getClass()); // = kryo.readObject(input, Mundo.get().getMapaView().getClass());
            input.close();
            return mapaModel;
        }
        catch (FileNotFoundException ex) { System.out.println("Fichero de MapaView no encontrado"); return null; }
    }
    
}
