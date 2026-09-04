package Model.Imagen;

// tiene el vector y todo....

import Model.Estructuras.Vector;

import java.util.UUID;

public class ImagenData {
    private UUID id;
    private Vector<Integer> vector;

    public ImagenData(Vector<Integer> v,UUID otra){
        vector = v;
        id = otra;
    }

    public Vector<Integer> getVector() {return vector;}
}
