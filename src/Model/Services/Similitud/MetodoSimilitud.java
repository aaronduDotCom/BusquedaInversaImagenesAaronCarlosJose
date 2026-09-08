package Model.Services.Similitud;

import Model.Imagen.ImagenData;

public interface MetodoSimilitud {

    double calcular(ImagenData a, ImagenData b);
    boolean esAscendente();
    String getNombre(); // BuscadorInverso sabe cómo ordenar sin preguntar por clases concretas.
}