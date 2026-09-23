package Busqueda.Model.Services.Similitud;

import Busqueda.Model.Excepciones.ValidacionImagen;
import Busqueda.Model.Imagen.ImagenData;

public class InterseccionHistogramas implements MetodoSimilitud {

    @Override
    public double calcular(ImagenData a, ImagenData b, int bins) {

        validarImagenes(a, b, bins);

        double resultado = 0;

        // El histograma tiene bins³ posiciones
        int tamannoVector = a.getVector().tamanno();

        for (int i = 0; i < tamannoVector; i++) {
            resultado += Math.min(
                    a.getVector().getPos(i),
                    b.getVector().getPos(i)
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
                            + "Vector A: " + tamannoA
                            + ", vector B: " + tamannoB
            );
        }

        int tamannoEsperado = calcularTamannoEsperado(bins);

        if (tamannoA != tamannoEsperado) {
            throw new ValidacionImagen(
                    "Con " + bins
                            + " bins, los histogramas deben tener "
                            + tamannoEsperado
                            + " posiciones, pero tienen "
                            + tamannoA
            );
        }
    }

    private int calcularTamannoEsperado(int bins) {
        if (bins <= 0 || bins > 256) {
            throw new ValidacionImagen(
                    "La cantidad de bins debe estar entre 1 y 256"
            );
        }

        return bins * bins * bins;
    }
}