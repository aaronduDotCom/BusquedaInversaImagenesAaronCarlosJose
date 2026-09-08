package Model.Repositorios;

import Model.Estructuras.ColeccionImagenData;
import Model.Imagen.ImagenData;

public class RepoImagenes {

    private static RepoImagenes instancia;
    ColeccionImagenData coleccion = new ColeccionImagenData();

    private RepoImagenes() {
        coleccion = new ColeccionImagenData();
    }

    public static RepoImagenes getInstance() {
        if (instancia == null) {
            instancia = new RepoImagenes();
        }

        return instancia;
    }

    public void agregar(ImagenData imagenData) {
        coleccion.insertarFinal(imagenData);
    }

    public ColeccionImagenData obtenerImagenes() {
        return coleccion;
    }

    public void reemplazar(
            ColeccionImagenData nuevaColeccion
    ) {
        if (nuevaColeccion == null) {
            coleccion = new ColeccionImagenData();
        } else {
            coleccion = nuevaColeccion;
        }
    }

    public void limpiar() {
        coleccion = new ColeccionImagenData();
    }

    public int cantidad() {
        return coleccion.tamano();
    }
}