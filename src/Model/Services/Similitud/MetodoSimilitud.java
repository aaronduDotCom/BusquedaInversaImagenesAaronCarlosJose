package Model.Services.Similitud;

import Model.Excepciones.ValidacionImagen;
import Model.Imagen.ImagenData;

public interface MetodoSimilitud {

    double calcular(ImagenData a, ImagenData b, int bins);
    boolean esAscendente();
    String getNombre(); // BuscadorInverso sabe cómo ordenar sin preguntar por clases concretas.

    public void validarImagenes(ImagenData a, ImagenData b, int bins);
}