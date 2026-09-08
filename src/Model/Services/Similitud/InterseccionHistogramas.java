package Model.Services.Similitud;

import Model.Imagen.ImagenData;

public class InterseccionHistogramas implements MetodoSimilitud {

    @Override
    public double calcular(ImagenData a, ImagenData b) {
        //     n
        //     E      min(A_i, B_i)
        //     i = 0

        validarImagenes(a, b);

        // Sumatoria
        double resultado = 0;

        for (int i = 0; i < 64; i++) {
            // Sacamos el mínimo de ambos histogramas
            resultado += Math.min(a.getVector().getPos(i), b.getVector().getPos(i)
            );
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

    private void validarImagenes(ImagenData a, ImagenData b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Las imágenes no pueden ser null");
        }
        if (a.getVector() == null || b.getVector() == null) {
            throw new IllegalArgumentException("Los vectores no pueden ser null");
        }
        if (a.getVector().tamanno() != b.getVector().tamanno()) {
            throw new IllegalArgumentException("Los vectores deben tener el mismo tamaño");
        }
        if (a.getVector().tamanno() != 64) {
            throw new IllegalArgumentException("Los histogramas deben tener 64 posiciones");
        }
    }
}