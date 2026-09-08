package Model.Imagen;

// tiene el vector y todo....

import Model.Estructuras.Vector;
import java.util.UUID;

public class ImagenData {
    private UUID id;
    private Vector<Double> vector;
    private String ruta;

    public ImagenData(Vector<Double> vector, UUID id, String ruta) {
        this.vector = vector;
        this.id = id;
        this.ruta = ruta;
    }

    public UUID getId() {
        return id;
    }

    public String getRuta() {
        return ruta;
    }

    public Vector<Double> getVector() {return vector;}
}
