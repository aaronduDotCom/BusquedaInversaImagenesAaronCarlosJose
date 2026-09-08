package Model.Imagen;

import Model.Estructuras.Vector;
import Model.Estructuras.ColeccionImagen;
import Model.Estructuras.ColeccionImagenData;
import Model.Estructuras.Iterator;

// por cada imagen en coleccion crea una imagen data y lo mete a coleccion de imagenes data y la devuelve, se lo pasamos a buscador inverso utilizando los metodos
// histogramaColor

public class AbstractorImagenes {
    private HistogramaColor hc;

    public AbstractorImagenes(){
        hc = new HistogramaColor();
    }

    public ColeccionImagenData abstractarImagenes(ColeccionImagen imagenes){

        ColeccionImagenData cid = new ColeccionImagenData();
        Iterator<Imagen> iterador = imagenes.getIterador();

        while (iterador.hasNext()){
            Imagen imagen = iterador.next();
            //creamos el vector de la nueva imagen data, a hc le pasamos la imagen y se crea el vector
            Vector<Double> hv = hc.calculaVector(imagen);

            //creamos la nueva imagen
            ImagenData iD = new ImagenData(hv, imagen.getId(), imagen.getRuta());


            //la insertamos en la lista de imagenesData
            cid.insertarFinal(iD);
        }

        return cid;
    }
}
