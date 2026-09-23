package Busqueda.Model.Imagen;

import java.awt.Color;

import Busqueda.Model.Estructuras.Vector;
import Busqueda.Model.Excepciones.CodigoColorInvalido;

public class HistogramaColor {

    public int evaluarPosBin(int color, int bins) {
        if (color < 0 || color > 255) {
            throw new CodigoColorInvalido(
                    "Código de color inválido: " + color
            );
        }

        if (bins <= 0 || bins > 256) {
            throw new IllegalArgumentException(
                    "La cantidad de bins debe estar entre 1 y 256"
            );
        }

        return (color * bins) / 256;
    }

    public Vector<Double> calculaVector(Imagen imagen, int bins) {
        if (imagen == null || imagen.getImagenBI() == null) {
            throw new IllegalArgumentException(
                    "La imagen no puede ser null"
            );
        }

        if (bins <= 0 || bins > 256) {
            throw new IllegalArgumentException(
                    "La cantidad de bins debe estar entre 1 y 256"
            );
        }

        int tamannoVector = bins * bins * bins;

        Vector<Integer> vectorConteos =
                new Vector<>(tamannoVector);

        // Inicializar todas las posiciones en cero
        for (int i = 0; i < tamannoVector; i++) {
            vectorConteos.insertar(0);
        }

        int width = imagen.getImagenBI().getWidth();
        int height = imagen.getImagenBI().getHeight();

        for (int fila = 0; fila < height; fila++) {
            for (int columna = 0; columna < width; columna++) {

                int rgb = imagen
                        .getImagenBI()
                        .getRGB(columna, fila);

                Color color = new Color(rgb, true);

                int binR = evaluarPosBin(
                        color.getRed(),
                        bins
                );

                int binG = evaluarPosBin(
                        color.getGreen(),
                        bins
                );

                int binB = evaluarPosBin(
                        color.getBlue(),
                        bins
                );

                int posicion =
                        binR * bins * bins
                                + binG * bins
                                + binB;

                int conteoActual =
                        vectorConteos.getPos(posicion);

                vectorConteos.setPos(
                        posicion,
                        conteoActual + 1
                );
            }
        }

        Vector<Double> vectorNormalizado =
                new Vector<>(tamannoVector);

        double totalPixeles = (double) width * height;

        for (int i = 0; i < tamannoVector; i++) {
            double conteo = vectorConteos.getPos(i);

            vectorNormalizado.insertar(
                    conteo / totalPixeles
            );
        }

        return vectorNormalizado;
    }
}