package Model.Imagen;

// tiene el vector y todo....

import Model.Estructuras.Vector;

public class ImagenData {
    //private String id;
    private Vector<Integer> vector;

    public ImagenData(Vector<Integer> v){
        vector = v;
    }

    public Vector<Integer> getVector() {return vector;}
}
