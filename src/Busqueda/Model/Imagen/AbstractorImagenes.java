package Busqueda.Model.Imagen;

import Busqueda.Model.Estructuras.Vector;
import Busqueda.Model.Estructuras.ColeccionImagen;
import Busqueda.Model.Estructuras.ColeccionImagenData;
import Busqueda.Model.Estructuras.Iterator;

// por cada imagen en coleccion crea una imagen data y lo mete a coleccion de imagenes data y la devuelve, se lo pasamos a buscador inverso utilizando los metodos
// histogramaColor

public class AbstractorImagenes {
    private HistogramaColor hc;
    private int bins;

    public AbstractorImagenes(int bins){
        hc = new HistogramaColor();
        this.bins = bins;
    }

    public ColeccionImagenData abstractarImagenes(ColeccionImagen imagenes){

        ColeccionImagenData cid = new ColeccionImagenData();
        Iterator<Imagen> iterador = imagenes.getIterador();

        while (iterador.hasNext()){
            Imagen imagen = iterador.next();
            //creamos el vector de la nueva imagen data, a hc le pasamos la imagen y se crea el vector
            Vector<Double> hv = hc.calculaVector(imagen,bins);

            //creamos la nueva imagen
            ImagenData iD = new ImagenData(hv, imagen.getId(), imagen.getRuta());


            //la insertamos en la lista de imagenesData
            cid.insertarFinal(iD);
        }

        return cid;
    }
}
