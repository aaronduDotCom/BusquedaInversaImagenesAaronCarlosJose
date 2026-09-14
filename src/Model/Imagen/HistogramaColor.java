package Model.Imagen;

import java.awt.Color;

import Model.Estructuras.Vector;
import Model.Excepciones.CodigoColorInvalido;

//saca la info de una imagen y devuelve los resultados para crear una imagenData

public class HistogramaColor {

    // El TAMANNO DEL BIN es cuantos valores de la escala 0-255 caen en cada cubeta.
    // De ahi sale la cantidad de bins por canal: 256 / tamannoBin
    // y el tamanno del vector caracteristico: (256/tamannoBin)^3
    //
    //   tamanno bin | bins por canal | posiciones del vector
    //        256    |       1        |          1
    //        128    |       2        |          8
    //         64    |       4        |         64   <- el que se usaba antes
    //         32    |       8        |        512
    //         16    |      16        |      4,096
    //          8    |      32        |     32,768
    //
    // Tamannos menores a 8 son validos matematicamente pero generan vectores
    // enormes (tamanno 1 -> 16.7 millones de posiciones por imagen), por eso
    // no se ofrecen en la interfaz.
    public static final int[] TAMANNOS_BIN_VALIDOS = {256, 128, 64, 32, 16, 8};
    public static final int TAMANNO_BIN_POR_DEFECTO = 64;

    private final int tamannoBin;    // cuantos valores de 0-255 caen en cada bin
    private final int bins;          // bins por canal = 256 / tamannoBin
    private final int tamannoVector; // bins^3

    public HistogramaColor() {
        this(TAMANNO_BIN_POR_DEFECTO);
    }

    public HistogramaColor(int tamannoBin) {
        if (!esTamannoBinValido(tamannoBin)) {
            throw new IllegalArgumentException(
                    "Tamanno de bin invalido: " + tamannoBin
                            + ". Valores permitidos: 8, 16, 32, 64, 128, 256");
        }
        this.tamannoBin = tamannoBin;
        this.bins = 256 / tamannoBin;
        this.tamannoVector = bins * bins * bins;
    }

    public static boolean esTamannoBinValido(int tamannoBin) {
        for (int valido : TAMANNOS_BIN_VALIDOS) {
            if (valido == tamannoBin) {
                return true;
            }
        }
        return false;
    }

    // Cuantas posiciones tendra el vector para un tamanno de bin dado.
    // Sirve para mostrarle al usuario el costo antes de escoger.
    public static int calcularTamannoVector(int tamannoBin) {
        int b = 256 / tamannoBin;
        return b * b * b;
    }

    public int getTamannoBin() {
        return tamannoBin;
    }

    public int getBins() {
        return bins;
    }

    public int getTamannoVector() {
        return tamannoVector;
    }

    public int evaluarPosBin(int color){
        if (color < 0 || color > 255) {
            throw new CodigoColorInvalido("codigo color invalido: " + color);
        }
        return color / tamannoBin;
    }

    //Este metodo se usa una vez por imagen
    public Vector<Double> calculaVector(Imagen imagen){
        Vector<Integer> vc = new Vector<>(tamannoVector); // vc = vector conteos
        //Inicializamos en 0s
        for (int i = 0; i < tamannoVector; i++) {
            vc.insertar(0);
        }

        //Tomamos ancho y altura del objeto imagen
        int width = imagen.getImagenBI().getWidth();
        int height = imagen.getImagenBI().getHeight();

        //recorremos todos los pixeles de la imagen
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                int rgb = imagen.getImagenBI().getRGB(j, i); // x = columna (j), y = fila (i)
                Color color = new Color(rgb, true); // true handles alpha channel

                // Extraemos canal de color
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                //evaluamos posiciones de los pixeles en sus bins
                int binR = evaluarPosBin(red);
                int binG = evaluarPosBin(green);
                int binB = evaluarPosBin(blue);

                //localizamos el color en el vector caracteristico y lo contamos en el vector
                int posVector = binR*bins*bins + binG*bins + binB;
                vc.setPos(posVector, vc.getPos(posVector) + 1);
            }
        }

        //Creamos un nuevo vector pero es la version normalizada del anterior
        Vector<Double> vn = new Vector<>(tamannoVector);
        double totalPixeles = (double)width * height;

        for (int i = 0; i < tamannoVector; i++) {
            double valor = (double)vc.getPos(i);
            vn.insertar(valor/totalPixeles);
        }
        // vector normalizado, tiene que ser double
        return vn;
    }
}