package Busqueda.Model.Services.Similitud;

import Busqueda.Model.Excepciones.ValidacionImagen;
import Busqueda.Model.Imagen.ImagenData;

public class SimilitudCoseno implements MetodoSimilitud {

    @Override
    public double calcular(
            ImagenData a,
            ImagenData b,
            int bins
    ) {
        validarImagenes(a, b, bins);

        double productoEscalar = 0;
        double sumaCuadradosA = 0;
        double sumaCuadradosB = 0;

        int tamannoVector = a.getVector().tamanno();

        for (int i = 0; i < tamannoVector; i++) {
            double valorA = a.getVector().getPos(i);
            double valorB = b.getVector().getPos(i);

            productoEscalar += valorA * valorB;
            sumaCuadradosA += valorA * valorA;
            sumaCuadradosB += valorB * valorB;
        }

        double normaA = Math.sqrt(sumaCuadradosA);
        double normaB = Math.sqrt(sumaCuadradosB);

        double denominador = normaA * normaB;

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
        return false;
    }

    @Override
    public String getNombre() {
        return "Similitud Coseno";
    }

    @Override
    public void validarImagenes(
            ImagenData a,
            ImagenData b,
            int bins
    ) {
        if (a == null || b == null) {
            throw new ValidacionImagen(
                    "Las imágenes no pueden ser null"
            );
        }

        if (a.getVector() == null || b.getVector() == null) {
            throw new ValidacionImagen(
                    "Los vectores no pueden ser null"
            );
        }

        int tamannoA = a.getVector().tamanno();
        int tamannoB = b.getVector().tamanno();

        if (tamannoA != tamannoB) {
            throw new ValidacionImagen(
                    "Los vectores deben tener el mismo tamaño. "
                            + "Consulta: "
                            + tamannoA
                            + ", banco: "
                            + tamannoB
            );
        }

        if (tamannoA == 0) {
            throw new ValidacionImagen(
                    "Los histogramas no pueden estar vacíos"
            );
        }
    }
}