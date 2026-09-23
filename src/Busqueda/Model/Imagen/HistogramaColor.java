package Busqueda.Model.Imagen;

import java.awt.Color;

import Busqueda.Model.Estructuras.Vector;
import Busqueda.Model.Excepciones.CodigoColorInvalido;

public class HistogramaColor {

    public int evaluarPosBin(int color, int bins) {
        if (color < 0 || color > 255) {
            throw new CodigoColorInvalido("Código de color inválido: " + color);
        }
        if (bins <= 0 || bins > 256) {
            throw new IllegalArgumentException("La cantidad de bins debe estar entre 1 y 256");
        }
        return (color * bins) / 256;
    }

    public Vector<Double> calculaVector(
            Imagen imagen,
            int bins
    ) {
        if (imagen == null || imagen.getImagenBI() == null) {
            throw new IllegalArgumentException("La imagen no puede ser null");
        }

        if (bins <= 0 || bins > 256) {
            throw new IllegalArgumentException("La cantidad de bins debe estar entre 1 y 256");
        }

        long tamannoCalculado = (long) bins * bins * bins;

        if (tamannoCalculado > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("El tamaño del histograma es demasiado grande");
        }

        int tamannoVector = (int) tamannoCalculado;

        Vector<Double> histograma =
                new Vector<>(tamannoVector);

        for (int i = 0; i < tamannoVector; i++) {
            histograma.insertar(0.0);
        }

        int ancho = imagen.getImagenBI().getWidth();
        int alto = imagen.getImagenBI().getHeight();

        for (int fila = 0; fila < alto; fila++) {
            for (int columna = 0; columna < ancho; columna++) {

                int rgb = imagen.getImagenBI().getRGB(columna, fila);
                Color color = new Color(rgb, true);
                int binR = evaluarPosBin(color.getRed(), bins);
                int binG = evaluarPosBin(color.getGreen(), bins);
                int binB = evaluarPosBin(color.getBlue(), bins);
                int posicion = binR * bins * bins + binG * bins + binB;
                double conteoActual = histograma.getPos(posicion);
                histograma.setPos(posicion, conteoActual + 1.0);
            }
        }

        double totalPixeles =
                (double) ancho * alto;

        for (int i = 0; i < tamannoVector; i++) {
            double conteo = histograma.getPos(i);
            histograma.setPos(i, conteo / totalPixeles);
        }

        return histograma;
    }
}