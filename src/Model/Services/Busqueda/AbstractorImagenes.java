package Model.Services.Busqueda;

import Model.Estructuras.Vector;

import Model.Estructuras.ColeccionImagen;
import Model.Estructuras.ColeccionImagenData;
import Model.Imagen.ImagenData;

// por cada imagen en coleccion crea una imagen data y lo mete a coleccion de imagenes data y la devuelve, se lo pasamos a buscador inverso utilizando los metodos
// histogramaColor

public class AbstractorImagenes {
    private HistogramaColor hc;

    public AbstractorImagenes(){
        hc = new HistogramaColor();
    }

    public ColeccionImagenData AbstractarImagenes(ColeccionImagen imagenes){
        ColeccionImagenData cid = new ColeccionImagenData();

        while (imagenes.getIterador().hasNext()){
            //creamos el vector de la nueva imagen data, a hc le pasamos la imagen y se crea el vector
            Vector<Integer> hv = hc.calculaVector(imagenes.getIterador().next());

            //creamos la nueva imagen
            ImagenData iD = new ImagenData(hv);

            //la insertamos en la lista de imagenesData
            cid.insertarInicio(iD);
        }

        return cid;
    }
}
