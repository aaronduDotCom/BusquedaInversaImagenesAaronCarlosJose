package Model.Services.Similitud;

import Model.Imagen.ImagenData;

public class SimilitudCoseno implements MetodoSimilitud {

    @Override
    public double calcular(ImagenData a, ImagenData b) {
        // Entre más cerca de 1, mejor
        //
        //         A · B
        //   ----------------
        //    ||A|| * ||B||

        validarImagenes(a, b);

        // A · B
        double productoEscalar = 0;

        // Sumatorias necesarias para calcular
        // las normas de A y B
        double sumaCuadradosA = 0;
        double sumaCuadradosB = 0;

        for (int i = 0; i < 64; i++) {
            double valorA = a.getVector().getPos(i);
            double valorB = b.getVector().getPos(i);
            // Producto escalar A · B
            productoEscalar += valorA * valorB;
            // ||A||²
            sumaCuadradosA += valorA * valorA;
            // ||B||²
            sumaCuadradosB += valorB * valorB;
        }

        // ||A||
        double normaA = Math.sqrt(sumaCuadradosA);

        // ||B||
        double normaB = Math.sqrt(sumaCuadradosB);

        // ||A|| * ||B||
        double denominador = normaA * normaB;

        //   ----------------

        if (denominador == 0) {
            throw new ArithmeticException(
                    "No se puede calcular la similitud coseno "
                            + "con un vector nulo"
            );
        }

        return productoEscalar / denominador;
    }

    @Override
    public boolean esAscendente() {
        // Entre mayor sea el coseno,
        // mayor es la similitud
        return false;
    }

    @Override
    public String getNombre() {
        return "Similitud Coseno";
    }

    private void validarImagenes(ImagenData a, ImagenData b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException(
                    "Las imágenes no pueden ser null"
            );
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