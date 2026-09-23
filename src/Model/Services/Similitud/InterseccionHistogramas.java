package Model.Services.Similitud;

import Model.Excepciones.ValidacionImagen;
import Model.Imagen.ImagenData;

public class InterseccionHistogramas implements MetodoSimilitud {

    @Override
    public double calcular(ImagenData a, ImagenData b, int bins) {
        //     n
        //     E      min(A_i, B_i)
        //     i = 0

        validarImagenes(a, b, bins);

        // Sumatoria
        double resultado = 0;

        for (int i = 0; i < bins; i++) {
            // Sacamos el mínimo de ambos histogramas
            resultado += Math.min(a.getVector().getPos(i), b.getVector().getPos(i));
        }

        return resultado;
    }

    @Override
    public boolean esAscendente() {
        // Entre mayor sea la intersección,
        // mayor es la similitud
        return false;
    }

    @Override
    public String getNombre() {
        return "Intersección de Histogramas";
    }

    @Override
    public void validarImagenes(ImagenData a, ImagenData b, int bins) {
        if (a == null || b == null) {
            throw new ValidacionImagen(
                    "Las imágenes no pueden ser null"
            );
        }

        if (a.getVector() == null || b.getVector() == null) {
            throw new ValidacionImagen("Los vectores no pueden ser null");
        }

        if (a.getVector().tamanno() != b.getVector().tamanno()) {
            throw new ValidacionImagen("Los vectores deben tener el mismo tamaño");
        }

        if (a.getVector().tamanno() != bins) {
            throw new ValidacionImagen("Los histogramas deben tener " + bins +" posiciones");
        }
    }
}